package com.example.shoppingplatform.Controller;

import com.example.shoppingplatform.Model.User;
import com.example.shoppingplatform.Dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.lang.*;

@RequestMapping(path = "/user")
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    @RequestMapping(path = "/AddAccount")
    @ResponseBody
    public boolean addAccount(@RequestParam String account, @RequestParam String password) {
        if (account.isEmpty())
            return false;
        if (password.isEmpty())
            return false;
        if (userRepository.findByAccount(account).get(0) == null) {
            User user = new User();
            user.setAccount(account);
            user.setPassword(password);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @RequestMapping(path = "/UpdateAccount")
    @ResponseBody
    public boolean updateAccount(@RequestParam String account, @RequestParam String oldPassword, @RequestParam String newPassword) {
        if (account.isEmpty())
            return false;
        if (oldPassword.isEmpty())
            return false;
        if (newPassword.isEmpty())
            return false;
        User user = userRepository.findByAccount(account).get(0);
        if (user == null)
            return false;
        if (user.getPassword() == oldPassword) {
            user.setPassword(newPassword);
            userRepository.save(user);
        }
        return false;
    }

    @RequestMapping(path = "/login")
    @ResponseBody
    public boolean login(@RequestParam String account, @RequestParam String password) {
        if (account.isEmpty())
            return false;
        if (password.isEmpty())
            return false;
        User user = userRepository.findByAccount(account).get(0);
        if (user == null)
            return false;
        if (user.getAccount() == account && user.getPassword() == password)
            return true;
        return false;
    }

}
