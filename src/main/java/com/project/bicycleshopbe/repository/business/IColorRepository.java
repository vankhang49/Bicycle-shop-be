package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IColorRepository extends JpaRepository<Color, Long> {
}
