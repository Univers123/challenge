package com.toufik.challenge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Company implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "company_id")
    private Integer companyId;

    private String companyName;

    private Double balance;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        Company company = new Company(this.companyId, this.companyName, this.balance, this.users);
        String s = "";
        try {
            s = mapper.writeValueAsString(company);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}
