package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBillRepository extends JpaRepository<Bill, Long> {

    Page<Bill> searchAllByAppUserUserCodeContainingAndAppUserFullNameContaining(String userCode,
                                                                                String fullName,
                                                                                Pageable pageable);

    List<Bill> searchByAppUserUserIdOrderByPaidDescDateCreateDesc(Long userId);

}
