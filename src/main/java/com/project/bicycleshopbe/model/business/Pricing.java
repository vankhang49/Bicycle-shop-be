package com.project.bicycleshopbe.model.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pricings",
        uniqueConstraints = { //
                @UniqueConstraint(name = "PRICINGS_UK", columnNames = "price_code") })
public class Pricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id")
    private Long priceId;

    @Column(name = "price_code")
    private String priceCode;

    @Column(name = "price_name")
    private String priceName;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price")
    @Min(value = 0, message = "Giá thành phải lớn hơn hoặc bằng 0!")
    private Double price;

    private String size;

    @Column(name = "inventory")
    @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0!")
    private Integer inventory;

    @ManyToOne(optional = false)
    @JoinColumn(name = "color_id")
    private Color color;

    private String imgColor;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

}
