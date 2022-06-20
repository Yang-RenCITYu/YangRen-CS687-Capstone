package com.lll.store.service;

import com.lll.store.entity.User;
import com.lll.store.mapper.UserMapper;
import com.lll.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@SpringBootTest：表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
//@RunWith:表示启动这个单元测试类(不写单元测试类是不能运行的)，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserServiceTests {
    /*idea有检测功能，接口是不能直接创建Bean的（动态代理技术解决）：
     *  Setting -> Editor ->Inspection -> Spring -> Spring core -> Code ->
     * Autowiring for bean class -> Serverity设置为警告
     */
    @Autowired
    private IUserService userService;

    /**
     * 单元测试方法：就可以单独独立运行，不用启动整个项目，可以做单元测试，提升了代码的测试效率
     * 1.必须被@Test注解修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     */
    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("qqq");
            user.setPassword("123456");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            //获取类的对象，在获取类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login() {
        User admin = userService.login("admin", "123456");
        System.out.println(admin);
    }

    @Test
    public void changePassword() {
        userService.changePassword(10, "管理员", "123456", "654321");
    }

    @Test
    public void getByUid() {
        System.err.println(userService.getByUid(11));
    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setPhone("18533604224");
        user.setEmail("qqq@qq.com");
        user.setGender(0);
        userService.changeInfo(21, "管理员", user);
    }

    @Test
    public void changeAvatar() {
        userService.changeAvatar(22, "/upload/test.png", "小明");
    }
}
