package com.university.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by songrenfei on 16/7/15.
 */
public class BaseUtils {

    // 验证手机号 ok
    public static boolean isMobile(String str) {
        boolean bl = false;
        if (str==null || "".equals(str.trim()) || str.length()!=11){
            return bl;
        }
        Pattern p = Pattern.compile("^[1][0-9]{10}$");
        Matcher m = p.matcher(str);
        bl = m.matches();
        return bl;
    }

    // 验证邮箱 ok
    public static boolean isEmail(String email){
        boolean bl = false;
        if (email==null || "".equals(email.trim())){
            return bl;
        }
        Pattern p = Pattern.compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
        Matcher m = p.matcher(email);
        bl = m.matches();
        return bl;
    }

    //转json ok
    public static String toJsonFromArray(Object obj){
        if(obj!=null){
            return JSONArray.fromObject(obj).toString();
        }else{
            return "";
        }

    }

    //转json ok
    public static String toJsonFromObject(Object obj){
        if(obj!=null){
            return JSONObject.fromObject(obj).toString();
        }else{
            return "";
        }

    }


    public static Date formatDateStr(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;

        if(dateStr != null && !"".equals(dateStr.trim())){
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return date;
    }

    public static String formatDateToStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = "";

        if(date != null){
            try {
                dateStr = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return dateStr;
    }

    public static void main(String[] args){
        boolean bl = BaseUtils.isMobile("18811211318");
        System.out.println(bl);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("a",1);
        map.put("b",2);
        map.put("c",3);
        System.out.println(BaseUtils.toJsonFromArray(map));

        boolean bl_email = BaseUtils.isEmail("songrf@1.");
        System.out.println("bl_email = "+bl_email);

        System.out.println(BaseUtils.formatDateStr("2016-08-12 ***16:25:00").toLocaleString());
        System.out.println(BaseUtils.formatDateStr("2016-08-12 16:25:00").toGMTString());
        System.out.println(BaseUtils.formatDateStr("2016-08-12 16:25:00").getTime());
    }

    //效验是否是纯数字
    public static  boolean checkNumber(String number) {
        String result ="";

        boolean bl = number.matches("[0-9]+");
        if (bl == true) {
            result="该字符串是纯数字";
        } else {
            result="该字符串不是纯数字";
        }
        return bl;

    }
}
