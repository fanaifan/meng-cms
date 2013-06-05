import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import static org.fest.assertions.Assertions.*;
import models.User;
import models.VerifyCode;

import org.junit.Test;


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
			}

		});
	}

}
