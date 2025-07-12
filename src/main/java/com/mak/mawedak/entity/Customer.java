package com.mak.mawedak.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    public Customer(Long customerId) {
        this.customerId = customerId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime joinDate;

    // System settings
    @OneToOne(cascade = CascadeType.ALL)
    private CustomerPersonalInfo customerPersonalInfo;

    @OneToOne(cascade = CascadeType.ALL)
    private PatientProfileSettings patientProfileSettings;

    @OneToOne(cascade = CascadeType.ALL)
    private CalendarSettings calendarSettings;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Insurance> insurances;

    @OneToOne(cascade = CascadeType.ALL)
    private NotificationSettings notificationSettings;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedDate;

    @CreationTimestamp
    @Column
    private LocalDateTime createdDate;
}
