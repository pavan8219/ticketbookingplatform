package com.movieticket.movieservice.services.impl;

import com.movieticket.movieservice.config.ShowEventProducer;
import com.movieticket.movieservice.dtos.ShowCreateRequest;
import com.movieticket.movieservice.dtos.ShowResponse;
import com.movieticket.movieservice.entity.Movie;
import com.movieticket.movieservice.entity.Screen;
import com.movieticket.movieservice.entity.Show;
import com.movieticket.movieservice.events.ShowCreatedEvent;
import com.movieticket.movieservice.exceptions.MovieNotFoundException;
import com.movieticket.movieservice.exceptions.ScreenNotFoundException;
import com.movieticket.movieservice.exceptions.ShowConflictException;
import com.movieticket.movieservice.exceptions.ShowNotFoundException;
import com.movieticket.movieservice.repository.MovieRepository;
import com.movieticket.movieservice.repository.ScreenRepository;
import com.movieticket.movieservice.repository.ShowRepository;
import com.movieticket.movieservice.services.ShowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final ShowEventProducer showEventProducer;
    private final ModelMapper modelMapper;

    @Override
    public ShowResponse create(ShowCreateRequest request) {

        validateTime(request);
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + request.getMovieId()));


        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new ScreenNotFoundException("Screen not found with id: " + request.getScreenId()));

        boolean overlapping = showRepository.existsOverlappingShow(
                                request.getScreenId(),
                                request.getStartTime(),
                                request.getEndTime());

        if (overlapping) {
            throw new ShowConflictException("Screen " + request.getScreenId() + " already has a show during this time");
        }

        Show show = Show.builder()
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        show.setMovie(movie);
        show.setScreen(screen);
        Show savedShow = showRepository.save(show);
        showEventProducer.publishShowCreated(new ShowCreatedEvent(savedShow.getId(),savedShow.getScreen().getId()));
        log.info("Published show created event");
        return toResponse(savedShow);
    }

    @Override
    @Transactional(readOnly = true)
    public ShowResponse getById(Long id) {

        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ShowNotFoundException("Show not found with id: " + id));

        return toResponse(show);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowResponse> getAll() {
        return showRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ShowResponse update(Long id, ShowCreateRequest request) {

        validateTime(request);
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ShowNotFoundException("Show not found with id: " + id));

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + request.getMovieId()));

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new ScreenNotFoundException("Screen not found with id: " + request.getScreenId()));

        boolean overlapping = showRepository.existsOverlappingShowExcludingId(
                                request.getScreenId(),
                                id,
                                request.getEndTime(),
                                request.getStartTime());

        if (overlapping) {
            throw new ShowConflictException("Screen " + request.getScreenId() + " already has a show during this time");
        }

        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(request.getStartTime());
        show.setEndTime(request.getEndTime());
        Show updatedShow = showRepository.save(show);
        return toResponse(updatedShow);
    }

    @Override
    public void delete(Long id) {

        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ShowNotFoundException("Show not found with id: " + id));

        showRepository.delete(show);
    }

    private void validateTime(ShowCreateRequest request) {
        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new ShowConflictException("End time must be after start time");
        }

    }

    private ShowResponse toResponse(Show show) {
        return ShowResponse.builder()
                .id(show.getId())
                .movieId(show.getMovie().getId())
                .screenId(show.getScreen().getId())
                .startTime(show.getStartTime())
                .endTime(show.getEndTime())
                .build();
    }
}
