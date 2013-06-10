package utils;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 字符串工具类
 * @author zhangpeng
 *
 */
public class StringUtils {

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
	
	/**
	 * Meng-CMS专用系统唯一标识码
	 * @return
	 */
	public static String getMengCode(){
		//TODO根据此码查询系统信息
		return RandomStringUtils.randomNumeric(32);
	}
	
	/**
	 * 获取标准时间
	 * @return
	 */
	public static String getStanderDate(){
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
	}
	
	public static void main(String[] args){
		System.out.println(getStanderDate());
	}
	
}
