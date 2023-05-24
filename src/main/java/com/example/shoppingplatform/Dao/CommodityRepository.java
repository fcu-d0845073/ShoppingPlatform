package com.example.shoppingplatform.Dao;

import com.example.shoppingplatform.Model.Commodity;
import org.springframework.data.repository.CrudRepository;
import java.util.*;

public interface CommodityRepository extends CrudRepository<Commodity, Integer> {
    List<Commodity> findByClassficationAndAttribute(String classfication, String attribute);
    List<Commodity> findByName(String name);
}
