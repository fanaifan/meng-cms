package utils;

import java.util.List;

import models.User;
import models.VerifyCode;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import play.Logger;
import play.i18n.Messages;


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
	 * @throws EmailException 
	 */
	public static void mail(String uemail, String subject, String content) throws EmailException{
		Email email = new SimpleEmail();
		email.setHostName(AppConfig.Email_Host);
		email.setSmtpPort(AppConfig.Email_Port);
		email.setAuthenticator(new DefaultAuthenticator(AppConfig.Email_Username, AppConfig.Email_Password));
		email.setSSLOnConnect(true);
		email.setFrom(AppConfig.Email_Email,AppConfig.Eamil_ShowName);
		email.setSubject(subject);
		email.setCharset("utf-8");
		email.setMsg(content);
		email.addTo(uemail);
		email.send();
		//TODO 邮件发送日志
		//TODO 定期重发邮件

	}
	
	/**
	 * 发送电子邮件(批量)
	 * @param email
	 * @param subject
	 * @param content
	 * @throws EmailException 
	 */
	public static void mail(List<String> email, String subject, String content) throws EmailException{
		for(String uemail:email){
			mail(uemail, subject, content);
		}
	}
	
	/**
	 * 发送电子邮件带附件(单封)
	 * @param email
	 * @param subject
	 * @param content
	 */
	public static void mailWithAttachment(String email,String subject, String content ){
		
	}
	
	/**
	 * 发送电子邮件HTML格式(单封)
	 * @param uemail
	 * @param subject
	 * @param htmlMsg
	 * @param textMsg
	 * @throws EmailException
	 */
	public static void mailHtml(String uemail, String subject, String htmlMsg, String textMsg) throws EmailException{
		HtmlEmail email = new HtmlEmail();
		email.setHostName(AppConfig.Email_Host);
		email.setSmtpPort(AppConfig.Email_Port);
		email.setAuthenticator(new DefaultAuthenticator(AppConfig.Email_Username, AppConfig.Email_Password));
		email.setSSLOnConnect(true);
		email.addTo(uemail);
		email.setFrom(AppConfig.Email_Email, AppConfig.Eamil_ShowName);
		email.setCharset("utf-8");
		email.setSubject(subject);
		email.setHtmlMsg(htmlMsg);
		email.setTextMsg(textMsg);
		email.send();
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
		String herf = Messages.get("user.active.herf", "");
		String ahtml = "<a href='"+herf + code+"'>"+Messages.get("user.active.btn", "")+"</a>";
		String text = Messages.get("user.active.text", herf + code);
		
		String emailSubject = Messages.get("user.active.subject", "");
		String htmlContent = Messages.get("user.active.msg", User.getUserByEmail(email).username, AppConfig.WebSiteName, ahtml);
		String textContent = Messages.get("user.active.msg", User.getUserByEmail(email).username, AppConfig.WebSiteName, text);
		try {
			mailHtml(email, emailSubject, htmlContent, textContent);
			Logger.info("email sent ...");
		} catch (EmailException e) {
			Logger.info(Messages.get("error.mail.send", ""));
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送电子邮件-验证邮箱
	 * @param email
	 */
	public static void mail_verify_email(String email){
		
		String code = saveVerifyCode(email,"E");
		String herf = Messages.get("email.verify.herf", "");
		String ahtml = "<a href='"+herf + code+"'>"+Messages.get("email.verify.btn", "")+"</a>";
		String text = Messages.get("email.verify.text", herf + code);
		
		String emailSubject = Messages.get("email.verify.subject", "");
		String htmlContent = Messages.get("email.verify.msg", User.getUserByEmail(email).username, AppConfig.WebSiteName, ahtml);
		String textContent = Messages.get("email.verify.msg", User.getUserByEmail(email).username, AppConfig.WebSiteName, text);
		try {
			mailHtml(email, emailSubject, htmlContent, textContent);
			Logger.info("email sent ...");
		} catch (EmailException e) {
			Logger.info(Messages.get("error.mail.send", ""));
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送短信息-验证移动电话
	 * @param mobile
	 */
	public static void sms_verify_mobile(String mobile){
//		String code = saveVerifyCode(mobile,"M");
	}
}
