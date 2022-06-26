package com.api.energymarket.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.api.energymarket.models.CollectPricesModel;
import com.api.energymarket.repositories.CollectPricesRepository;

@Service
public class CollectPricesService {

  final CollectPricesRepository collectPricesRepository;

  public CollectPricesService(CollectPricesRepository collectPricesRepository) {
    this.collectPricesRepository = collectPricesRepository;
  }

  @Transactional
  public CollectPricesModel save(CollectPricesModel collectPricesModel) {
    return collectPricesRepository.save(collectPricesModel);
  }

  public List<CollectPricesModel> getAllPrices() {
    return collectPricesRepository.findAll();
  }
}
