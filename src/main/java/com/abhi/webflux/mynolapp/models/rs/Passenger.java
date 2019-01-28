package com.abhi.webflux.mynolapp.models.rs;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by Abhinav on 1/9/2019.
 */
@Getter
@Builder
public class Passenger {
    private final String name;
    private final String address;
}
