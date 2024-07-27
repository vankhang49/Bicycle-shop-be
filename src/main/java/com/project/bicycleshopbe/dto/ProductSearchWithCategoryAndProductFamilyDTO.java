package com.project.bicycleshopbe.dto;

import com.project.bicycleshopbe.model.business.Category;
import com.project.bicycleshopbe.model.business.Product;
import com.project.bicycleshopbe.model.business.ProductFamily;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchWithCategoryAndProductFamilyDTO {
    private Page<Product> products;
    private List<Category> categories;
    private Integer page;
    private List<ProductFamily> productFamilies;
    private String nameSearch;

}
