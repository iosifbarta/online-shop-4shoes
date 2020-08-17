package org.fastrackit.onlineshop4shoes;

import org.fastrackit.onlineshop4shoes.service.ReviewService;
import org.fastrackit.onlineshop4shoes.steps.ReviewTestSteps;
import org.fastrackit.onlineshop4shoes.transfer.review.ReviewResponse;
import org.fastrackit.onlineshop4shoes.transfer.review.SaveReviewRequest;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReviewServiceIntegrationTests {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewTestSteps reviewTestSteps;

    @Test
    void createReview_whenHaveContent_thenReturnCreatedReview(){

        reviewTestSteps.createReview();
    }

    @Test
    void createReview_whenNoContent_thenThrowException() {
        SaveReviewRequest request = new SaveReviewRequest();
        request.setAuthor("Test");

     try{
         ReviewResponse review = reviewService.createReview(request);

         assertThat(review, notNullValue());
         assertThat(review.getId(), is(request.getId()));
         assertThat(review.getAuthor(), is(request.getAuthor()));
     }catch (Exception e){
         MatcherAssert.assertThat("Unexpected exception thrown", e instanceof ConstraintViolationException);
     }

    }

}
