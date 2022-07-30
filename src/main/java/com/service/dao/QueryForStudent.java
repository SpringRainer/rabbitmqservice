package com.service.dao;

import com.service.model.PaperInformation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryForStudent {
    List<PaperInformation> queryAllInformation();
}
