package com.example.shoppingplatform.Dao;

import com.example.shoppingplatform.Model.CommodityRecord;
import org.springframework.data.repository.CrudRepository;
import java.util.*;

public interface CommodityRecordRepository extends CrudRepository<CommodityRecord, Integer> {
    List<CommodityRecord> findByPurchaseAccount(String purchaseAccount);
}
