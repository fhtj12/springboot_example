package com.example.web.dao;

import com.example.web.model.ProtoMap;
import com.example.web.model.QueryMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ICommonDao {

    String getSysPropValue(String propCd);
    ProtoMap getSysProp(QueryMap queryMap);

}
