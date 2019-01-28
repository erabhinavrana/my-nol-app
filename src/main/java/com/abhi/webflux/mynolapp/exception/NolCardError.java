package com.abhi.webflux.mynolapp.exception;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by Abhinav on 1/24/2019.
 */
@Getter
@Builder
public class NolCardError {
    private final String errorCode;
    private final String errorMessage;
    private final String errorSummary;

    @Override
    public String toString() {
        return String.format("{%s} {%s} {%s}", errorCode, errorMessage, errorSummary);
    }

}
