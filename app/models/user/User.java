package models.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.Logger;
import play.db.ebean.Model;
import utils.SendUtils;
import utils.StringUtils;

@Entity
@Table(name="user")
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
public class User extends Model {
	
	/**
	 * User serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	public long uid;
	
	/**
	 * 用户名
	 */
	public String username;
	
	/**
	 * 密码
	 */
	public String password;
	
	/**
	 * 电子邮箱
	 */
	public String email;
	
	/**
	 * 验证邮箱
	 */
	public boolean verify_email = false;
	
	/**
	 * 移动电话
	 */
	public String mobile;
	
	/**
	 * 验证移动电话
	 */
	public boolean verify_mobile = false;
	
	/**
	 * 身份证号
	 */
	public String id_num;
	
	/**
	 * 真实姓名
	 */
	public String real_name;
	
	/**
	 * 激活状态和暂停用户(新注册用户默认为不可用)
	 */
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
		
		User u_name = getUserByName(userinfo);
		User u_email = getUserByEmail(userinfo);
		User u_mobile = getUserByMobile(userinfo);
		if(u_name != null || u_email != null || u_mobile != null){
			if(u_name.password.equals(password)){
				return true;
			}
			Logger.info("密码错误");
		}
		Logger.info("用户信息错误");	
		return false;
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
	 * @param email
	 * @param verify_code
	 * @return
	 */
	public static boolean verify_email(String email, String verify_code){
		//TODO 获取邮箱验证码匹配
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
	 * @param mobile
	 * @param verify_code
	 * @return
	 */
	public static boolean verify_mobile(String mobile, String verify_code){
		//TODO 获取邮箱验证码匹配
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
	
	

}
