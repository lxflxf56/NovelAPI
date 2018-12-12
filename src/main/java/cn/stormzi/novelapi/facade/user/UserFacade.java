package cn.stormzi.novelapi.facade.user;

import cn.stormzi.novelapi.facade.bean.analysis.UserBean;

public interface UserFacade {

    UserBean login(String username, String password);
    boolean logout(String username);
    boolean register(UserBean userBean)throws Exception;
    boolean isUsernameExist(String username);
}
