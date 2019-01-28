package com.abhi.webflux.mynolapp.models.rs;

import java.util.Date;

/**
 * Created by Abhinav on 1/9/2019.
 */
public class Trip {
    private long tripID;
    private long nolID;
    private String transportType;
    private String checkInZone;
    private Date checkInTime;
    private String checkOutZone;
    private Date checkOutTime;
}
