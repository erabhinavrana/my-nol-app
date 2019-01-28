package com.abhi.webflux.mynolapp.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Abhinav on 1/24/2019.
 */
@Getter
public class NolCardBaseException extends RuntimeException {
    private final Collection<NolCardError> errors = new ArrayList<>();

    protected NolCardBaseException(final Collection<NolCardError> errors, final Throwable cause){

        super(Optional.ofNullable(errors)
                .map(nolCardErrors -> nolCardErrors.stream()
                        .map(NolCardError::getErrorMessage)
                        .collect(Collectors.joining(",")))
                .orElse(NolCardException.DEFAULT_MESSAGE), cause);
        initialise(Optional.empty());
        Optional.ofNullable(errors).ifPresent(this.errors::addAll);
    }

    protected NolCardBaseException(final Collection<NolCardError> errors){
        super(Optional.ofNullable(errors)
                .map(nolCardErrors -> nolCardErrors.stream()
                        .map(NolCardError::getErrorMessage)
                        .collect(Collectors.joining(", ")))
                .orElse(NolCardException.DEFAULT_MESSAGE));

        initialise(Optional.empty());
        Optional.ofNullable(errors).ifPresent(this.errors::addAll);
    }

    protected NolCardBaseException(final Throwable cause){
        super(cause);
        initialise(Optional.of(cause));
    }

    private void initialise(final Optional<Throwable> optionalThrowable) {
        final Class<?> exception = getClass();
        if (exception.isAnnotationPresent(NolCardException.class)) {
            final NolCardException annotation = exception.getAnnotation(NolCardException.class);
            errors.add(NolCardError.builder()
                    .errorMessage(
                            String.format("%s%s", annotation.errorMessage(), optionalThrowable
                                    .map(Throwable::getMessage).orElse("")))
                    .errorCode(annotation.errorCode())
                    .errorSummary(annotation.errorSummary()).build());
        }
        else {
            errors.add(NolCardError.builder()
                    .errorMessage(NolCardException.DEFAULT_MESSAGE)
                    .errorCode(NolCardException.DEFAULT_CODE)
                    .errorSummary(NolCardException.DEFAULT_SUMMARY).build());
        }
    }
}
