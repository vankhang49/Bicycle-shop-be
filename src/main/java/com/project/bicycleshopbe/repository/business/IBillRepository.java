package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.Bill;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBillRepository extends JpaRepository<Bill, Long> {

    Page<Bill> searchAllByBillCodeContainingAndAppUserFullNameContaining(String billCode,
                                                                         String fullName,
                                                                         Pageable pageable);

    Slice<Bill> searchByAppUserUserIdOrderByPaidDescDateCreateDesc(Long userId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE bill SET paid = 1 WHERE bill_id = :billId", nativeQuery = true)
    void updateReceivedBill(@Param("billId") Long billId);
}
