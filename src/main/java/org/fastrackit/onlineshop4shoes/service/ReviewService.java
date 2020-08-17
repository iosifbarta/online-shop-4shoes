package org.fastrackit.onlineshop4shoes.service;

import org.fastrackit.onlineshop4shoes.domain.Review;
import org.fastrackit.onlineshop4shoes.exception.ResourceNotFoundException;
import org.fastrackit.onlineshop4shoes.persistence.ReviewRepository;
import org.fastrackit.onlineshop4shoes.transfer.review.ReviewResponse;
import org.fastrackit.onlineshop4shoes.transfer.review.SaveReviewRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewResponse createReview(SaveReviewRequest request){

        LOGGER.info("Creating review {} ", request);
        Review review = new Review();
        review.setContent(request.getContent());
        review.setAuthor(request.getAuthor());
        review.setId(request.getId());

        Review saveReview = reviewRepository.save(review);
        return mapReviewResponse(saveReview);

    }

    public  Review getReview(long id){

        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review " + id + " not found."));
    }
    public ReviewResponse updateReview(long id, SaveReviewRequest request){

        LOGGER.info("Updating review {}: {}", id, request);

        Review review = getReview(id);
        BeanUtils.copyProperties(request,review);
        Review saveReview = reviewRepository.save(review);
         return mapReviewResponse(saveReview);
    }

    public void deleteReview(long id){

        LOGGER.info("Deleting review {}", id);
        reviewRepository.deleteById(id);
    }

    public Page<ReviewResponse> getReviews(long productId, Pageable pageable){
        Page<Review> reviewsPage = reviewRepository.findReviewsByProductId(productId, pageable);

        List<ReviewResponse> reviewsList = new ArrayList<>();
        for (Review review : reviewsPage.getContent()){
            ReviewResponse reviewResponse = mapReviewResponse(review);

            reviewsList.add(reviewResponse);
        }
        return new PageImpl<>(reviewsList, pageable, reviewsPage.getTotalElements());
    }

    private ReviewResponse mapReviewResponse (Review review){
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setId(review.getId());
        reviewResponse.setContent(review.getContent());
        reviewResponse.setAuthor(review.getAuthor());

        return reviewResponse;

    }
}
