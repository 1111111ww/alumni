package com.university.common.util;

import java.util.ArrayList;

/**
 * Created by songrenfei on 16/8/13.
 */
public class NoCheckUrlsUtils {

    /**
     * admin
     */
    public static String GenerateAdminNoCheckUrls(){
        String noCheckUrls = "";
        ArrayList<String> list = new ArrayList<>();
        list.add("/user/login.do");                 //打开登录页面
        list.add("/user/createCaptcha.do");         //生成验证码
        list.add("/user/checkCaptcha.do");          //验证图形验证码
        list.add("/user/sendMobileCode.do");        //发送手机验证码
        list.add("/user/loginIn.do");               //登陆(带图形验证码)
        list.add("/user/findPassword.do");          //忘记密码重置密码

        list.add("/match/setTakeTime.do");          //计算选手比赛用时
        list.add("/match/setRanking.do");           //计算选手比赛排名

        list.add("/test/template.do");              //测试
        list.add("/server/alive.do");               //测试服务器是否正常运行

        noCheckUrls = NoCheckUrlsUtils.arrayToString(list);

        return noCheckUrls;
    }

    /**
     * client
     */
    public static String GenerateClientNoCheckUrls(){
        String noCheckUrls = "";
        ArrayList<String> list = new ArrayList<>();
        list.add("/user/clientRegisterUserPage.do");    //用户注册页面-[pc]
        list.add("/user/register.do");                  //用户注册-[pc]
        list.add("/user/login.do");                     //登录页面-[pc]
        list.add("/user/sendMobileCode.do");            //发送手机验证码-[app|pc]
        list.add("/user/createUser.do");                //创建用户-[app|pc]
        list.add("/user/createCaptcha.do");             //生成验证码-[pc]
        list.add("/user/checkCaptcha.do");              //验证图形验证码-[pc]
        list.add("/user/baseLoginIn.do");               //基础登陆(无图形验证码)-[app]
        list.add("/user/loginIn.do");                   //登陆(带图形验证码)-[pc]
        list.add("/user/findPassword.do");              //忘记密码重置密码-[app|pc]
        list.add("/user/clientFindPasswordPage.do");    //忘记密码重置密码页面-[pc]
        list.add("/user/protocolPage.do");              //用户服务协议-[pc]

        list.add("/match/getCasinoEventListPageByMatchTypeForClient.do");    //获取比赛列表（前台-体验赛）

        list.add("/userService/checkUserToken.do");     //考试系统验证token-[app|pc]

        list.add("/test/template.do");                  //测试
        list.add("/server/alive.do");                   //测试服务器是否正常运行
        //list.add("/check/createCheckToken.do");       //创建token
        list.add("/phone/phone.do");                    //下载app

        noCheckUrls = NoCheckUrlsUtils.arrayToString(list);

        return noCheckUrls;
    }

    /**
     * race
     */
    public static String GenerateRaceNoCheckUrls(){
        String noCheckUrls = "";
        ArrayList<String> list = new ArrayList<>();
        list.add("/user/login.do");                     //打开登录页面
        list.add("/matchService/checkUser.do");         //检查用户名和密码是否合法
        list.add("/matchService/checkToken.do");        //检查token是否合法返回people_id
        list.add("/matchService/getPeopleInfo.do");     //获取用户信息

        list.add("/test/template.do");                  //测试
        list.add("/server/alive.do");                   //测试服务器是否正常运行

        noCheckUrls = NoCheckUrlsUtils.arrayToString(list);

        return noCheckUrls;
    }

    public static String arrayToString(ArrayList<String> list){
        String str = "";
        if(list!=null && list.size()>0){
            for (int i = 0; i < list.size(); i++){
                if(i==0){
                    str = str + list.get(i);
                }else{
                    str = str + ";" + list.get(i);
                }
            }
        }
        return str;
    }

    public static void main(String[] args){
        System.out.println(NoCheckUrlsUtils.GenerateAdminNoCheckUrls());
        System.out.println(NoCheckUrlsUtils.GenerateClientNoCheckUrls());
        System.out.println(NoCheckUrlsUtils.GenerateRaceNoCheckUrls());
    }
}
