package com.abhi.webflux.mynolapp.functions;

import com.abhi.webflux.mynolapp.constant.Tariff;
import com.abhi.webflux.mynolapp.constant.Transport;
import com.abhi.webflux.mynolapp.dao.NolCard;
import com.abhi.webflux.mynolapp.dao.TripDetails;
import com.abhi.webflux.mynolapp.models.rq.NolCheckInReq;
import com.abhi.webflux.mynolapp.models.rq.NolCheckOutReq;
import com.abhi.webflux.mynolapp.models.rq.NolCreateReq;
import com.abhi.webflux.mynolapp.models.rs.NolCardRes;
import com.abhi.webflux.mynolapp.models.rs.Passenger;
import org.springframework.stereotype.Component;
import reactor.util.function.Tuple2;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by Abhinav on 12/30/2018.
 */
@Component
public class NolCardFunction {

    public final Function<NolCreateReq, NolCard> prepareNolCardF = nolCreateReq -> {
        return NolCard.builder()
                .nolID(UUID.randomUUID().toString())
                .balance(nolCreateReq.getNolBalance())
                .passenger(Passenger.builder()
                        .name(nolCreateReq.getPaxName())
                        .address(nolCreateReq.getPaxAddress()).build())
                .creationDate(nolCreateReq.getCreationDate()).build();
    };

    public Function<NolCard, NolCardRes> prepareCreateNolCardResF = nolCard -> {
        return NolCardRes.builder()
                .nolID(nolCard.getNolID())
                .balance(nolCard.getBalance())
                .passenger(nolCard.getPassenger())
                .build();
    };

    public Function<NolCheckInReq, TripDetails> prepareTripDetailsF = nolCheckInReq -> {
        return TripDetails.builder()
                .tripID(UUID.randomUUID().toString())
                .nolID(nolCheckInReq.getNolID())
                .transportType(nolCheckInReq.getTransportType())
                .checkInZone(nolCheckInReq.getCheckInZone())
                .checkInTime(nolCheckInReq.getCheckInTime()).build();
    };

    public Function<Tuple2<NolCard, TripDetails>, NolCard> prepareNolCardUpdateF = tuple2 -> {
        return NolCard.builder()
                .nolID(tuple2.getT1().getNolID())
                .passenger(tuple2.getT1().getPassenger())
                .balance(tuple2.getT1().getBalance())
                .creationDate(tuple2.getT1().getCreationDate())
                .activeTrip(tuple2.getT2()).build();
    };
    public Function<NolCard, NolCardRes> prepareNolCardCheckInResF = nolCard -> {
        return NolCardRes.builder()
                .nolID(nolCard.getNolID())
                .balance(nolCard.getBalance())
                .passenger(nolCard.getPassenger())
                .activeTrip(nolCard.getActiveTrip())
                .build();
    };
    public Function<Tuple2<NolCard, NolCheckOutReq>, NolCard> prepareNolCardCheckOutF = tuple2 -> {
        return NolCard.builder()
                .nolID(tuple2.getT1().getNolID())
                .passenger(tuple2.getT1().getPassenger())
                .balance(tuple2.getT1().getBalance())
                .creationDate(tuple2.getT1().getCreationDate())
                .activeTrip(TripDetails.builder()
                        .tripID(tuple2.getT1().getActiveTrip().getTripID())
                        .nolID(tuple2.getT1().getActiveTrip().getNolID())
                        .transportType(tuple2.getT1().getActiveTrip().getTransportType())
                        .checkInZone(tuple2.getT1().getActiveTrip().getCheckInZone())
                        .checkInTime(tuple2.getT1().getActiveTrip().getCheckInTime())
                        .checkOutZone(tuple2.getT2().getCheckOutZone())
                        .checkOutTime(tuple2.getT2().getCheckOutTime()).build()).build();
    };
    public Function<Tuple2<TripDetails,NolCard>, NolCard> calculateTariffF = tuple2 -> {
            double chargedAmount =  Optional.ofNullable(tuple2.getT1())
                    .filter(tripDetails -> !tuple2.getT2().getActiveTrip().getTransportType().equalsIgnoreCase(tripDetails.getTransportType()))
                    .filter(tripDetails -> TimeUnit.MINUTES.convert((tuple2.getT2().getActiveTrip().getCheckOutTime().getTime()-tripDetails.getCheckOutTime().getTime()), TimeUnit.MILLISECONDS) <= 30)
                    .map(tripDetails -> {return Tariff.zeroTariff;})
                    .orElse(this.calculateChargedAmount.apply(tuple2.getT2()));

            double balance = tuple2.getT2().getBalance() - chargedAmount;

            return NolCard.builder()
                    .nolID(tuple2.getT2().getNolID())
                    .passenger(tuple2.getT2().getPassenger())
                    .creationDate(tuple2.getT2().getCreationDate())
                    .balance(balance)
                    .activeTrip(tuple2.getT2().getActiveTrip()).build();
    };

    public Function<Tuple2<TripDetails,NolCard>, NolCard> prepareNolCardUpadteF = tuple2 -> {
        return NolCard.builder()
                .nolID(tuple2.getT2().getNolID())
                .passenger(tuple2.getT2().getPassenger())
                .creationDate(tuple2.getT2().getCreationDate())
                .balance(tuple2.getT2().getBalance()).build();
    };

    private Function<NolCard, Double> calculateChargedAmount = nolCard -> {
        double chargedAmount = 0.0;
        if(Transport.Boat.name().equalsIgnoreCase(nolCard.getActiveTrip().getTransportType())){
            chargedAmount = Tariff.boatTariff;
        }else{
            int zonesTravelled = Math.abs(Integer.parseInt(nolCard.getActiveTrip().getCheckInZone()) - Integer.parseInt(nolCard.getActiveTrip().getCheckOutZone())) + 1;
            chargedAmount = Arrays.stream(Tariff.Tariff_Zone.values())
                    .filter(tariff_zone -> tariff_zone.getZones() == zonesTravelled).findFirst()
                    .get().getCharge();
        }
        return chargedAmount;
    };
}
