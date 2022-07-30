package com.example.cachemanager;

import com.service.model.Student;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/23 21:35
 */
@Service
public class CacheTest {

    @Cacheable(cacheNames = "string")
    public String setCache() {
        System.out.println("加载缓存");
        return "success1";
    }

    @CachePut(cacheNames = "student")
    public Student getStudent() {
        System.out.println("写入缓存");
        Student student = new Student();
        student.setStuId("101");
        student.setSexType("male");
        student.setPassPort("123456");
        return student;
    }

}
