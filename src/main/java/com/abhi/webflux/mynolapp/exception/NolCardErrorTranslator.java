package com.abhi.webflux.mynolapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by Abhinav on 1/27/2019.
 */
@Getter
@Component
public class NolCardErrorTranslator {
    private HttpStatus httpStatus;
    private List<NolCardError> errors = new ArrayList<>();

    public static final Supplier<NolCardErrorTranslatorBuilder> builder = NolCardErrorTranslator.NolCardErrorTranslatorBuilder::new;

    public static class NolCardErrorTranslatorBuilder{
        private final NolCardErrorTranslator nolCardErrorTranslator;
        public void prepareNolCardError(final Throwable error){
            HttpStatus httpStatus;
            if (error instanceof ActiveTripException){
                httpStatus = HttpStatus.BAD_REQUEST;
            }else if (error instanceof MinimumBalanceException){
                httpStatus = HttpStatus.PAYMENT_REQUIRED;
            }else {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                nolCardErrorTranslator.errors.add(NolCardError.builder()
                        .errorMessage(NolCardException.DEFAULT_MESSAGE)
                        .errorCode(NolCardException.DEFAULT_CODE)
                        .errorSummary(NolCardException.DEFAULT_SUMMARY).build());
            }
            nolCardErrorTranslator.httpStatus = httpStatus;
            if(error instanceof NolCardBaseException){
                final NolCardBaseException nolCardBaseException = (NolCardBaseException) error;
                nolCardErrorTranslator.errors.addAll(nolCardBaseException.getErrors());
            }
        }

        public NolCardErrorTranslatorBuilder() {
            this.nolCardErrorTranslator = new NolCardErrorTranslator();
        }

        public NolCardErrorTranslatorBuilder and(final Consumer<NolCardErrorTranslatorBuilder> input){
            input.accept(this);
            return this;
        }

        public Mono<NolCardErrorTranslator> nolCardErrorTranslator(){
            return Mono.just(nolCardErrorTranslator);
        }
    }
}
