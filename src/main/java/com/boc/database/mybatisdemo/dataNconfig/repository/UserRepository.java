package com.boc.database.mybatisdemo.dataNconfig.repository;


import com.boc.database.mybatisdemo.dataNconfig.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//继承JpaRepository来完成对数据库的操作
public interface UserRepository extends JpaRepository<User,Integer> {
}
