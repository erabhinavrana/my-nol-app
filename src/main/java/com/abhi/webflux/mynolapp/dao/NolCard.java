package com.abhi.webflux.mynolapp.dao;

import com.abhi.webflux.mynolapp.models.rs.Passenger;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by Abhinav on 12/27/2018.
 */
@Getter
@Builder
@Document(collection = "NOL_CARD")
public class NolCard{
    @Id
    private final String nolID;
    private final double balance;
    private final Passenger passenger;
    private final Date creationDate;
    private final TripDetails activeTrip;
}
