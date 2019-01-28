package com.abhi.webflux.mynolapp.handlers;

import com.abhi.webflux.mynolapp.dao.TripDetails;
import com.abhi.webflux.mynolapp.functions.NolCardFunction;
import com.abhi.webflux.mynolapp.functions.NolCardValidation;
import com.abhi.webflux.mynolapp.models.rq.NolCheckInReq;
import com.abhi.webflux.mynolapp.models.rq.NolCheckOutReq;
import com.abhi.webflux.mynolapp.models.rq.NolCreateReq;
import com.abhi.webflux.mynolapp.models.rs.NolCardRes;
import com.abhi.webflux.mynolapp.service.NolCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Created by Abhinav on 12/30/2018.
 */
@Component
@RequiredArgsConstructor
public class NolAppHandler {

    @Autowired
    private NolCardFunction nolCardFunction;

    @Autowired
    private NolCardValidation nolCardValidation;

    @Autowired
    private NolCardService nolCardService;

    @Autowired
    private NolErrorHandler errorHandler;


    public Mono<ServerResponse> createNOLCard(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(NolCreateReq.class)
                .map(nolCardFunction.prepareNolCardF)
                .flatMap(nolCard -> nolCardService.createNolCard(nolCard))
                .map(nolCardFunction.prepareCreateNolCardResF)
                .flatMap(nolCardRes -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(nolCardRes), NolCardRes.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> checkInNolCard(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(NolCheckInReq.class)
                .map(nolCardFunction.prepareTripDetailsF)
                .flatMap(tripDetails -> nolCardService.findNolCard(tripDetails.getNolID())
                        .map(nolCardValidation.checkForMinimumBalance)
                        .map(nolCardValidation.checkForAnyActiveTrip)
                        .zipWith(nolCardService.createTripDetails(tripDetails)))
                .map(nolCardFunction.prepareNolCardUpdateF)
                .flatMap(nolCard -> nolCardService.updateNolCard(nolCard))
                .map(nolCardFunction.prepareNolCardCheckInResF)
                .flatMap(nolCardRes -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(nolCardRes), NolCardRes.class))
                .onErrorResume(errorHandler::handleError);
    }

    public Mono<ServerResponse> checkOutNolCard(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(NolCheckOutReq.class)
                .flatMap(nolCheckOutReq -> nolCardService.findNolCard(nolCheckOutReq.getNolID())
                .zipWith(Mono.just(nolCheckOutReq)))
                .map(nolCardFunction.prepareNolCardCheckOutF)
                .flatMap(nolCard -> nolCardService.findLastTripDetails(nolCard.getNolID())
                        .zipWith(Mono.just(nolCard)))
                .map(nolCardFunction.calculateTariffF)
                .flatMap(nolCard -> nolCardService.updateTripDetails(nolCard.getActiveTrip())
                .zipWith(Mono.just(nolCard)))
                .map(nolCardFunction.prepareNolCardUpadteF)
                .flatMap(nolCard -> nolCardService.updateNolCard(nolCard))
                .map(nolCardFunction.prepareNolCardCheckInResF)
                .flatMap(nolCardRes -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(nolCardRes), NolCardRes.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findLastTripDetails(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(NolCheckOutReq.class)
                .flatMap(nolCheckOutReq -> nolCardService.findLastTripDetails(nolCheckOutReq.getNolID()))
                .flatMap(tripDetails -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(tripDetails), TripDetails.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
