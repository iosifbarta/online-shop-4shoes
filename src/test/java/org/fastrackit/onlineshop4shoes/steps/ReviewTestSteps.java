package org.fastrackit.onlineshop4shoes.steps;

import org.fastrackit.onlineshop4shoes.service.ReviewService;
import org.fastrackit.onlineshop4shoes.transfer.review.ReviewResponse;
import org.fastrackit.onlineshop4shoes.transfer.review.SaveReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class ReviewTestSteps {

    @Autowired
    private ReviewService reviewService;

    public ReviewResponse createReview (){
        SaveReviewRequest request = new SaveReviewRequest();
        request.setContent("Test review");
        request.setAuthor("Test");

        ReviewResponse review = reviewService.createReview(request);

        assertThat(review, notNullValue());
        assertThat(review.getId(), greaterThan(0L));
        assertThat(review.getContent(), is(request.getContent()));
        assertThat(review.getAuthor(), is(request.getAuthor()));

        return review;

    }
}
