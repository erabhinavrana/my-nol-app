package com.abhi.webflux.mynolapp.exception;

import java.util.Collection;

/**
 * Created by Abhinav on 1/24/2019.
 */
@NolCardException(
        errorCode = "400.001.002",
        errorMessage = "Last trip checkout is still pending. Please do the checkout first to continue next trip!",
        errorSummary = "Last Checkout Failure"
)
public class ActiveTripException extends NolCardBaseException{
    public ActiveTripException(Collection<NolCardError> errors, Throwable cause) {
        super(errors, cause);
    }

    public ActiveTripException(Collection<NolCardError> errors) {
        super(errors);
    }

    public ActiveTripException(Throwable cause) {
        super(cause);
    }
}
