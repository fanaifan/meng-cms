package models.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import models.verify.VerifyCode;
import play.Logger;
import play.db.ebean.Model;
import play.i18n.Messages;
import utils.SendUtils;
import utils.StringUtils;

import com.avaje.ebean.Ebean;


/**
 * 会员管理<br>
 * 1.注册功能
 * 2.添加功能
 * 3.修改密码功能
 * 4.密码的加密功能
 * 5.修改会员信息功能
 * 6.email的激活功能
 * 7.手机号码激活功能
 * 8.登陆功能
 * @author zhangpeng
 *
 */
@Entity
@Table(name="user")
public class User extends Model {
	
	/**
	 * User serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Id
	public long id;
	
	/**
	 * 用户名
	 */
	@Column
	public String username;
	
	/**
	 * 密码
	 */
	@Column
	public String password;
	
	/**
	 * 电子邮箱
	 */
	@Column
	public String email;
	
	/**
	 * 验证邮箱
	 */
	@Column
	public boolean verify_email = false;
	
	/**
	 * 移动电话
	 */
	@Column
	public String mobile;
	
	/**
	 * 验证移动电话
	 */
	@Column
	public boolean verify_mobile = false;
	
	/**
	 * 身份证号
	 */
	@Column
	public String id_num;
	
	/**
	 * 真实姓名
	 */
	@Column
	public String real_name;
	
	/**
	 * 激活状态和暂停用户(新注册用户默认为不可用)
	 */
	@Column
	public boolean status = false;
	
	public static Model.Finder<Long, User> find = new Model.Finder<Long, User>(Long.class, User.class);
	
	/**
	 * 用户注册和添加用户
	 * 检查用户名称,电子邮箱,移动电话是否重复
	 * @param user
	 */
	public static boolean register(User user){
		
		User u_name = getUserByName(user.username);
		User u_email = getUserByEmail(user.email);
		User u_mobile = getUserByMobile(user.mobile);
		if(u_name != null){
			Logger.info("用户名重复");
			return false;
		}else if(u_email != null){
			Logger.info("电子邮箱重复");
			return false;
		}else if(u_mobile != null){
			Logger.info("移动电话重复");
			return false;
		}else{
			user.password = StringUtils.md5(user.password);
			user.save();
			SendUtils.mail_active_user(user.email);
		}
		return true;	
	}
	
	/**
	 * 用户登陆
	 * 用户名,电子邮箱,移动电话均可登陆
	 * @param userinfo
	 * @param password
	 * @return
	 */
	public static boolean login(String userinfo, String password){
		password = StringUtils.md5(password);

		if (verify_pwd(getUserByName(userinfo),password)) {
			return true;
		} else if (verify_pwd(getUserByEmail(userinfo),password)) {
			return true;
		} else if (verify_pwd(getUserByMobile(userinfo),password)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 用户登录验证密码和是否可用(辅助方法)
	 * @param user
	 * @param password
	 * @return
	 */
	private static boolean verify_pwd(User user,String password){
		if ( user == null ) {
			Logger.info(Messages.get("login.user.nothing", ""));
			return false;
		}
		if(!user.password.equals(password)){
			Logger.info(Messages.get("login.password.error", ""));
			return false;
		}
		if(user.status == false){
			Logger.info(Messages.get("login.user.disable", ""));
			return false;
		}
		return true;
	}
	
	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	public static User getUserByName(String username){
		return find.where().eq("username", username).findUnique();
	}
	
	/**
	 * 根据电子邮箱查询用户信息
	 * @param username
	 * @return
	 */
	public static User getUserByEmail(String email){
		return find.where().eq("email", email).findUnique();
	}

	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	public static User getUserByMobile(String mobile){
		return find.where().eq("mobile", mobile).findUnique();
	}
	
	/**
	 * 获取用户列表(分页方式)
	 * @param page
	 * @param size
	 * @return
	 */
	public static List<User> getUserByPage(int page, int size){
		return find.findPagingList(size).getPage(page).getList();
	}
	/**
	 * 获取用户的总数
	 * @return
	 */
	public static int getUserCount(){
		return find.findRowCount();
	}
	
	/**
	 * 修改用户信息
	 * @param user
	 */
	public static void modifyUserInfo(User user){
		User u = find.where().eq("username", user.username).findUnique();
		if(!u.email.equals(user.email)){
			u.email = user.email;
			u.verify_email = false;
		}
		if(!u.mobile.equals(user.mobile)){
			u.mobile = user.mobile;
			u.verify_mobile = false;
		}
		u.real_name = user.real_name;
		u.id_num = user.id_num;
		u.update();
	}
	
	/**
	 * 申请验证邮箱
	 * @param email
	 */
	public static void verify_email(String email){
		SendUtils.mail_verify_email(email);
	}
	
	/**
	 * 检验验证邮箱
	 * @param username
	 * @param verify_code
	 * @return
	 */
	public static boolean verify_email(String username, String verify_code){
		if(verify_code.equals(VerifyCode.getVerify_Email_Code(username))){
			User u = getUserByName(username);
			u.verify_email = true;
			modifyUserInfo(u);
			return true;
		}
		return false;
	}
	
	/**
	 * 申请验证移动电话
	 * @param email
	 */
	public static void verify_mobile(String mobile){
		SendUtils.sms_verify_mobile(mobile);
	}
	
	/**
	 * 检验验证移动电话
	 * @param username
	 * @param verify_code
	 * @return
	 */
	public static boolean verify_mobile(String username, String verify_code){
		if(verify_code.equals(VerifyCode.getVerify_Mobile_Code(username))){
			User u = getUserByName(username);
			u.verify_mobile = true;
			modifyUserInfo(u);
			return true;
		}
		return false;
	}

	
	/**
	 * 修改用户密码
	 * @param username
	 * @param password
	 */
	public static void modifyUserPassword(String username, String password){
		User u = find.where().eq("username", username).findUnique();
		u.password = StringUtils.md5(password);
		u.save();
	}
	
	/**
	 * 删除用户信息(从数据库中删除)
	 * @param username
	 */
	public static void destroyUser(String username){
		Ebean.delete(find.where().eq("username", username).findList());
	}
	
	/**
	 * 标识用户不可用
	 * @param username
	 */
	public static void disableUser(String username){
		User user = getUserByName(username);
		user.status = false;
		user.save();
	}
	

}
