import java.security.MessageDigest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class md5test {
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
	public static byte[] md5encode(byte[] content){
		byte[] digestedValue = null;
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(content);
			digestedValue = md.digest();
		}catch(Exception e){
			e.printStackTrace();
		}
		return digestedValue;
	}
	
	public static void main(String args[]){
		//测试BASE64加密
		System.out.println("------------------------------------");
		String str = "wlj1234";
		String ret = null;
		ret = base64encode(str);
		System.out.println("加密前:"+str+" \n加密后:"+ret);
		 
		//测试BASE64解密
		System.out.println("------------------------------------");
		String str1 = ret;
		ret = base64decode(ret);
	    System.out.println("解密前:"+str1+"\n 解密后:"+ret);
	    
	    //测试MD5加密，MD5是不可逆置的
	    System.out.println("------------------------------------");
	    String str2 = str;
	    byte[] ret1 = null;
	    ret1 = md5encode(str2.getBytes());	
	    System.out.println("解密前:"+str2+" \n解密后:"+new String(ret1));
	     
	    //将MD5和BASE64结合起来使用
	    System.out.println("------------------------------------");
	    String str3 = str;
	    byte[] md5str = md5encode(str3.getBytes());
	    String temp = new String(md5str);
	    String basestr1 = base64encode(temp);
	    System.out.println("MD5加密后:"+temp+"\nBASE64加密后:"+basestr1);
	    String basestr2 = base64decode(basestr1);
	    System.out.println("BASE64加密后:"+basestr1+"\nBASE64解密后:"+basestr2);
	    System.out.println("------------------------------------");
	}
}