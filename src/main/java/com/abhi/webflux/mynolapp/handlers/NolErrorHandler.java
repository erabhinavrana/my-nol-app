package com.abhi.webflux.mynolapp.handlers;

import com.abhi.webflux.mynolapp.exception.NolCardErrorTranslator;
import com.abhi.webflux.mynolapp.models.rs.NolCardRes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Created by Abhinav on 1/24/2019.
 */
@Component
public class NolErrorHandler {
    public Mono<ServerResponse> handleError(Throwable error) {
        return Mono.just(error).transform(this::transformNolCardErrors);
    }

    private <T extends Throwable> Mono<ServerResponse> transformNolCardErrors(final Mono<T> monoErrors) {
        return monoErrors
                .flatMap(error -> NolCardErrorTranslator.builder.get().and(nolCardErrorTranslatorBuilder -> nolCardErrorTranslatorBuilder.prepareNolCardError(error)).nolCardErrorTranslator())
                .flatMap(nolCardErrorTranslator -> ServerResponse
                        .status(nolCardErrorTranslator.getHttpStatus())
                        .body(Mono.just(NolCardRes.builder().errors(nolCardErrorTranslator.getErrors()).build()), NolCardRes.class));
    }
}
