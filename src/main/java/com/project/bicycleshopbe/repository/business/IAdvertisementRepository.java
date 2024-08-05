package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
