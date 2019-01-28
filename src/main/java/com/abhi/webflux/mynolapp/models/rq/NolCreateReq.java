package com.abhi.webflux.mynolapp.models.rq;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Abhinav on 12/30/2018.
 */
@Getter
@Setter
@Builder
public class NolCreateReq {
    private final String paxName;
    private final String mobileNumber;
    private final String paxAddress;
    private final double nolBalance;
    private final Date creationDate;
}
