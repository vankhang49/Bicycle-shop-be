package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
