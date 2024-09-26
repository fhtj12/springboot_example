package com.example.web.service;

import com.example.web.exception.ServiceException;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = {RuntimeException.class, Exception.class, ServiceException.class})
public abstract class AbstractService {

}
