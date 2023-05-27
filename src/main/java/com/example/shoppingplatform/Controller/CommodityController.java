package com.example.shoppingplatform.Controller;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.apache.commons.io.IOUtils;

import java.util.*;
import java.lang.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RequestMapping(path = "/Commodity")
@CrossOrigin
@Controller
public class CommodityController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private CommodityRecordController commodityRecordController;

    private byte[] getResourceFileBytesByClassPathResource(String pictureName) throws IOException {
        Resource resource = new ClassPathResource("static/" + pictureName);
        InputStream in = resource.getInputStream();
        return IOUtils.toByteArray(in);
    }

    @RequestMapping(path = "/InitCommodity")
    @ResponseBody
    public boolean initCommodity() throws IOException {
        //localhost:8080/Commodity/InitCommodity
        String[] classfication = {"Book", "Cd"};
        String[] attribute = {"Education", "Love", "Terrible", "Yt"};
        for (int i = 0; i < 4; i++) {
            Commodity commodity = new Commodity();
            commodity.setName(Integer.toString(i));
            commodity.setPrice(i);
            commodity.setQuantity(i + 1);
            commodity.setClassfication("Book");
            commodity.setAttribute(attribute[i]);
            byte[] bytes = null;
            bytes = getResourceFileBytesByClassPathResource(attribute[i] + ".JPG");
            commodity.setPicture(bytes);
            commodityRepository.save(commodity);
        }
        return true;
    }

    @RequestMapping(path = "/SearchCommodity")
    @ResponseBody
    public List<Commodity> searchCommodity(@RequestParam String name, @RequestParam String classfication, @RequestParam String attribute) {
        //localhost:8080/Commodity/SearchCommodity?name=&classfication=&attribute=
        //localhost:8080/Commodity/SearchCommodity?name=&classfication=Book&attribute=love
        //localhost:8080/Commodity/SearchCommodity?name=&classfication=Book&attribute=terrible
        //localhost:8080/Commodity/SearchCommodity?name=1&classfication=&attribute=
        if (!name.isEmpty()) {
            return commodityRepository.findByName(name);
        } else if (!classfication.isEmpty() && !attribute.isEmpty()) {
            return commodityRepository.findByClassficationAndAttribute(classfication, attribute);
        } else {
            Iterable<Commodity> iterable = commodityRepository.findAll();
            List<Commodity> result = new ArrayList<Commodity>();
            iterable.forEach(result::add);
            return result;
        }
    }

    @RequestMapping(path = "/PurchaseCommodity")
    @ResponseBody
    public boolean purchaseCommodity(@RequestParam int[] ids, @RequestParam int payModel, @RequestParam int commodityPlace, @RequestParam String account) {
        //localhost:8080/User/AddAccount?account=d0845073&password=123
        //localhost:8080/Commodity/InitCommodity
        //localhost:8080/Commodity/PurchaseCommodity?ids=1&payModel=1&commodityPlace=1&account=d0845073
        //localhost:8080/Commodity/PurchaseCommodity?ids=1&ids=2&payModel=1&commodityPlace=1&account=d0845073 multiple commodity
        if (ids.length == 0)
            return false;
        if (!(payModel >= 1 && payModel <= 3))
            return false;
        if (!(commodityPlace >= 1 && commodityPlace <= 5))
            return false;
        if (userRepository.findByAccount(account).size() == 0)
            return false;
        List<Integer> storeIdForLaterUsed = new ArrayList<Integer>();
        for (int id : ids) {
            Optional<Commodity> optional = commodityRepository.findById(id);
            if (!optional.isPresent())
                return false;
            Commodity commodity = optional.get();
            int quantity = commodity.getQuantity();
            if (quantity >= 1) {
                storeIdForLaterUsed.add(id);
            } else
                return false;
        }
        for (int id : storeIdForLaterUsed) {
            Commodity commodity = commodityRepository.findById(id).get();
            commodity.setQuantity(commodity.getQuantity() - 1);
            commodityRepository.save(commodity);
        }
        int[] idsArray = new int[storeIdForLaterUsed.size()];
        for (int i = 0; i < idsArray.length; i++)
            idsArray[i] = storeIdForLaterUsed.get(i);
        commodityRecordController.addCommodityRecord(account, idsArray);
        return true;
    }


}
