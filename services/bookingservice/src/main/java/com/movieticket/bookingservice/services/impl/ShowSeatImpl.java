package com.movieticket.bookingservice.services.impl;

import com.movieticket.bookingservice.dtos.FetchSeatsResponse;
import com.movieticket.bookingservice.entity.ShowSeat;
import com.movieticket.bookingservice.enums.SeatStatus;
import com.movieticket.bookingservice.repository.ShowSeatRepository;
import com.movieticket.bookingservice.services.SeatLockService;
import com.movieticket.bookingservice.services.ShowSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.Show;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowSeatImpl implements ShowSeatService {
    private final ShowSeatRepository showSeatRepository;
    private final SeatLockService seatLockService;
    @Override
    public List<FetchSeatsResponse> getAllSeatsForShowWithStatus(Long showId) {

        List<ShowSeat> showSeats=showSeatRepository.findByShowId(showId);
        List<String> showSeatKeys=showSeats.stream().map((show -> seatLockService.buildKey(showId,show.getSeatId()))).toList();
        List<String> lockedSeatValues = seatLockService.getLockedSeatValues(showSeatKeys);
        List<FetchSeatsResponse> response=new ArrayList<>();
        for(int i=0;i<showSeats.size();i++){
            ShowSeat showSeat=showSeats.get(i);
            String lockStatus;
            lockStatus=lockedSeatValues.get(i);

            if(lockStatus !=null || showSeat.getStatus()== SeatStatus.BOOKED){
                response.add(FetchSeatsResponse.builder()
                                .seatId(showSeat.getId())
                                .seatNumber(showSeat.getSeatId())
                                .status(SeatStatus.BOOKED)
                        .build());
            }else {
                response.add(FetchSeatsResponse.builder()
                        .seatId(showSeat.getId())
                        .seatNumber(showSeat.getSeatId())
                        .status(SeatStatus.AVAILABLE)
                        .build());
            }
        }
        return response;
    }
}
