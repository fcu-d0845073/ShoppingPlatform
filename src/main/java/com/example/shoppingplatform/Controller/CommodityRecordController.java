package com.example.shoppingplatform.Controller;

import com.example.shoppingplatform.Dao.CommodityRecordRepository;
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

@RequestMapping(path = "/CommodityRecord")
@Controller
public class CommodityRecordController {

    @Autowired
    private CommodityRecordRepository commodityRecordRepository;
    @Autowired
    private CommodityRepository commodityRepository;

    @RequestMapping(path = "/SearchCommodityRecord")
    @ResponseBody
    public List<CommodityRecord> searchCommodityRecord(@RequestParam String account) {
        if (account.isEmpty())
            return null;
        List<CommodityRecord> commodityRecordList = commodityRecordRepository.findByPurchaseAccount(account);
        if (commodityRecordList.get(0) == null)
            return null;
        for (CommodityRecord commodityRecord : commodityRecordList) {
            if (commodityRecord.getUsed() == true)
                commodityRecordList.remove(commodityRecord);
        }
        return commodityRecordList;
    }

    @RequestMapping(path = "/AddCommodityRecord")
    @ResponseBody
    public boolean addCommodityRecord(@RequestParam String account, @RequestParam int[] ids) {
        if (account.isEmpty())
            return false;
        if (ids.length == 0)
            return false;
        for (int id: ids) {
            CommodityRecord commodityRecord = new CommodityRecord();
            Commodity commodity = commodityRepository.findById(id).get();
            if (commodity == null)
                return false;
            commodityRecord.setPurchaseAccount(account);
            commodityRecord.setPurchaseCommodity(commodity);
            commodityRecord.setUsed(false);
        }
        return true;
    }

    @RequestMapping(path = "/Refund")
    @ResponseBody
    public boolean refund(@RequestParam String account, @RequestParam int id) {
        if (account.isEmpty())
            return false;
        List<CommodityRecord> commodityRecordList = commodityRecordRepository.findByPurchaseAccount(account);
        for (CommodityRecord commodityRecord : commodityRecordList) {
            if (commodityRecord.getId() == id) {
                commodityRecord.setUsed(true);
                commodityRecordRepository.save(commodityRecord);
                return true;
            }
        }
        return false;
    }
}
