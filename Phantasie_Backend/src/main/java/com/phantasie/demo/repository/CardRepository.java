package com.phantasie.demo.repository;

import com.phantasie.demo.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface CardRepository extends JpaRepository<Card, Integer>, JpaSpecificationExecutor<Card> {

}
