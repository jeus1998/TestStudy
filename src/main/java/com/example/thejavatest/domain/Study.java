package com.example.thejavatest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Study {

    @Id @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private StudyStatus status = StudyStatus.DRAFT;
    private int limitCount;
    private String name;
    private LocalDateTime openedDateTime;
    private Long ownerId;

    public Study(int limit, String name) {
        this.limitCount = limit;
        this.name = name;
    }
    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit 0보다 커야 한다.");
        }
        this.limitCount = limit;
    }
    public void open() {
        this.openedDateTime = LocalDateTime.now();
        this.status = StudyStatus.OPENED;
    }
}