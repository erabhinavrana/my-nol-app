package com.abhi.webflux.mynolapp.models.rq;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

/**
 * Created by Abhinav on 1/9/2019.
 */
@Getter
@Builder
public class NolCheckOutReq {
    private final String nolID;
    private final String checkOutZone;
    private final Date checkOutTime;
}
