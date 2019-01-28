package com.abhi.webflux.mynolapp.models.rq;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

/**
 * Created by Abhinav on 1/9/2019.
 */
@Getter
@Builder
public class NolCheckInReq {
    private final String nolID;
    private final String transportType;
    private final String checkInZone;
    private final Date checkInTime;
}
