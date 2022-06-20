package com.lll.store.controller;

import com.lll.store.controller.ex.*;
import com.lll.store.entity.User;
import com.lll.store.service.IUserService;
import com.lll.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    /**
     * * Covenant is greater than the configuration: the development of fine thought to complete, omitting a large number of configuration and even annotation writing
     *      * 1. receive data way: request processing method parameter list set to pojo type to receive data from the front end
     *      * SpringBoot will compare the parameter name in the url address of the front-end data with the property name of the pojo class, and if
     *      * the two name items, then the value is injected into the corresponding property in the pojo class
     */
    @PostMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<Void>(OK);
    }

    /**
     @RequestMapping("reg") public JsonResult<Void> reg(User user){
     // Create the response result object
     JsonResult<Void> result = new JsonResult<>();//throw exception
     try {
     userService.reg(user);
     result.setState(200);
     result.setMessage("User registration successful");
     } catch (UsernameDuplicatedException e) {
     result.setState(4000);
     result.setMessage("User name is occupied");
     }catch (InsertException e) {
     result.setState(5000);
     result.setMessage("Unknown exception thrown during registration");
     }
     return result;
     }
     */

    /**
     * 2. Receive data method: request processing method parameter list set to non-pojo type
     * SpringBoot will directly compare the parameter name of the request with the parameter name of the method directly, and if the name is the same
     * then the dependency injection will be done automatically
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User data = userService.login(username, password);
        //向session对象中完成数据的绑定（session全局的）
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        //获取session中绑定的数据
//        System.out.println(getuidFromSession(session));
//        System.out.println(getUsernameFromSession(session));
        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
//        System.out.println("lllllll");
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
//        System.out.println(uid);
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<Void>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User data = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        // user对象有四部分的数据：username、phone、Email、gender、
        // uid数据需要再次封装到user对象中
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult<>(OK);
    }

    /**
     * 设置上传文件的最大值
     **/
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    /**
     * 限制上传文件的类型
     **/
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("images/jpeg");
        AVATAR_TYPE.add("images/png");
        AVATAR_TYPE.add("images/bmp");
        AVATAR_TYPE.add("images/gif");
    }

    /**
     * MultipartFile接口是SpringMVC提供的一个接口，这个接口为我们包装了
     * 获取文件类型的数据（任何类型的file都可以接收），SpringBoot它有整合了
     * SpringMVC，只需要在处理请求的方法参数列表上声明了应该参数类型为MultipartFile
     * 的参数，然后SpringBoot自动将传递给服务的文件数据赋值给这个参数
     *
     * @param session
     * @param file
     * @return
     * @RequestParam 表示请求中的参数，将请求中的参数注入请求处理方法的某个参数上，
     * 如果名称不一致则可以使用@RequestParam注解进行标记和映射
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, @RequestParam("file") MultipartFile file) {
        // 判断文件是否为null
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超出限制");
        }
        // 判断文件的类型是否包含我们规定的后缀类型
        String contentType = file.getContentType();
        // 如果集合包含某个元素则返回值true
        if (AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }
        // 上传的文件.../upload/文件.png
        String parent = session.getServletContext().getRealPath("upload");
        // File对象指向这个路径，File是否存在
        File dir = new File(parent);
        if (!dir.exists()) {// 检测目录是否存在
            dir.mkdirs();//创建当前的目录
        }
        // 获取到这个文件名称，UUID工具来将生成一个新的字符串作为文件名
        // 例如：avatar01.png
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename=" + originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);

        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        File dest = new File(dir, filename);// 是一个空文件
        // 参数file中数据写入到这个空文件中
        try {
            file.transferTo(dest);// 将file文件中的数据写入到dest文件
        } catch (FileStateException e) {
            throw new FileStateException("File type not support");
        } catch (IOException e) {
            throw new FileUploadIOException("File can not be read");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 返回头像的路径/upload/test.png
        String avatar = "/upload/" + filename;
        userService.changeAvatar(uid, avatar, username);
        // 返回用户头像的路径给前端页面，将来用于头像展示使用
        return new JsonResult<>(OK,avatar);
    }
}
