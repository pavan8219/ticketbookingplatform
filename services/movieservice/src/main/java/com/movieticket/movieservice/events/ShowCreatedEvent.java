package com.movieticket.movieservice.events;


public record ShowCreatedEvent(
        Long showId,
        Long screenId
) { }
