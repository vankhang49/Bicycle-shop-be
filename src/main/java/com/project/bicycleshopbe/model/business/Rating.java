package com.project.bicycleshopbe.model.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bicycleshopbe.model.permission.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long ratingId;

    @Column(name = "star")
    private Integer star;

    @Column(name = "content")
    private String content;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
}
