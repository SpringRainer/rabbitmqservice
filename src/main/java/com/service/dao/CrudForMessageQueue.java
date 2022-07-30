package com.service.dao;

import com.service.model.MessageTemp;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrudForMessageQueue {
    int insertInformation(MessageTemp messageTemp);
    List<MessageTemp> queryAllInformation();
}
