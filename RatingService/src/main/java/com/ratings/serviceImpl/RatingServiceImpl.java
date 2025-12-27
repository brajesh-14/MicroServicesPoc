package com.ratings.serviceImpl;

import com.ratings.client.HotelService;
import com.ratings.dto.HotelDTO;
import com.ratings.dto.RatingResponseDTO;
import com.ratings.entity.Ratings;
import com.ratings.exceptions.ResourceNotFoundException;
import com.ratings.repository.RatingRepo;
import com.ratings.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepo ratingRepo;
    private final HotelService hotelService;

    @Override
    public Ratings saveRatings(Ratings ratings) {
        String rateId = UUID.randomUUID().toString();
        ratings.setRatingId(rateId);
        return ratingRepo.save(ratings);
    }

    public Ratings getRating(String ratingId){
        return ratingRepo.findById(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException("User with userId " + ratingId + " does not exist"));
    }

    @Override
    public List<Ratings> getAllRatings() {
        return ratingRepo.findAll();
    }

    @Override
    public List<RatingResponseDTO> getRatingByUserId(String userId) {
        List<Ratings> ratings = ratingRepo.findByUserId(userId);
        if (ratings.isEmpty()) {
            throw new ResourceNotFoundException("No ratings found for userId: " + userId);
        }
        return ratings.stream().map(r ->{
            HotelDTO hotel = hotelService.getHotels(r.getHotelId());

            return RatingResponseDTO.builder()
                    .ratingId(r.getRatingId())
                    .userId(r.getUserId())
                    .hotelId(r.getHotelId())
                    .rating(r.getRating())
                    .feedback(r.getFeedback())
                    .hotel(hotel)
                    .build();
        }).toList();
    }

    @Override
    public List<Ratings> getRatingByHotelId(String hotelId) {
        List<Ratings> ratings = ratingRepo.findByHotelId(hotelId);
        if (ratings.isEmpty()) {
            throw new ResourceNotFoundException("No ratings found for hotelId: " + hotelId);
        }
        return ratings;
    }
}
