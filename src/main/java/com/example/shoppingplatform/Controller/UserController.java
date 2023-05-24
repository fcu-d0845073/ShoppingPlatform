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

@RequestMapping(path = "/User")
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/SearchAccount")
    @ResponseBody
    public Iterable<User> searchAccount() {
        //localhost:8080/User/SearchAccount
        return userRepository.findAll();
    }

    @RequestMapping(path = "/AddAccount")
    @ResponseBody
    public boolean addAccount(@RequestParam String account, @RequestParam String password) {
        //localhost:8080/User/AddAccount?account=d0845073&password=123
        if (account.isEmpty())
            return false;
        if (password.isEmpty())
            return false;
        if (userRepository.findByAccount(account).size() == 0) {
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
        //localhost:8080/User/UpdateAccount?account=d0845073&oldPassword=123&newPassword=234
        if (account.isEmpty())
            return false;
        if (oldPassword.isEmpty())
            return false;
        if (newPassword.isEmpty())
            return false;
        List<User> list = userRepository.findByAccount(account);
        if (list.size() == 0)
            return false;
        if (list.get(0).getPassword().equals(oldPassword)) {
            list.get(0).setPassword(newPassword);
            userRepository.save(list.get(0));
            return true;
        }
        return false;
    }

    @RequestMapping(path = "/login")
    @ResponseBody
    public boolean login(@RequestParam String account, @RequestParam String password) {
        //localhost:8080/User/login?account=d0845073&password=123
        if (account.isEmpty())
            return false;
        if (password.isEmpty())
            return false;
        List<User> list = userRepository.findByAccount(account);
        if (list.size() == 0)
            return false;
        System.out.println(list.get(0).getPassword());
        if (list.get(0).getPassword().equals(password))
            return true;
        return false;
    }

}
