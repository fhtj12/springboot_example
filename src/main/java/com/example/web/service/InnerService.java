package com.example.web.service;

import com.example.web.dao.ITestDao;
import com.example.web.exception.ServiceException;
import com.example.web.model.ProtoMap;
import com.example.web.util.CUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InnerService extends AbstractService {

    private ITestDao testDao;

    @Autowired
    public void setTestDao(ITestDao testDao) {
        this.testDao = testDao;
    }

    public ProtoMap getTestCode() throws ServiceException {
        try {
            String code = testDao.getTestCode();
            ProtoMap outMap = new ProtoMap();
            if (code.startsWith("TRF")) {
                outMap.put("CODE", "dev");
            } else {
                outMap.put("CODE", "prod");
            }
            return outMap;
        } catch (ServiceException e) {
            log.error("Service Error-{}", CUtil.exceptionToStringShort(e));
            throw e;
        } catch (Exception e) {
            log.error("Normal Error-{}", CUtil.exceptionToString(e));
            throw new ServiceException("CM9999");
        }
    }
}
