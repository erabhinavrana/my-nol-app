package com.abhi.webflux.mynolapp.service;

import com.abhi.webflux.mynolapp.dao.NolCard;
import com.abhi.webflux.mynolapp.dao.TripDetails;
import reactor.core.publisher.Mono;

/**
 * Created by Abhinav on 12/27/2018.
 */
public interface NolCardService {

    Mono<NolCard> createNolCard(NolCard nolCard);
    Mono<NolCard> findNolCard(final String nolID);
    Mono<NolCard> updateNolCard(NolCard nolCard);

    Mono<TripDetails> createTripDetails(TripDetails tripDetails);

    Mono<TripDetails> updateTripDetails(TripDetails tripDetails);
    Mono<TripDetails> findLastTripDetails(final String nolID);
}
