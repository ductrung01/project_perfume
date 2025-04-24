package com.dan.shoe.perfume.repositories;

import com.dan.shoe.perfume.models.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Optional<Voucher> findByCode(String code);
    List<Voucher> findByActiveTrueAndStartDateBeforeAndEndDateAfter(LocalDate currentDate1, LocalDate currentDate2);
    Voucher findTopByActiveTrueAndStartDateBeforeAndEndDateAfterAndMaxUsageGreaterThanOrderByDiscountAmountDesc(LocalDate currentDate1, LocalDate currentDate2, int minUsage);
    Page<Voucher> findByCodeContaining(String code, Pageable pageable);
}
