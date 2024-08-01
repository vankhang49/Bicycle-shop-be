package com.project.bicycleshopbe.model.business;

import com.project.bicycleshopbe.model.permission.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bill")
@EqualsAndHashCode(exclude = "billItems")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "dateCreate")
    private LocalDateTime dateCreate;

    @Column(name = "paid")
    private Boolean paid;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    private boolean payment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private Set<BillItem> billItems = new HashSet<>();


    public void addBillItem(BillItem billItem) {
        billItems.add(billItem);
        billItem.setBill(this);
    }
}
