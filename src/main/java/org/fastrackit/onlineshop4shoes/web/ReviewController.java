package org.fastrackit.onlineshop4shoes.web;


import org.fastrackit.onlineshop4shoes.service.ReviewService;
import org.fastrackit.onlineshop4shoes.transfer.review.GetReviewsRequest;
import org.fastrackit.onlineshop4shoes.transfer.review.ReviewResponse;
import org.fastrackit.onlineshop4shoes.transfer.review.SaveReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody SaveReviewRequest request){
        ReviewResponse review = reviewService.createReview(request);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable long id, @Valid@RequestBody SaveReviewRequest request){
        ReviewResponse review = reviewService.updateReview(id, request);

        return new ResponseEntity<>(review, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Page<ReviewResponse>> getReviewsByContent(long productId, Pageable pageable){
        Page<ReviewResponse> reviews = reviewService.getReviews(productId,pageable);

        return new ResponseEntity<>(reviews, HttpStatus.OK);

    }

}
