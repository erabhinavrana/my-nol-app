package com.abhi.webflux.mynolapp.repo;

import com.abhi.webflux.mynolapp.dao.NolCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Created by Abhinav on 12/30/2018.
 */
@Repository
public interface NolCardRepo extends ReactiveMongoRepository<NolCard,String>{
    Mono<NolCard> findByNolID(Mono<String> nolID);
}
