package com.phantasie.demo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "single_Mode")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler", "userVerify", "cardList", "orderList"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "user_id")
public class SingleMode {
    @Id
    private int user_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    private int level;

    private int exp;

    private int curHP;

    private int gold;

    private String mapRoute;

    @OneToMany(mappedBy = "singleMode",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<SingleModeCard> cardList;

//    TODO:为每个状态设计实体
//
//    TODO:为剧情进度设计实体


    public User getUser(){
        return user;
    }
}
