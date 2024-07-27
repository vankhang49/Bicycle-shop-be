package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IColorRepository extends JpaRepository<Color, Long> {
}
