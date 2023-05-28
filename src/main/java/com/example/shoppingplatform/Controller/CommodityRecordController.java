package com.example.shoppingplatform.Controller;

import com.example.shoppingplatform.Dao.CommodityRecordRepository;
import com.example.shoppingplatform.Dao.CommodityRepository;
import com.example.shoppingplatform.Dao.UserRepository;
import com.example.shoppingplatform.Model.Commodity;
import com.example.shoppingplatform.Model.CommodityRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.lang.*;

@RequestMapping(path = "/CommodityRecord")
@CrossOrigin
@Controller
public class CommodityRecordController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommodityRecordRepository commodityRecordRepository;
    @Autowired
    private CommodityRepository commodityRepository;

    @RequestMapping(path = "/SearchCommodityRecord")
    @ResponseBody
    public List<Commodity> searchCommodityRecord(@RequestParam String account) {
        //localhost:8080/CommodityRecord/SearchCommodityRecord?account=d0845073
        if (account.isEmpty())
            return null;
        List<CommodityRecord> commodityRecordList = commodityRecordRepository.findByPurchaseAccount(account);
        if (commodityRecordList.size() == 0)
            return null;
        String[] ids = commodityRecordList.get(0).getPurchaseCommodity().split(",");
        List<Commodity> commodityList = new ArrayList<Commodity>();
        for (int i = 0; i < ids.length; i++) {
            if (!ids[0].equals(""))
                commodityList.add(commodityRepository.findById(Integer.parseInt(ids[i])).get());
        }
        return commodityList;
    }

    public boolean addCommodityRecord(String account, int[] ids) {
        if (account.isEmpty())
            return false;
        if (ids.length == 0)
            return false;
        for (int id: ids) {
            CommodityRecord commodityRecord = new CommodityRecord();
            Commodity commodity = commodityRepository.findById(id).get();
            if (commodity == null)
                return false;
            List<CommodityRecord> list = commodityRecordRepository.findByPurchaseAccount(account);
            if (list.size() == 0) {
                commodityRecord.setPurchaseAccount(account);
                commodityRecord.setPurchaseCommodity(commodity.getId() + ",");
                commodityRecordRepository.save(commodityRecord);
            } else {
                commodityRecord = list.get(0);
                commodityRecord.setPurchaseCommodity(commodityRecord.getPurchaseCommodity() + commodity.getId() + ",");
                commodityRecordRepository.save(commodityRecord);
            }
        }
        return true;
    }

    @RequestMapping(path = "/Refund")
    @ResponseBody
    public boolean refund(@RequestParam String account, @RequestParam int id) {
        //localhost:8080/CommodityRecord/Refund?account=d0845073&id=1
        if (account.isEmpty())
            return false;
        if (id <= 0)
            return false;
        if (userRepository.findByAccount(account).size() == 0)
            return false;
        CommodityRecord commodityRecord = commodityRecordRepository.findByPurchaseAccount(account).get(0);
        String[] ids = commodityRecord.getPurchaseCommodity().split(",");
        String newPurchaseCommodity = new String();
        for (int i = 0; i < ids.length; i++) {
            if (Integer.parseInt(ids[i]) == id) {
                ids[i] = ids[ids.length - 1];
                break;
            }
        }
        for (int i = 0; i < ids.length - 1; i++) {
            newPurchaseCommodity = ids[i] + "," + newPurchaseCommodity;
        }
        if (ids.length == 1) {
            commodityRecord.setPurchaseCommodity("");
        } else {
            commodityRecord.setPurchaseCommodity(newPurchaseCommodity);
        }
        commodityRecordRepository.save(commodityRecord);
        return true;
    }
}
