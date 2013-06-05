package utils;

import play.Play;


public class AppConfig {

	/**
	 * Email SMTP 主机
	 */
	public static String Email_Host = getConfig("email.host");
	
	/**
	 * Email SMTP 端口
	 */
	public static int Email_Port = Integer.parseInt(getConfig("email.port"));
	
	/**
	 * Email 用户名
	 */
	public static String Email_Username = getConfig("email.username");
	
	/**
	 * Email 密码
	 */
	public static String Email_Password = getConfig("email.password");
	
	/**
	 * Email 电子邮件
	 */
	public static String Email_Email = getConfig("email.email");
	
	/**
	 * Email 邮件中显示名称
	 */
	public static String Eamil_ShowName = getConfig("email.showname");
	
	/**
	 * Website 网站名称
	 */
	public static String WebSiteName = getConfig("website");
	

			
	private static String getConfig(String config){
		return Play.application().configuration().getString(config);
	}
}
