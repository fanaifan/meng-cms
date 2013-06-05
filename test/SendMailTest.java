import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import models.User;

import org.junit.Test;


public class SendMailTest {
	
	@Test
	public void createUser(){
		 running(testServer(3333, fakeApplication(inMemoryDatabase())),new Runnable() {
			public void run() {
				User user = new User();
				user.username = "zhangpeng";
				user.email = "zp8360@sina.com";
				user.password = "zhangpeng";
				user.real_name = "张鹏";
				user.mobile = "18801011130";
				user.id_num = "110111198805261234";
				User.register(user);
			}

		});
//		 assertThat("18801011130").equals(User.getUserByName("zhangpeng").email);
	}

}
