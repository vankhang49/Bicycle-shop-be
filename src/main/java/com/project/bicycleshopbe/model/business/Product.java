package com.project.bicycleshopbe.model.business;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products",
        uniqueConstraints = { //
                @UniqueConstraint(name = "PRODUCTS_UK", columnNames = "product_code") })
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_code", unique = true)
    @Pattern(regexp = "^[A-Z]{2}[0-9A-Z]{4}$", message = "Vui lòng nhập đúng định dạng, mã sản phẩm chỉ phải bắt đầu bằng 2 chữ cái in hoa và kết thúc bằng 4 ký tự chỉ bao gồm số và chữ!")
    private String productCode;

    @Column(name = "product_name")
    @NotBlank(message = "Tên sản phẩm không được để trống!")
    private String productName;

    @Column(name = "product_desc",columnDefinition = "TEXT")
    private String description;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "date_create")
    private LocalDate dateCreate;

    @Column(name="delete_flag")
    private boolean deleteFlag;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private ProductFamily productFamily;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Pricing> pricingList;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

}
