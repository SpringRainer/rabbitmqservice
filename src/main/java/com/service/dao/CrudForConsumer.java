package com.service.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface CrudForConsumer {
    int insertInformation(String messageContent);
}
