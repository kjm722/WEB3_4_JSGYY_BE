package com.ll.nbe344team7.domain.pay.repository;

import com.ll.nbe344team7.domain.pay.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shjung
 * @since 25. 3. 25.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    public List<Payment> findByMyId(Long memberId);
}
