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
@Table(name = "USER")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Integer userId;

    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnore
    private Company company;

    @OneToMany(mappedBy = "user")
    private List<Distribution> wedooGiftList = new ArrayList<>();


    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User(this.userId, this.userName, this.company, this.wedooGiftList);
        String s = "";
        try {
            s = mapper.writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

}
