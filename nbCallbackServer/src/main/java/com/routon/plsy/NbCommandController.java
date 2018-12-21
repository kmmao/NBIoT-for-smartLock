package com.routon.plsy;


import com.routon.plsy.authorization.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.routon.plsy.service.NbCommandService;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author : wangxiwei
 */
@RestController
public class NbCommandController {

    private static Logger logger = LoggerFactory.getLogger(NbCommandController.class);

    private static ConcurrentHashMap<String, String> userInfo = new ConcurrentHashMap<>();

    private static RSAPrivateKey privateKey = null;

    private static RSAPublicKey publickey = null;

    static {
        userInfo.put("43A6AA5473AE4D8BB05ED89E8C692B90", "87839397994A45DC8A9F350D838A8270");
        KeyPair keyPair = null;
        try {
            keyPair = RSAUtil.loadKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }

    @Autowired
    private NbCommandService nbCommandService;

    @RequestMapping(value ="/nbiot/sendWhiteNames")
    @ResponseBody public String sendWhiteNames(@RequestParam(required = true) String deviceId, @RequestParam(required = true) int personId,
                                               @RequestParam(required = true) String authority, @RequestParam(required = true) String card1,
                                               String startTime, String endTime) throws Exception{
        logger.info(">>>>>>下发白名单指令");
        int defaultTag = 1;
        if(startTime == null || endTime == null){
            defaultTag = 0;
        }
        String result = nbCommandService.sendWhiteNames(deviceId, personId, authority, card1, startTime, endTime, defaultTag);
        return result;

    }

    @RequestMapping(value ="/nbiot/delWhiteNames")
    @ResponseBody public String delWhiteNames(@RequestParam(required = true) String delsel, String personId, @RequestParam(required = true) String deviceId) throws Exception{
        logger.info(">>>>>>下发删除白名单指令");
        String result = nbCommandService.delWhiteNames(delsel, personId, deviceId);
        return result;

    }

    @RequestMapping(value ="/nbiot/openAndClose")
    @ResponseBody public String openAndClose(@RequestParam(required = true) int personId, @RequestParam(required = true) String operaCode,
                                             String phone, @RequestParam(required = true) String deviceId) throws Exception{
        logger.info(">>>>>>下发开关门操作指令");
        String result = nbCommandService.openAndClose(personId, operaCode, phone, deviceId);
        return result;

    }

    @RequestMapping(value ="/nbiot/lock")
    @ResponseBody public String lock(@RequestParam(required = true) String deviceId, @RequestParam(required = true) String lockStatus) throws Exception{
        logger.info(">>>>>>下发远程开锁指令");
        String result = nbCommandService.lock(deviceId, lockStatus);
        return result;

    }

    @RequestMapping(value ="/nbiot/clearRecord")
    @ResponseBody public String clearRecord(@RequestParam(required = true) String deviceId) throws Exception{
        logger.info(">>>>>>下发清除开门记录指令");
        String result = nbCommandService.clearRecord(deviceId);
        return result;

    }

    @RequestMapping(value ="/nbiot/synTime")
    @ResponseBody public String synTime(@RequestParam(required = true) String deviceId) throws Exception{
        logger.info(">>>>>>下发同步网络时间指令");
        String result = nbCommandService.synTime(deviceId);
        return result;
    }

    @RequestMapping(value ="/nbiot/login")
    @ResponseBody public String reqToken(String appId, String secretCiphertext){
        KeyPair keyPair = null;
        try {

            byte[] originText = RSAUtil.decrypt(privateKey, secretCiphertext.getBytes());
            String originSecret = new String(originText);
            String userSecret = userInfo.get(appId);
            if(userSecret != null){
                if(userSecret == originSecret){

                } else {
                    return "鉴权失败";
                }
            } else {
                return "未注册";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}