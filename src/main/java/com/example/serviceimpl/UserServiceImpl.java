package com.example.serviceimpl;

import com.example.dto.LoginMessageDTO;
import com.example.enums.LoginEnum;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public User getUser(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 校验登录用户信息并登录
     * @param loginMessageDTO
     * @param Request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public String checkUser(LoginMessageDTO loginMessageDTO,HttpServletRequest Request) {
        String name = loginMessageDTO.getUserName();
        if (userMapper.selectNumByName(name) == 0) {
            return LoginEnum.USER_NULL.getMessage();
        }
        if(!userMapper.selectPasswordByName(name).equals(loginMessageDTO.getPassWord())) {
            return LoginEnum.PASSWORD_ERROR.getMessage();
        }
        User user = userMapper.selectUserByName(name);
        Request.getSession().setAttribute("userbean",user);
        return LoginEnum.SUCCESS.getMessage();
    }
}
