package com.api.energymarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.energymarket.models.CollectPricesModel;

public interface CollectPricesRepository extends JpaRepository<CollectPricesModel, Integer> {

}