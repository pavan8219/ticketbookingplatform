package com.movieticket.movieservice.services.impl;

import com.movieticket.movieservice.dtos.TheatreCreateRequest;
import com.movieticket.movieservice.dtos.TheatreResponse;
import com.movieticket.movieservice.entity.Theatre;
import com.movieticket.movieservice.exceptions.TheatreNotFoundException;
import com.movieticket.movieservice.repository.TheatreRepository;
import com.movieticket.movieservice.services.TheatreService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TheatreServiceImpl implements TheatreService {

    private final TheatreRepository theatreRepository;
    private final ModelMapper modelMapper;

    @Override
    public TheatreResponse create(TheatreCreateRequest request) {

        Theatre theatre = modelMapper.map(request, Theatre.class);
        Theatre saved = theatreRepository.save(theatre);
        return modelMapper.map(saved, TheatreResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public TheatreResponse getById(Long id) {

        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new TheatreNotFoundException("Theatre not found with id: " + id));

        return modelMapper.map(theatre, TheatreResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TheatreResponse> getAll() {

        return theatreRepository.findAll()
                .stream()
                .map(theatre -> modelMapper.map(theatre, TheatreResponse.class))
                .toList();
    }

    @Override
    public TheatreResponse update(Long id, TheatreCreateRequest request) {

        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new TheatreNotFoundException("Theatre not found with id: " + id));

        modelMapper.map(request, theatre);
        return modelMapper.map(theatre, TheatreResponse.class);
    }

    @Override
    public void delete(Long id) {

        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new TheatreNotFoundException("Theatre not found with id: " + id));

        theatreRepository.delete(theatre);
    }
}
