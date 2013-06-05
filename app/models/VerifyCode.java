package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;


/**
 * 验证码列表
 * 1.用户验证码
 * 2.邮箱验证码
 * 3.移动电话验证码
 * @author zhangpeng
 *
 */
@Entity
@Table(name="verifycode")
public class VerifyCode extends Model {
	
	/**
	 * 验证码 SerialVersion UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 验证码ID
	 */
	@Id
	public long id;
	
	/**
	 * 用户名
	 */
	@Column
	public String username;
	
	/**
	 * 用户验证码
	 */
	@Column
	public String active_code;
	
	/**
	 * 邮箱验证码
	 */
	@Column
	public String verify_email_code;
	
	/**
	 * 移动电话验证码
	 */
	@Column
	public String verify_mobile_code;
	
	public static Model.Finder<Long, VerifyCode> find = new Model.Finder<Long, VerifyCode>(Long.class, VerifyCode.class);
	
	/**
	 * 保存验证码
	 * @param vc
	 */
	public static void saveVerifyCode(VerifyCode vc){
		if(getVCByUsername(vc.username) != null){
			modifyVerifyCode(vc);
		}
		vc.save();
	}
	
	/**
	 * 修改验证码
	 * @param vc
	 */
	public static void modifyVerifyCode(VerifyCode vc){
		VerifyCode vcode = getVCByUsername(vc.username);
		if(!vcode.active_code.equals(vc.active_code)){
			vcode.active_code = vc.active_code;
		}
		if(!vcode.verify_email_code.equals(vc.verify_email_code)){
			vcode.verify_email_code = vc.verify_email_code;
		}
		if(!vcode.verify_mobile_code.equals(vc.verify_mobile_code)){
			vcode.verify_mobile_code = vc.verify_mobile_code;
		}
		vcode.update();
	}
	
	/**
	 * 根据用户名获取验证码
	 * @param username
	 * @return
	 */
	public static VerifyCode getVCByUsername(String username){
		return find.where().eq("username", username).findUnique();
	}
	
	/**
	 * 获取用户激活码
	 * @param username
	 * @return
	 */
	public static String getActiveCode(String username){
		return getVCByUsername(username).active_code;
	}
	
	/**
	 * 获取邮箱验证码
	 * @param username
	 * @return
	 */
	public static String getVerify_Email_Code(String username){
		return getVCByUsername(username).verify_email_code;
	}
	
	/**
	 * 获取移动电话验证码
	 * @param username
	 * @return
	 */
	public static String getVerify_Mobile_Code(String username){
		return getVCByUsername(username).verify_mobile_code;
	}

}
