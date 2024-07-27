package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillItemsRepository extends JpaRepository<BillItem, Long> {
}
