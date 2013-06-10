import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import models.admin.AdminUser;

import org.junit.Test;

import utils.StringUtils;


/**
 * 测试管理员管理
 * @author zhangpeng
 *
 */
public class AdminUserTest {
	
	@Test
	public void createUser(){
		 running(testServer(3333, fakeApplication(inMemoryDatabase())),new Runnable() {
			public void run() {
				
				/**
				 * 清除数据和初始化数据
				 */
				String username = "admin";
				assertThat(AdminUser.getAdminUserByUsername(username) != null);
				if(AdminUser.getAdminUserByUsername(username) != null){
					AdminUser.destroyAdminUser(username);
				}
				
				/**
				 * 注册用户
				 */
				AdminUser user = new AdminUser();
				user.username = "admin";
				user.email = "zp8360@sina.com";
				user.password = "zhangpeng";
				user.real_name = "zhangpeng";
				user.mobile = "18801011130";
				AdminUser.createUser(user);
				
				/**
				 * 用户登录
				 */
				assertThat(true == AdminUser.login(username, "admin"));
				
				/**
				 * 获取用户信息
				 */
				assertThat(user.email.equals(AdminUser.getAdminUserByUsername(username).email));
				
				
				/**
				 * 测试修改密码
				 */
				AdminUser.modifyAdminUserPassword(username, "123456");
				assertThat(StringUtils.md5("123456").equals(AdminUser.getAdminUserByUsername(username).password));
				
				/**
				 * 测试查询分页
				 */
				assertThat(AdminUser.getAdminUserByPage(0, 1).size() == 1);
			}

		});
	}

}
