package com.university.common.util;
import java.security.MessageDigest;


public class SHA256 {    
    public static String encrypt(String str)
    {
    	String result="";
    	try
		{
    		MessageDigest md = MessageDigest.getInstance("SHA-256");
		    md.update(str.getBytes("GBK"));
		   
		    for(byte b:md.digest())
		    {
		    	result=result+b;
		    }
		} 
    	catch (Exception e)
    	{ 
		     e.printStackTrace();
		}
	    return result;
    }		
    public static void main(String[] args) {
    	System.out.println(encrypt("imsa@W#E$R%T"));
	}
}