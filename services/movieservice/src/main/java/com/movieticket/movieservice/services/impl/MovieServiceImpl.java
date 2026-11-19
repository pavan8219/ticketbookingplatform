package com.movieticket.movieservice.services.impl;

import com.movieticket.movieservice.dtos.*;
import com.movieticket.movieservice.entity.Movie;
import com.movieticket.movieservice.entity.Screen;
import com.movieticket.movieservice.entity.Show;
import com.movieticket.movieservice.entity.Theatre;
import com.movieticket.movieservice.exceptions.MovieNotFoundException;
import com.movieticket.movieservice.repository.MovieRepository;
import com.movieticket.movieservice.repository.ShowRepository;
import com.movieticket.movieservice.services.MovieService;



import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final ModelMapper modelMapper;

    @Override
    public MovieResponse createMovie(MovieCreateRequest request) {

        Movie movie = modelMapper.map(request, Movie.class);
        Movie savedMovie = movieRepository.save(movie);
        return modelMapper.map(savedMovie, MovieResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public MovieResponse getMovie(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));

        return modelMapper.map(movie, MovieResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(movie -> modelMapper.map(movie, MovieResponse.class))
                .toList();
    }

    @Override
    public MovieResponse updateMovie(
            Long id,
            MovieUpdateRequest request) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));

        modelMapper.map(request, movie);
        Movie updatedMovie = movieRepository.save(movie);
        return modelMapper.map(updatedMovie, MovieResponse.class);
    }

    @Override
    public void deleteMovie(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
        movieRepository.delete(movie);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TheatreShowsResponse> getShowsByMovieAndDate(Long movieId,LocalDate date) {

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime nextDay = date.plusDays(1).atStartOfDay();

        List<Show> shows = showRepository.findShowsByMovieAndDate(movieId,
                        startOfDay,
                        nextDay
                );


        Map<Theatre, List<Show>> showsByTheatre =
                shows.stream()
                        .collect(Collectors.groupingBy(
                                show -> show.getScreen().getTheatre()
                        ));



        return showsByTheatre.entrySet()
                .stream()
                .map(theatreEntry -> {

                    Theatre theatre = theatreEntry.getKey();

                    Map<Screen, List<Show>> showsByScreen =
                            theatreEntry.getValue()
                                    .stream()
                                    .collect(Collectors.groupingBy(
                                            Show::getScreen
                                    ));

                    List<ScreenShowsResponse> screens =
                            showsByScreen.entrySet()
                                    .stream()
                                    .map(screenEntry -> {

                                        Screen screen = screenEntry.getKey();

                                        List<ShowSummaryResponse> showResponses =
                                                screenEntry.getValue()
                                                        .stream()
                                                        .map(show ->
                                                                ShowSummaryResponse
                                                                        .builder()
                                                                        .showId(show.getId())
                                                                        .startTime(show.getStartTime())
                                                                        .endTime(show.getEndTime())
                                                                        .build()
                                                        )
                                                        .toList();

                                        return ScreenShowsResponse.builder()
                                                .screenId(screen.getId())
                                                .screenName(screen.getName())
                                                .shows(showResponses)
                                                .build();
                                    })
                                    .toList();

                    return TheatreShowsResponse.builder()
                            .theatreId(theatre.getId())
                            .theatreName(theatre.getName())
                            .address(theatre.getAddress())
                            .city(theatre.getCity())
                            .screens(screens)
                            .build();
                })
                .toList();
    }
}
