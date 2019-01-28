package com.abhi.webflux.mynolapp.models.rs;

import com.abhi.webflux.mynolapp.dao.TripDetails;
import com.abhi.webflux.mynolapp.exception.NolCardError;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Created by Abhinav on 1/9/2019.
 */
@Getter
@Builder
public class NolCardRes {
    private final String nolID;
    private final Passenger passenger;
    private final double balance;
    private final TripDetails activeTrip;
    private final List<NolCardError> errors;
}
