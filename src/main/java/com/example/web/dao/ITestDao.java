package com.example.web.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ITestDao {

    String getTestCode() throws Exception;

}
