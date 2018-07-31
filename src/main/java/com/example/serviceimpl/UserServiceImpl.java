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

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 校验登录用户信息并登录
     *
     * @param loginMessageDTO
     * @param Request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public String checkUser(LoginMessageDTO loginMessageDTO, HttpServletRequest Request) {
        String name = loginMessageDTO.getUserName();
        System.out.println(userMapper.selectNumByLogin(loginMessageDTO));
        if (userMapper.selectNumByLogin(loginMessageDTO) == 0) {
            return LoginEnum.USER_NULL.getMessage();
        }
        if (!userMapper.selectPasswordByName(name).equals(loginMessageDTO.getPassWord())) {
            return LoginEnum.PASSWORD_ERROR.getMessage();
        }
        User user = userMapper.selectUserByName(name);
        Request.getSession().setAttribute("userbean", user);
        return user.getUserLevel();
    }

    @Override
    /**
     * 得到当前部门所有人员的姓名
     */
    public List<String> getDepamentAllname(int depament) {
        return userMapper.selectAllNameByDepament(depament,null);
    }

    @Override
    /**
     * 得到所有人员的姓名
     */
    public List<String> getAllname() {
        return userMapper.selectAllName();
    }

}
