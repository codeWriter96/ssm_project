package com.bytedance.crm.settings.aspect;
import com.bytedance.crm.exception.LoginException;
import com.bytedance.crm.settings.domain.User;
import com.bytedance.crm.settings.vo.LoginUser;
import com.bytedance.crm.untils.DateTimeUtil;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@Aspect
public class ServiceAspect {
    @AfterReturning(value = "execution(* *..Impl.UserServiceImpl.loginUser (..))",returning = "map")
    public void LoginUserAspect(Map<String,Object> map){
        User user = (User) map.get("user");
        if(user==null){
            throw new LoginException("用户名或者密码错误");
        }
        String expireTime =user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime)<0){
            throw new LoginException("用户账号已失效");
        }
        String lockState =user.getLockState();
        if("0".equals(lockState)){
            throw new LoginException("用户账号已锁定，联系管理员解锁");
        }
        LoginUser loginUser = (LoginUser) map.get("loginUser");
        String allowIps = user.getAllowIps();
        if(!allowIps.contains(loginUser.getIp())){
            System.out.println(loginUser.getIp());
            throw new LoginException("用户ip地址受限，联系管理员解锁");
        }
    }
}
