package com.wmeup.photoshare.test.provider;

import com.alibaba.fastjson.JSON;
import com.wmeup.photoshare.api.config.ConfigProviderService;
import com.wmeup.photoshare.api.config.bo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zy on 2016/8/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-applicationContext-provider.xml"})
public class ConfigServiceTest {
    @Autowired
    private ConfigProviderService configProviderService;

    @Test
    public void isBankAvailableTest(){
        BankAvailReq req = new BankAvailReq();
        req.setBankCode("CCB");
        req.setVersion("1.0");
        req.setRequestId("123");
        req.setChannel("UMG");
        req.setSign("asdasd");
        req.setSignType("asdasd");
        BankAvailRsp rsp = configProviderService.isBankAvailable(req);
        System.out.println(JSON.toJSONString(rsp));
    }

    @Test
    public void addOneConfigTest(){
        AddOneConfigReq req = new AddOneConfigReq();
        req.setVersion("1.0");
        req.setRequestId("123");
        req.setChannel("UMG");
        req.setSign("asdasd");
        req.setSignType("asdasd");
        req.setFlag("upop_upgrade");
        req.setValue("ABCD");
        req.setDescription("llll");
        AddOneConfigRsp rsp = configProviderService.addOneConfig(req);
        System.out.println(JSON.toJSONString(rsp));
    }

    @Test
    public void delByIdTest(){
        DelConfigReq req = new DelConfigReq();
        req.setVersion("1.0");
        req.setRequestId("123");
        req.setChannel("UMG");
        req.setSign("asdasd");
        req.setSignType("asdasd");
       // req.setId(1653L);
        DelConfigRsp rsp = configProviderService.delById(req);
        System.out.println(JSON.toJSONString(rsp));
    }
    @Test
    public void updateByIdTest(){
        UpdateConfigReq req = new UpdateConfigReq();
        req.setVersion("1.0");
        req.setRequestId("123");
        req.setChannel("UMG");
        req.setSign("asdasd");
        req.setSignType("asdasd");
       // req.setId(1656L);
        req.setDescription("8888");
        UpdateConfigRsp rsp = configProviderService.updateById(req);
        System.out.println(JSON.toJSONString(rsp));
    }
    @Test
    public void queryConfigsByPageTest(){
        QryPageConfigsReq req = new QryPageConfigsReq();
        req.setVersion("1.0");
        req.setRequestId("123");
        req.setChannel("UMG");
        req.setSign("asdasd");
        req.setSignType("asdasd");
       // req.setFlag("upop_upgrad");
        QryPageConfigsRsp rsp = configProviderService.queryConfigsByPage(req);
        System.out.println(JSON.toJSONString(rsp));
    }

}
