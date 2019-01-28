package com.abhi.webflux.mynolapp.repo;

import com.abhi.webflux.mynolapp.dao.TripDetails;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Created by Abhinav on 12/30/2018.
 */
@Repository
public interface TripDetailRepo extends ReactiveMongoRepository<TripDetails,String>{

    Mono<TripDetails> findFirstByNolID(final String nolID, final Sort sort);
}
