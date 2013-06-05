package utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;



/**
 * 字符串工具类
 * @author zhangpeng
 *
 */
public class StringUtils {

	public static void main(String[] args){
		System.out.println(get_verifycode());
	}
	/**
	 * md5加密
	 * @param str
	 * @return
	 */
	public static String md5(String str){
		return DigestUtils.md5Hex(str);
	}
	
	/**
	 * 获得用户激活码
	 * @return
	 */
	public static String get_verifycode(){
		return RandomStringUtils.randomAlphabetic(10);
	}
}
