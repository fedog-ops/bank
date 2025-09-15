package com.eagle.bank_api.user.entity;
import java.time.Instant;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @GeneratedValue
    @Id
    private Long id;

    @Transient
    public String getUserId() {
        return "usr-" + id;
    }

    private String name;

    @Embedded
    @Valid
    private Address address;

    private String number;
    private String email;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}

