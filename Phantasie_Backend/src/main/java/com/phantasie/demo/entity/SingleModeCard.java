package com.phantasie.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "single_Mode_Card")
public class SingleModeCard {
    @Id
    private int item_id;

    private int user_id;

    private int cardId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private SingleMode singleMode;



    public SingleMode getSingleMode(){
        return singleMode;
    }


}
