package com.abhi.webflux.mynolapp.dao;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by Abhinav on 1/9/2019.
 */
@Getter
@Builder
@Document(collection = "TRIP_DETAILS")
public class TripDetails {
    @Id
    private String tripID;
    private String nolID;
    private String transportType;
    private String checkInZone;
    private Date checkInTime;
    private String checkOutZone;
    private Date checkOutTime;
}
