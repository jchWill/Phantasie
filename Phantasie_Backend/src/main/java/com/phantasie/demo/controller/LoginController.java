package com.phantasie.demo.controller;

import com.phantasie.demo.entity.User;
import com.phantasie.demo.service.AnsyService;
import com.phantasie.demo.service.MailService;
import com.phantasie.demo.service.UserService;
import com.phantasie.demo.utils.JwtUtil;
import com.phantasie.demo.utils.MD5Util;
import com.phantasie.demo.utils.SessionUtil;
import com.phantasie.demo.utils.TokenUtil;
import com.phantasie.demo.utils.msg.jobInfo;
import com.phantasie.demo.utils.msgutils.Msg;
import com.phantasie.demo.utils.msgutils.MsgCode;
import com.phantasie.demo.utils.msgutils.MsgUtil;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    AnsyService ansyService;

    private static Map<String,String> codeResult = new ConcurrentHashMap<>();


    @RequestMapping("/login")
    public Msg login(@RequestBody Map<String,String> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println("用户登录请求");
        String username =map.get("username");
        String password = (String) map.get("password");

        Msg ret = userService.login(username, password);
        if(ret.getStatus() == -100) {
            return ret;
        }
        User user = userService.findUserByUsername(username);
        Integer id = user.getUserId();

        String jwtToken = JwtUtil.createJWT(username);

        String token = TokenUtil.generate();
        userService.setToken(token,user);
        ret.getData().put("token",token);

        String jobInfoStr = user.getJobInfo();
        List<jobInfo> jobInfoList = com.alibaba.fastjson.JSONArray.parseArray(jobInfoStr,jobInfo.class);
        ret.setList(jobInfoList);

        Websocket.insertToken(token,id);
        return ret;
    }

    @PostMapping(value = "/signup")
    public Msg signup(@RequestBody Map map) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String phone = (String) map.get("phone");
        return userService.signup(username, password,phone);
    }

    //@RequestMapping(value = "/test")
    public String TestFunc() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return TokenUtil.generate();
    }

    @RequestMapping("/checkSession")
    public Msg checkSession(){
        JSONObject auth = SessionUtil.getAuth();
        if(auth == null){
            return MsgUtil.makeMsg(MsgCode.NOT_LOGGED_IN_ERROR);
        }
        else{
            return MsgUtil.makeMsg(MsgCode.LOGIN_SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG, auth);
        }
    }


    @RequestMapping("/sendEMail")
    public String sendEmail(@RequestBody Map<String,String> params) {
        String email = params.get("add");
        return ansyService.send(email);
    }

    public static void saveCode(String code){
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        String currentTime = sf.format(c.getTime());// 生成5分钟后时间，用户校验是否过期

        String hash =  MD5Util.code(code);//生成MD5值
        codeResult.put("hash", hash);
        codeResult.put("tamp", currentTime);
    }


    public Msg reg(@RequestBody Map<String,String> params){
        if(codeResult.size() == 0){
            return MsgUtil.makeMsg(MsgCode.NOT_LOGGED_IN_ERROR);
        }

        String identify = params.get("identify");
        String requestHash = codeResult.get("hash").toString();

        String tamp = codeResult.get("tamp").toString();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");//当前时间
        Calendar c = Calendar.getInstance();
        String currentTime = sf.format(c.getTime());
        if (tamp.compareTo(currentTime) > 0) {
            String hash =  MD5Util.code(identify);//生成MD5值
            if (hash.equalsIgnoreCase(requestHash)){
                //校验成功
                //插入用户名密码和邮箱手机号等
                String username =  params.get("username");
                String password =  params.get("password");
                String phone = params.get("phone");
                return userService.signup(username, password,phone);
            }else {
                //验证码不正确，校验失败
                System.out.println("2");
                return MsgUtil.makeMsg(MsgCode.SIGNUP_USER_ERROR,"验证码校验失败");

            }
        } else {
            // 超时
            System.out.println("3");
            return MsgUtil.makeMsg(MsgCode.SIGNUP_USER_ERROR,"验证码已超时，请重新发送");

        }


        //return MsgUtil.makeMsg(MsgCode.SIGNUP_SUCCESS);

    }

}

