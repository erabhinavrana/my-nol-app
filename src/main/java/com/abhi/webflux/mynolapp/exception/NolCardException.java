package com.abhi.webflux.mynolapp.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Abhinav on 1/24/2019.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface NolCardException {
        /**
         * The constant DEFAULT_CODE.
         */
        String DEFAULT_CODE = "520.001.001";
        /**
         * The constant DEFAULT_MESSAGE.
         */
        String DEFAULT_MESSAGE = "Please contact Support Team";
        /**
         * The constant DEFAULT_CMS_CODE.
         */
        String DEFAULT_SUMMARY = "Internal Error";

        /**
         * Error message string.
         *
         * @return the string
         */
        String errorMessage() default DEFAULT_MESSAGE;

        /**
         * Error code string.
         *
         * @return the string
         */
        String errorCode() default DEFAULT_CODE;

        /**
         * Cms error key string.
         *
         * @return the string
         */
        String errorSummary() default DEFAULT_SUMMARY;
}
