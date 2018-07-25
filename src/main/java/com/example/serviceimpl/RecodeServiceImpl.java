package com.example.serviceimpl;

import com.example.common.util.DateUtil;
import com.example.controller.RecodeInfoController;
import com.example.dto.DateByWeekDTO;
import com.example.dto.RecodeConditionDTO;
import com.example.dto.RecodeCondtion2DTO;
import com.example.dto.UserDayRecodeDTO;
import com.example.entity.Recode;
import com.example.entity.User;
import com.example.enums.WeekEnum;
import com.example.mapper.RecodeMapper;
import com.example.mapper.UserMapper;
import com.example.service.IRecodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class RecodeServiceImpl implements IRecodeService {

    @Autowired
    private RecodeMapper recodeMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 日清插入数据更新
     *
     * @param userDayRecodeDTO
     * @return 1:代表更新正确；0：错误
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateUserRecode(UserDayRecodeDTO userDayRecodeDTO, User user) {
        int id = user.getId();
        List<Recode> list = userDayRecodeDTO.getWeeks();
        for (Recode recode : list) {
            if(!DateUtil.isValidDate(recode.getTheDay())){
                return 0;
            }
        }
        for (Recode recode : list) {
            recode.setUid(id);
            recodeMapper.updateRecodeByIdDay(recode);
        }
        return 1;
    }

    /**
     * 查询本周的日清数据，如果数据库不存在数据，则生成数据库语句插入
     *
     * @param userid
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Recode> getUserRecode(int userid) {
        Date currentDate = new Date();
        String date = DateUtil.getFirstDayBy(currentDate);
        if (recodeMapper.selectUserByDate(userid, date) == 0) {
            //如果不存在当前所在周的表
            Recode recode = new Recode();
            recode.setUid(userid);
            for (int i = 0; i < 7; i++) {
                recode.setTheWek(WeekEnum.getByValue(i + 2).getMessage());
                recode.setTheDay(new Integer(Integer.parseInt(date) + i).toString());
                recode.setRecodeCreate(DateUtil.getCurrentDate2(currentDate));
                recodeMapper.insert(recode);
            }
        }
        return recodeMapper.selectUserRecodeByUid7days(userid, date);
    }

    /**
     * 条件查询一些数据(需要写入异常处理：待理)
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public List<DateByWeekDTO> getRecodeCondition(RecodeConditionDTO recodeConditionDTO) {
        String dateWay = recodeConditionDTO.getDateWay();
        String userName = recodeConditionDTO.getUserName();
        String message = recodeConditionDTO.getMessage();
        String year = recodeConditionDTO.getYear();
        List<DateByWeekDTO> recodeList = null;
        if (userName.equals("ALL")) {
            userName = "";
        }
        if (dateWay.equals("按月份查看")) {
            String date = year + message;
            return recodeMapper.selectUserRecodeByMonth(userName, date);
        } else if (dateWay.equals("按季度查看")) {
            int quarter = 0;
            switch (Integer.parseInt(message)){
                case 103: quarter = 1; break;
                case 406: quarter = 2; break;
                case 709: quarter = 3; break;
                case 1012: quarter = 4; break;
                default: return null;
            }
            return recodeMapper.selectUserRecodeByQuarter(userName,quarter,year);
        } else if (dateWay.equals("按年份查看")) {
            return recodeMapper.selectUserRecodeByYear(userName,year);
        }
        return null;
    }

    /**
     * 得到所有部门人员的指定日期所在周的表
     *
     * @param recodeCondtion2DTO
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public List<UserDayRecodeDTO> getRecodeCondition2(RecodeCondtion2DTO recodeCondtion2DTO) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //设置时间格式
        Calendar cal = Calendar.getInstance();
        Date time = sdf.parse(recodeCondtion2DTO.getDate());
        cal.setTime(time);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (dayWeek == 1) {
            dayWeek = 8;
        }
        //得到指定日期的星期一日期
        String date = new Integer(Integer.parseInt(recodeCondtion2DTO.getDate()) + 2 - dayWeek).toString();
        UserDayRecodeDTO userDayRecodeDTO = null;
        List<UserDayRecodeDTO> dayRecodelist = new ArrayList<>();
        //根据姓名依次查询需要的数据
        if (recodeCondtion2DTO.getUserName().equals("ALL")) {
            //得到所有人员的姓名
            List<String> nameList = userMapper.selectAllName();
            for (String name : nameList) {
                userDayRecodeDTO = new UserDayRecodeDTO();
                userDayRecodeDTO.setUserName(name);
                userDayRecodeDTO.setWeeks(recodeMapper.selectUserRecodeByName7days(name, date));
                dayRecodelist.add(userDayRecodeDTO);
            }
            return dayRecodelist;
        }
        userDayRecodeDTO = new UserDayRecodeDTO();
        userDayRecodeDTO.setUserName(recodeCondtion2DTO.getUserName());
        userDayRecodeDTO.setWeeks(recodeMapper.selectUserRecodeByName7days(recodeCondtion2DTO.getUserName(), date));
        dayRecodelist.add(userDayRecodeDTO);
        return dayRecodelist;
    }
}
