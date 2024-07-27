package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillRepository extends JpaRepository<Bill, Long> {

}
