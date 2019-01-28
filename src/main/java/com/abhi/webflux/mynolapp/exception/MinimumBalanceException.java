package com.abhi.webflux.mynolapp.exception;

import java.util.Collection;

/**
 * Created by Abhinav on 1/24/2019.
 */
@NolCardException(
        errorCode = "404.001.001",
        errorMessage = "Minimum balance not available. Please recharge your Nol Card!",
        errorSummary = "Low Balance"
)
public class MinimumBalanceException  extends NolCardBaseException{
    public MinimumBalanceException(Collection<NolCardError> errors, Throwable cause) {
        super(errors, cause);
    }

    public MinimumBalanceException(Collection<NolCardError> errors) {
        super(errors);
    }

    public MinimumBalanceException(Throwable cause) {
        super(cause);
    }
}
