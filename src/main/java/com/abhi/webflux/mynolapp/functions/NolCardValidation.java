package com.abhi.webflux.mynolapp.functions;

import com.abhi.webflux.mynolapp.constant.Tariff;
import com.abhi.webflux.mynolapp.dao.NolCard;
import com.abhi.webflux.mynolapp.exception.ActiveTripException;
import com.abhi.webflux.mynolapp.exception.MinimumBalanceException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Abhinav on 1/13/2019.
 */
@Component
public class NolCardValidation {
    public Function<NolCard, NolCard> checkForAnyActiveTrip = nolCard -> {
        return Optional.ofNullable(nolCard)
                .filter(this.validateActiveTrip)
                .orElseThrow(() -> new ActiveTripException(new RuntimeException(nolCard.getNolID())));
    };

    public Function<NolCard, NolCard> checkForMinimumBalance = nolCard -> {
        return Optional.ofNullable(nolCard)
                .filter(this.validateMinBalance)
                .orElseThrow(() -> new MinimumBalanceException(new RuntimeException(nolCard.getNolID())));
    };

    public Predicate<NolCard> validateMinBalance = nolCard ->  {
        return nolCard.getBalance() > Tariff.minTariff;
    };

    public Predicate<NolCard> validateActiveTrip =  nolCard -> {
        return !Optional.ofNullable(nolCard.getActiveTrip()).isPresent();
    };

}
