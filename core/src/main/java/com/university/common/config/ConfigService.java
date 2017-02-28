package com.university.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by leo on 16/4/16.
 */
@Component("configService")
@PropertySource(value = "classpath:config/common.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/database.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/email.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/sms.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/menu.properties", ignoreResourceNotFound = true)
public class ConfigService {

    @Autowired
    private Environment env;

    public String getPropertyValue(String key) {
        return env.getProperty(key);
    }


    //******************** email config start ********************

    public String getSmtpEmail() {
        return env.getProperty("smtpEmail");
    }

    public String getSmtpHostName() {
        return env.getProperty("smtpHostName");
    }

    public String getSmtpPass() {
        return env.getProperty("smtpPass");
    }

    //******************** email config start ********************


    //******************** host config start ********************

    public String getHost() {
        return env.getProperty("host");
    }

    //******************** host config end ********************


    //******************** sms config start ********************

    public String getSMSDisabled() {
        return env.getProperty("sms.disabled");
    }

    public String getSMSUrl() {
        return env.getProperty("sms.url");
    }

    public String getSMSAppKey() {
        return env.getProperty("sms.appKey");
    }

    public String getSMSAppSecret() {
        return env.getProperty("sms.appSecret");
    }

    public String getSMSSignName() {
        return env.getProperty("sms.signName");
    }

    public String getSMSTemplateCode() {
        return env.getProperty("sms.templateCode");
    }

    //******************** sms config end ********************


    //******************** page config start ********************

    public String getPageSize() {
        return env.getProperty("pageSize");
    }

    //******************** page config end ********************


    //******************** examHost config start ********************

    public String getExamHost() {
        return env.getProperty("examHost");
    }

    //******************** examHost config end ********************


    //******************** adminHost config start ********************

    public String getAdminHost() {
        return env.getProperty("adminHost");
    }

    //******************** adminHost config end ********************


    //******************** match start end time ********************

    public String getMatchStartTime(){
        return env.getProperty("match-start-time");
    }

    public String getMatchEndTime(){
        return env.getProperty("match-end-time");
    }

    public String getMatchStartTime_weiqi(){
        return env.getProperty("match-start-time.weiqi");
    }

    public String getMatchEndTime_weiqi(){
        return env.getProperty("match-end-time.weiqi");
    }

    //******************** match start end time ********************


    public String getMenuOrderStr(Integer roleType){
        String orderStr = "";

        switch (roleType){
            case 1 :
                orderStr = env.getProperty("menuOrder.super");
                break;
            case 2 :
                orderStr = env.getProperty("menuOrder.admin");
                break;
            case 3 :
                orderStr = env.getProperty("menuOrder.user");
                break;
        }

        return orderStr;
    }
}
