package com.phantasie.demo.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Data
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler", "userVerify", "cartList", "orderList"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "user_id")
public class User {

    private Integer userId;

    private String nickName;

//    @JsonIgnore
//    private String achieve;

    private Integer job;

//    @JsonIgnore
    private String jobInfo;

    private String token;

//    @JsonIgnore
    private UserVerify userVerify;

    public User() {
//        achieve = null;
        job = 0;
    }

    @Id
    @GeneratedValue(generator = "increment", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "user_id")
    public Integer getUserId() { return userId; }

    public void setUserId(Integer user_id) {
        this.userId = user_id;
    }


    @OneToOne(cascade = CascadeType.ALL) // 相互关联，共同增删改查
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UserVerify getUserVerify() { return userVerify; }

    public void setUserVerify(UserVerify userVerify) { this.userVerify = userVerify; }
}
