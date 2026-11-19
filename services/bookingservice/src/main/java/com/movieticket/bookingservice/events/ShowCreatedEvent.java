package com.movieticket.bookingservice.events;


public record ShowCreatedEvent(
        Long showId,
        Long screenId
) { }
