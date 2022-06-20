package com.lll.store.controller;

import com.lll.store.controller.ex.*;
import com.lll.store.entity.User;
import com.lll.store.service.ex.*;
import com.lll.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 控制层的基类
 */
public class BaseController {
    //操作成功的状态码
    public static final int OK = 200;

    //请求处理方法，这个方法的返回值就是需要传递给前端的数据
    //自动将异常对象传递给此方法的参数列表上
    //当前项目中产生了异常，被统一拦截到此方法中，这个方法此时就充当的是请求处理方法，方法的返回值会直接给到前端
    @ExceptionHandler({ServiceException.class, FileUploadException.class})//用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMessage("User name has been taken");
        } else if (e instanceof UserNotFoundException) {
            result.setState(5001);
            result.setMessage("User data does not exist exception");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(5002);
            result.setMessage("Exception for incorrect password for username");
        } else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage("Exception for user's shipping address exceeding the upper limit");
        } else if (e instanceof AddressNotFoundException) {
            result.setState(4004);
            result.setMessage("Exception for user's shipping address data does not exist");
        } else if (e instanceof AccessDeniedException) {
            result.setState(4005);
            result.setMessage("Exception for illegal access to shipping address data");
        } else if (e instanceof ProductNotFoundException) {
            result.setState(4006);
            result.setMessage("Exception for non-existent product");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("Unknown exception thrown during registration");
        } else if (e instanceof InsertException) {
            result.setState(5001);
            result.setMessage("Unknown exception thrown while updating data");
        } else if (e instanceof DeleteException) {
            result.setState(5002);
            result.setMessage("Unknown exception thrown when deleting data");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }
        return result;
    }

    /**
     * 获取session对象中的uid
     *
     * @param session session对象
     * @return 当前登录的用户的uid的值
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取当前登录用户的username
     *
     * @param session session对象
     * @return 当前登录用户的用户名
     * 在实现类中重写父类的toString()，不是句柄信息的输出
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
