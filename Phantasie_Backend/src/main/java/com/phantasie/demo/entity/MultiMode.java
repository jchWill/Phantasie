package com.phantasie.demo.entity;

import javax.persistence.*;
import java.util.List;

public class MultiMode {
    @Id
    private int user_id;

    private User user;

    @OneToMany(mappedBy = "singleMode",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<MultiModeCard> cardList;//牌库



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public User getUser(){
        return user;
    }
}
