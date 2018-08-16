package com.glodon.tot.util;

import java.security.MessageDigest;
import java.util.Date;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author Crawn 加解密生成token
 */
public class TokenGenerater {
	private static BASE64Encoder encoder = new BASE64Encoder();
	private static BASE64Decoder decoder = new BASE64Decoder();
	/**
	 *	加密：
	 * @param content 要加密内容
	 * @return token String
	 */
	public static String base64encode(String content){
		try{
			String token = encoder.encode(content.getBytes());
			return token;
		}catch(Exception e){
			return content;
		}
	}
	
	/**
	 * 解密
	 * @param token 加密的数据
	 * @return String content 解密后的数据
	 */
	public static String base64decode(String token){
		try{
			String content = new String(decoder.decodeBuffer(token));
						
			return content;
		}catch(Exception e){
			return token;
		}
	}
	
	/**
	 * md5加密
	 * @param content 待加密数据
	 * @return byte[] 加密后的字符串
	 */
	public static String md5encode(String content){
		byte[] content1=content.getBytes();
		byte[] digestedValue = null;
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(content1);
			digestedValue = md.digest();
		}catch(Exception e){
			e.printStackTrace();
		}
		return new String(digestedValue);
	}

	/**
	 * 利用MD5加密信息后生成token
	 * @param content
	 * @return
	 */
	public static String makeToken(String content){
		return base64encode(md5encode(content)+","+new Date()+",3600");
	}
	/**
	 * 核对用户信息和token期限是否过期
	 * @param content
	 * @return
	 */


	public static int ccompareToken(String token,String content){
		String decodeToken=new String(base64decode(md5encode(content)));
		String encodeContent=md5encode(content);
		String[] contents=decodeToken.split(",");
		if (contents[0].equals(encodeContent)){
			if (new Date().getTime()-new Date(contents[1]).getTime()<Integer.valueOf(contents[2])){
				return 1;
			}else {
				return 2;
			}
		}
		return 0;
	}


	public static void main(String[] args){
		//测试BASE64加密
		System.out.println("------------------------------------");
		String str = "wlj1234";
		String ret = null;
		ret = base64encode(str);
		System.out.println("加密前:"+str+" \n加密后:"+ret);
		 
		//测试BASE64解密
		System.out.println("------------------------------------");
		String str1 = "VGh1IEF1ZyAxNiAwOTowMDowNCBDU1QgMjAxOCwwLGFkbWluLDM2MDA=";
		ret = base64decode(str1);
	    System.out.println("解密前:"+str1+"\n 解密后:"+ret);
	    
	    //测试MD5加密，MD5是不可逆置的
	    System.out.println("------------------------------------");
	    String str2 = str;
	    String ret1 = null;
	    ret1 = md5encode(str2);
	    System.out.println("解密前:"+str2+" \n解密后:"+ret1);
	     
	    //将MD5和BASE64结合起来使用
	    System.out.println("------------------------------------");
	    String str3 = str;
	    String md5str = md5encode(str3);
	    String temp = new String(md5str);
	    String basestr1 = base64encode(temp);
	    System.out.println("MD5加密后:"+temp+"\nBASE64加密后:"+basestr1);
	    String basestr2 = base64decode(basestr1);
	    System.out.println("BASE64加密后:"+basestr1+"\nBASE64解密后:"+basestr2);
	    System.out.println("------------------------------------");
		//将MD5和BASE64结合起来使用
		String token=makeToken("crawn101");
		System.out.println("BASE64加密后:"+token);
		String content = base64decode(token);
		System.out.println("BASE64加密后:"+token+"\nBASE64解密后:"+content);
		System.out.println("------------------------------------");
		System.out.print(new Date().getTime()-new Date().getTime());
	}
}