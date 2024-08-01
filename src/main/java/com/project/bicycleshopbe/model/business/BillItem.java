package com.project.bicycleshopbe.model.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bill_items")
@EqualsAndHashCode(exclude = "bill")
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "price_id")
    private Pricing pricing;

    private int quantity;

    public BillItem(Pricing pricing, int quantity) {
        this.pricing = pricing;
        this.quantity = quantity;
    }
}