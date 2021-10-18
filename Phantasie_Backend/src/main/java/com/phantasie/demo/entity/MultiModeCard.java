package com.phantasie.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class MultiModeCard {
    @Id
    private int item_id;

    private int user_id;

    private int cardId;

    private Card card;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private MultiMode multiMode;
}
