package com.example.serviceimpl;

import com.example.dto.userinfo.LoginMessageDTO;
import com.example.entity.User;
import com.example.enums.LoginEnum;
import com.example.mapper.DepartmentMapper;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 校验登录用户信息并登录
     *
     * @param loginMessageDTO
     * @param Request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public int checkUser(LoginMessageDTO loginMessageDTO, HttpServletRequest Request) {
        String name = loginMessageDTO.getUserName();
        if (userMapper.selectNumByLogin(loginMessageDTO) == 0) {
            return LoginEnum.USER_NULL.getCode();
        }
        if (!userMapper.selectPasswordByName(name).equals(loginMessageDTO.getPassWord())) {
            return LoginEnum.PASSWORD_ERROR.getCode();
        }
        User user = userMapper.selectUserByName(name);
        Request.getSession().setAttribute("userbean", user);
        return LoginEnum.SUCCESS.getCode();
    }

    @Override
    /**
     * 得到当前部门所有人员的姓名
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public List<String> getDepamentAllname(int depament) {
        return userMapper.selectAllNameByDepament(null, null);
    }

    @Override
    /**
     * 得到所有人员的姓名
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public List<String> getAllname() {
        return userMapper.selectAllName();
    }

    @Override
    /**
     * 增加用户
     * @param userbean 当前登陆用户
     * @param user 需要增加的用户信息
     * @return 0,无权限；1,成功; 2，用户名已存在
     */
    public int addUser(User userbean, User user) {
        int userLevel = userMapper.selectLevelById(userbean.getId());
        if ( userLevel != 3 && userLevel != 4) {
            return 0;
        }
        if(userMapper.selectIdByName(user.getUserName()) != null){
            return 2;
        }
        userMapper.insert(user);
        return 1;
    }

    @Override
    /**
     * 删除用户
     * @param userbean 当前登陆用户
     * @param user 需要删除的用户信息
     * @return 0,无权限；1,成功; 2，用户名不存在
     */
    public int deleteUser(User userbean, String userName) {
        int userLevel = userMapper.selectLevelById(userbean.getId());
        if ( userLevel != 3 && userLevel != 4) {
            return 0;
        }
        if(userMapper.selectIdByName(userName) == null){
            return 2;
        }
        userMapper.deleteUser(userName);
        return 1;
    }

}
