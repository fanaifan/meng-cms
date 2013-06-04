package utils;

import java.util.List;


/**
 * 发送工具类
 * 1.发送邮件
 * 2.发送短信息
 * 3.发送站内信
 * @author zhangpeng
 *
 */
public class SendUtils {
	
	/**
	 * 发送邮件(单封)
	 * @param email
	 * @param subject
	 * @param content
	 */
	public static void mail(String email, String subject, String content){
		
	}
	
	/**
	 * 发送电子邮件(批量)
	 * @param email
	 * @param subject
	 * @param content
	 */
	public static void mail(List<String> email, String subject, String content){
		
	}
	
	/**
	 * 发送电子邮件-激活用户
	 * @param email
	 * @param activecode
	 */
	public static void mail_activeu_ser(String email, String activecode){
		
	}
	
	/**
	 * 发送电子邮件-验证邮箱
	 * @param email
	 */
	public static void mail_verify_email(String email, String verifycode){
		
	}
}
