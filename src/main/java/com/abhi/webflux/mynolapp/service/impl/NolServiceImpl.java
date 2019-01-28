package com.abhi.webflux.mynolapp.service.impl;

import com.abhi.webflux.mynolapp.dao.NolCard;
import com.abhi.webflux.mynolapp.dao.TripDetails;
import com.abhi.webflux.mynolapp.repo.NolCardRepo;
import com.abhi.webflux.mynolapp.repo.TripDetailRepo;
import com.abhi.webflux.mynolapp.service.NolCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Created by Abhinav on 12/27/2018.
 */
@Service
public class NolServiceImpl implements NolCardService {

    @Autowired
    private NolCardRepo nolCardRepo;

    @Autowired
    private TripDetailRepo tripDetailRepo;

    @Override
    public Mono<NolCard> createNolCard(NolCard nolCard) {
        return nolCardRepo.insert(nolCard);
    }

    @Override
    public Mono<NolCard> findNolCard(final String nolID) {
        return nolCardRepo.findByNolID(Mono.just(nolID));
    }

    @Override
    public Mono<NolCard> updateNolCard(NolCard nolCard) {
        return nolCardRepo.save(nolCard);
    }

    @Override
    public Mono<TripDetails> createTripDetails(TripDetails tripDetails) {
        return tripDetailRepo.insert(tripDetails);
    }

    @Override
    public Mono<TripDetails> updateTripDetails(TripDetails tripDetails) {
        return tripDetailRepo.save(tripDetails);
    }

    @Override
    public Mono<TripDetails> findLastTripDetails(final String nolID) {
        return tripDetailRepo.findFirstByNolID(nolID, Sort.by(Sort.Direction.DESC,"checkOutTime"));
    }
}
