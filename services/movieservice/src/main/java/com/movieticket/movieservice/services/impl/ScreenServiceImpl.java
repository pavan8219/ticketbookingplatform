package com.movieticket.movieservice.services.impl;

import com.movieticket.movieservice.dtos.ScreenCreateRequest;
import com.movieticket.movieservice.dtos.ScreenResponse;
import com.movieticket.movieservice.entity.Screen;
import com.movieticket.movieservice.entity.Theatre;
import com.movieticket.movieservice.exceptions.ScreenAlreadyExistsException;
import com.movieticket.movieservice.exceptions.ScreenNotFoundException;
import com.movieticket.movieservice.exceptions.TheatreNotFoundException;
import com.movieticket.movieservice.repository.ScreenRepository;
import com.movieticket.movieservice.repository.TheatreRepository;
import com.movieticket.movieservice.services.ScreenService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;
    private final ModelMapper modelMapper;

    @Override
    public ScreenResponse create(ScreenCreateRequest request) {

        Theatre theatre = theatreRepository.findById(request.getTheatreId())
                .orElseThrow(() -> new TheatreNotFoundException("Theatre not found with id: " + request.getTheatreId()));
        if(screenRepository.existsByNameAndTheatreId(request.getName(),theatre.getId())){
            throw new ScreenAlreadyExistsException("Screen name Already exists in the theatre");
        }
        Screen screen = new Screen();
        screen.setName(request.getName());
        screen.setCapacity(request.getCapacity());
        screen.setTheatre(theatre);
        Screen savedScreen = screenRepository.save(screen);
        return toResponse(savedScreen);
    }

    @Override
    @Transactional(readOnly = true)
    public ScreenResponse getById(Long id) {

        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ScreenNotFoundException("Screen not found with id: " + id));

        return toResponse(screen);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScreenResponse> getAll() {

        return screenRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ScreenResponse update(Long id, ScreenCreateRequest request) {

        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ScreenNotFoundException("Screen not found with id: " + id));

        // Find the new theatre
        Theatre theatre = theatreRepository.findById(request.getTheatreId())
                .orElseThrow(() -> new TheatreNotFoundException("Theatre not found with id: " + request.getTheatreId()));

        // Update simple fields
        screen.setName(request.getName());
        screen.setCapacity(request.getCapacity());

        // Update relationship
        screen.setTheatre(theatre);
        Screen updatedScreen = screenRepository.save(screen);
        return toResponse(updatedScreen);
    }

    @Override
    public void delete(Long id) {

        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ScreenNotFoundException("Screen not found with id: " + id));
        screenRepository.delete(screen);
    }

    private ScreenResponse toResponse(Screen screen) {

        return ScreenResponse.builder()
                .id(screen.getId())
                .name(screen.getName())
                .capacity(screen.getCapacity())
                .theatreId(screen.getTheatre().getId())
                .build();
    }
}
