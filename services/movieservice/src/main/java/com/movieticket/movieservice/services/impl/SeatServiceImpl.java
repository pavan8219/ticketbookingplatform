package com.movieticket.movieservice.services.impl;

import com.movieticket.movieservice.dtos.SeatCreateRequest;
import com.movieticket.movieservice.dtos.SeatResponse;
import com.movieticket.movieservice.entity.Screen;
import com.movieticket.movieservice.entity.Seat;
import com.movieticket.movieservice.exceptions.ScreenNotFoundException;
import com.movieticket.movieservice.exceptions.SeatAlreadyExistsException;
import com.movieticket.movieservice.exceptions.SeatNotFoundException;
import com.movieticket.movieservice.repository.ScreenRepository;
import com.movieticket.movieservice.repository.SeatRepository;
import com.movieticket.movieservice.services.SeatService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;
    private final ModelMapper modelMapper;

    @Override
    public SeatResponse create(SeatCreateRequest request) {

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() ->
                        new ScreenNotFoundException("Screen not found with id: " + request.getScreenId()));

        boolean exists =
                seatRepository.existsByScreenIdAndSeatNumber(
                        request.getScreenId(),
                        request.getSeatNumber()
                );

        if (exists) {
            throw new SeatAlreadyExistsException(
                    "Seat " + request.getSeatNumber() + " already exists in screen " + request.getScreenId());
        }

        Seat seat = Seat.builder()
                .seatType(request.getSeatType())
                .seatNumber(request.getSeatNumber())
                .screen(screen)
                .build();


        // theatreId/screenId from DTO cannot be mapped
        // automatically to the entity relationship.
//        seat.setScreen(screen);
        Seat savedSeat = seatRepository.save(seat);
        return toResponse(savedSeat);
    }

    @Override
    @Transactional(readOnly = true)
    public SeatResponse getById(Long id) {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() ->
                        new SeatNotFoundException("Seat not found with id: " + id));

        return toResponse(seat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatResponse> getAll() {

        return seatRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public SeatResponse update(Long id, SeatCreateRequest request) {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found with id: " + id));

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() ->
                        new ScreenNotFoundException("Screen not found with id: " + request.getScreenId()));

        /*
         * Check whether another seat already has
         * the same seat number in this screen.
         */
        boolean exists = seatRepository.existsByScreenIdAndSeatNumber(request.getScreenId(), request.getSeatNumber());

        /*
         * If the existing seat itself has that number,
         * that's okay. We only care about another seat.
         */
        if (exists && (!seat.getScreen().getId().equals(request.getScreenId())
                        || !seat.getSeatNumber().equals(request.getSeatNumber()))) {

            throw new SeatAlreadyExistsException(
                    "Seat " + request.getSeatNumber()
                            + " already exists in screen "
                            + request.getScreenId()
            );
        }

        seat.setSeatNumber(request.getSeatNumber());
        seat.setSeatType(request.getSeatType());
        seat.setScreen(screen);
        Seat updatedSeat = seatRepository.save(seat);
        return toResponse(updatedSeat);
    }

    @Override
    public void delete(Long id) {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() ->
                        new SeatNotFoundException("Seat not found with id: " + id));

        seatRepository.delete(seat);
    }



    @Override
    public List<SeatResponse> getByScreenId(Long id) {
        if(!seatRepository.existsByScreenId(id)) {
            throw new ScreenNotFoundException("screen not found with id"+id);
        }
        List<Seat> seats=seatRepository.findByScreenId(id);

        return seats.stream().map(seat->modelMapper.map(seat, SeatResponse.class)).toList();
    }

    private SeatResponse toResponse(Seat seat) {

        return SeatResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .seatType(seat.getSeatType())
                .screenId(seat.getScreen().getId())
                .build();
    }
}
