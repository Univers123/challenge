package com.toufik.challenge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.toufik.challenge.utils.DistributionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Distribution")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Distribution implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer distributionId;

    private DistributionEnum type;

    private Double amount;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate entredDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    // Constructor to instantiate a Distribution object.
    public Distribution(String type, Double amount, User user) {
        LocalDate date = LocalDate.now();
        this.amount = amount;
        this.entredDate = date;
        this.user = user;
        switch (type.toLowerCase()) {
            case "gift":
                this.type = DistributionEnum.GIFT;
                this.expirationDate = date.plusYears(1).minusDays(1);
                break;
            case "meal":
                this.type = DistributionEnum.MEAL;
                this.expirationDate = date.plusYears(1).withMonth(2).withDayOfMonth(28);
                break;
        }
    }

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        Distribution distribution = new Distribution(this.distributionId, this.type, this.amount, this.entredDate, this.expirationDate, this.user);
        String s = "";
        try {
            s = mapper.writeValueAsString(distribution);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}
