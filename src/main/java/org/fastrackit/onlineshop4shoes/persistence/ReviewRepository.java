package org.fastrackit.onlineshop4shoes.persistence;


import org.fastrackit.onlineshop4shoes.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

//    @Query(value = "SELECT  review FROM  Review  review WHERE "+
//            "(:partialContent IS null OR review.content LIKE %:partialContent%) AND "+
//            "(:author IS null OR review.author = :author)")
//
//
    Page<Review> findReviewsByProductId (long productId, Pageable pageable);

}
