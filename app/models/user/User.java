package models.user;

import javax.persistence.Entity;
import javax.persistence.Table;

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
	 * 移动电话
	 */
	public String mobile;
	
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
	
	/**
	 * 用户激活码
	 */
	public String activecode;
	
	public static Model.Finder<Long, User> find = new Model.Finder<Long, User>(Long.class, User.class);
	
	/**
	 * 用户注册和添加用户
	 * @param user
	 */
	public static boolean register(User user){
		//TODO 用户名唯一性验证
		if(find.where().eq("username", user.username).findUnique() == null){
			user.password = StringUtils.md5(user.password);
			user.activecode = StringUtils.get_activecode();
			user.save();
			SendUtils.mail_activeu_ser(user.email, user.activecode);
		}else{
			return false;
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
		return false;
	}
	
	/**
	 * 修改用户信息
	 * @param user
	 */
	public static void modifyUserInfo(User user){
		User u = find.where().eq("username", user.username).findUnique();
		//TODO 邮箱修改需要验证
		u.email = user.email;
		//TODO 移动电话修改需要验证
		u.mobile = user.mobile;
		u.real_name = user.real_name;
		u.id_num = user.id_num;
		u.update();
	}
	
	/**
	 * 修改用户密码
	 * @param username
	 * @param password
	 */
	public static void modifyUserPassword(String username, String password){
		User u = find.where().eq("username", username).findUnique();
		
	}
	
	

}
