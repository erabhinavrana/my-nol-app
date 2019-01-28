package com.abhi.webflux.mynolapp.routers;

import com.abhi.webflux.mynolapp.handlers.NolAppHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Created by Abhinav on 12/30/2018.
 */
@Configuration
public class NolAppRouter {
    private final String MODULE_PATH = "/nol";

    @Bean
    public RouterFunction<ServerResponse> nolAPPRoute(NolAppHandler nolAppHandler) {
        return nest(RequestPredicates.path(MODULE_PATH),
                route(RequestPredicates.POST("/create")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), nolAppHandler::createNOLCard)
                        .andRoute(RequestPredicates.POST("/checkin")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), nolAppHandler::checkInNolCard)
                        .andRoute(RequestPredicates.POST("/checkout")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), nolAppHandler::checkOutNolCard)
                        .andRoute(RequestPredicates.POST("/test")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), nolAppHandler::findLastTripDetails));
    }

}
