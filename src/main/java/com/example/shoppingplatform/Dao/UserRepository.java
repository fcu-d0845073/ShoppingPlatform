package com.example.shoppingplatform.Dao;

import com.example.shoppingplatform.Model.User;
import org.springframework.data.repository.CrudRepository;
import java.util.*;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByAccount(String account);
}
