import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import models.User;
import models.VerifyCode;

import org.junit.Test;

import utils.StringUtils;

/**
 * 测试会员管理
 * @author zhangpeng
 *
 */
public class UserTest {
	
	@Test
	public void createUser(){
		 running(testServer(3333, fakeApplication(inMemoryDatabase())),new Runnable() {
			public void run() {
				
				/**
				 * 清除数据和初始化数据
				 */
				String username = "zhangpeng";
				if(User.getUserByName(username) != null){
					User.destroyUser(username);
				}
				if(VerifyCode.getVCByUsername(username) != null){
					VerifyCode.deleteVerifyCode(username);
				}
				
				/**
				 * 注册用户
				 */
				User user = new User();
				user.username = "zhangpeng";
				user.email = "zp8360@sina.com";
				user.password = "zhangpeng";
				user.real_name = "zhangpeng";
				user.mobile = "18801011130";
				user.id_num = "110111198805261234";
				User.register(user);
				
				/**
				 * 用户登录
				 */
				assertThat(true == User.login(username, "zhangpeng"));
				assertThat(true == User.login(user.email, "zhangpeng"));
				assertThat(true == User.login(user.mobile, "zhangpeng"));
				
				/**
				 * 获取用户信息
				 */
				assertThat(user.email.equals(User.getUserByName(username)));
				assertThat(user.username.equals(User.getUserByEmail(user.email)));
				assertThat(user.username.equals(User.getUserByMobile(user.mobile)));
				
				/**
				 * 验证邮箱
				 */
				User.verify_email("zp8360@sina.com");
				User.verify_email(username, VerifyCode.getVerify_Email_Code(username));
				assertThat(true == User.getUserByName(username).verify_email);
				
				/**
				 * 测试修改密码
				 */
				User.modifyUserPassword(username, "123456");
				assertThat(StringUtils.md5("123456").equals(User.getUserByName(username).password));
				
				/**
				 * 测试查询分页
				 */
				assertThat(User.getUserByPage(0, 1).size() == 1);
			}

		});
	}

}
