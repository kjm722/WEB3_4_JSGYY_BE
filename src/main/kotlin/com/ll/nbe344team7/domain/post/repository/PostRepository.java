package com.ll.nbe344team7.domain.post.repository;

import com.ll.nbe344team7.domain.post.entity.Post;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE " +
            "(:category IS NULL OR p.category = :category) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:saleStatus IS NULL OR p.saleStatus = :saleStatus) AND " +
            "(:keyword IS NULL OR p.title LIKE CONCAT('%', :keyword, '%')) AND " +
            "(:place IS NULL OR p.place LIKE CONCAT('%', :place, '%'))")
    Page<Post> findBySearchCriteria(
            @Param("category") String category,
            @Param("minPrice") Long minPrice,
            @Param("maxPrice") Long maxPrice,
            @Param("saleStatus") Boolean saleStatus,
            @Param("keyword") String keyword,
            @Param("place") String place,
            Pageable pageable
    );


    Optional<Post> findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Post p WHERE p.id = :postId")
    Optional<Post> findByIdWithLock(@Param("postId") Long id);

    Optional<Post> findFirstByOrderByIdDesc();

    Page<Post> findByMemberId(Long id, Pageable pageable);
}
