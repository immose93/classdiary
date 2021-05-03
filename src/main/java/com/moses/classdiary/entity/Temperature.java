package com.moses.classdiary.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Temperature {
    @Id @GeneratedValue
    private Long id;    // PK, 식별자
    @Temporal(TemporalType.DATE)
    private LocalDateTime date; // 체온 측정 날짜
    private Double value1;  // 1차 측정
    private Double value2;  // 2차 측정
}
