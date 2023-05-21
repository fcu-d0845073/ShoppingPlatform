package com.example.shoppingplatform.Controller;

import com.example.shoppingplatform.Dao.CommodityRepository;
import com.example.shoppingplatform.Model.Commodity;
import com.example.shoppingplatform.Model.CommodityRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.lang.*;

@RequestMapping(path = "/Commodity")
@Controller
public class CommodityController {

    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private CommodityRecordController commodityRecordController;

    @RequestMapping(path = "/SearchCommodity")
    @ResponseBody
    public List<Commodity> searchCommodity(@RequestParam String name, @RequestParam String classfication, @RequestParam String attribute) {
        if (!name.isEmpty()) {
            return commodityRepository.findByName(name);
        } else if (!classfication.isEmpty() && !attribute.isEmpty()) {
            return commodityRepository.findByClassfiationAndAttribute(classfication, attribute);
        }
        return null;
    }

    @RequestMapping(path = "/PurchaseCommodity")
    @ResponseBody
    public boolean purchaseCommodity(@RequestParam int[] ids, @RequestParam int payModel, @RequestParam int commodityPlace, @RequestParam String account) {
        if (ids.length == 0)
            return false;
        if (!(payModel >= 1 && payModel <= 3))
            return false;
        if (!(commodityPlace >= 1 && commodityPlace <= 5))
            return false;
        List<Integer> storeIdForLaterUsed = new ArrayList<Integer>();
        for (int id : ids) {
            Commodity commodity = commodityRepository.findById(id).get();
            if (commodity == null)
                return false;
            int quantity = commodity.getQuantity();
            if (quantity >= 1) {
                storeIdForLaterUsed.add(id);
            } else
                return false;
        }
        for (int id : storeIdForLaterUsed) {
            Commodity commodity = commodityRepository.findById(id).get();
            commodity.setQuantity(commodity.getQuantity() - 1);
        }
        int[] idsArray = new int[storeIdForLaterUsed.size()];
        for (int i = 0; i < idsArray.length; i++)
            idsArray[i] = storeIdForLaterUsed.get(i);
        commodityRecordController.addCommodityRecord(account, idsArray);
        return true;
    }


}
