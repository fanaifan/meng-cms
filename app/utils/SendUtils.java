package utils;

import java.util.List;

import models.user.User;
import models.user.VerifyCode;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;


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
		Email mail = new SimpleEmail();
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
	 * 保存生成出来的验证码
	 * @param userinfo
	 * @param type A:active_code E:email_code M:mobile_code
	 * @return
	 */
	private static String saveVerifyCode(String userinfo,String type){
		
		String code = StringUtils.get_verifycode();
		VerifyCode vc = new VerifyCode();
		if(type.equals("A")){
			vc.username = User.getUserByEmail(userinfo).username;
			vc.active_code = code;
		}
		if(type.equals("E")){
			vc.username = User.getUserByEmail(userinfo).username;
			vc.verify_email_code = code;
		}
		if(type.equals("M")){
			vc.username = User.getUserByMobile(userinfo).username;
			vc.verify_mobile_code = code;
		}
		VerifyCode.saveVerifyCode(vc);
		return code;
	}
	
	/**
	 * 发送电子邮件-激活用户
	 * @param email
	 * @param activecode
	 */
	public static void mail_active_user(String email){
		String code = saveVerifyCode(email,"A");
		//TODO 模板发送code
	}
	
	/**
	 * 发送电子邮件-验证邮箱
	 * @param email
	 */
	public static void mail_verify_email(String email){
		String code = saveVerifyCode(email,"E");
	}
	
	/**
	 * 发送短信息-验证移动电话
	 * @param mobile
	 */
	public static void sms_verify_mobile(String mobile){
		String code = saveVerifyCode(mobile,"M");
	}
}
