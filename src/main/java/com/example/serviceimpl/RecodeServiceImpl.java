package com.example.serviceimpl;

import com.example.common.util.DateUtil;
import com.example.dto.recode.*;
import com.example.entity.Recode;
import com.example.entity.Review;
import com.example.entity.User;
import com.example.enums.WeekEnum;
import com.example.mapper.DepartmentMapper;
import com.example.mapper.RecodeMapper;
import com.example.mapper.ReviewMapper;
import com.example.mapper.UserMapper;
import com.example.service.IRecodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    /**
     * 日清插入数据更新
     *
     * @param userDayRecodeDTO
     * @return 1:代表更新正确；0：格式数据错误
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateUserRecode(UserDayRecodeDTO userDayRecodeDTO, User user) throws ParseException {
        int id = user.getId();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        List<DateByWeekDTO> dateByWeekDTOlist = userDayRecodeDTO.getWeeks();
        Recode recode = new Recode();
        for (int i = 0; i < dateByWeekDTOlist.size(); i++) {
            String dateTime = dateByWeekDTOlist.get(i).getDateTime();
            //是否符合yyyyMMdd格式
            if (!DateUtil.isValidDate(dateTime)) {
                return 0;
            }
            //是否传输日期越界（今天）
            BeanUtils.copyProperties(dateByWeekDTOlist.get(i), recode);
            recode.setUserId(id);
            recodeMapper.updateRecodeByIdDay(recode);
        }
        return 1;
    }

    /**
     * 查询当前用户指定日期所在周的日清数据，如果数据库不存在数据，则生成数据库语句插入
     *
     * @param userid
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<DateByWeekDTO> getUserRecode(int userid, String dateTime) throws ParseException {
        String date = DateUtil.getFirstDayBy(DateUtil.getStringDate(dateTime));
        if (recodeMapper.selectUserByDate(userid, date) == 0) {
            //如果不存在指定日期所在周的表
            Recode recode = new Recode();
            recode.setUserId(userid);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtil.getStringDate(date));
            for (int i = 0; i < 7; i++) {
                recode.setWeekDay(WeekEnum.getByValue(i + 2).getMessage());
                recode.setDateTime(DateUtil.getCurrentDate(calendar.getTime()));
                recode.setWeekId(date + StringUtils.leftPad(new Integer(userid).toString(), 5, "0"));
                recode.setState(0);
                recodeMapper.insert(recode);
                calendar.add(Calendar.DATE, 1);
            }
        }
        List<Recode> recodeList = recodeMapper.selectUserRecodeByUid7days(userid, date);
        List<DateByWeekDTO> dateByWeekDTOList = new ArrayList<>();
        for (int i = 0; i < recodeList.size(); i++) {
            dateByWeekDTOList.add(new DateByWeekDTO());
            BeanUtils.copyProperties(recodeList.get(i), dateByWeekDTOList.get(i));
        }
        return dateByWeekDTOList;
    }

    /**
     * 条件查询一些数据(需要写入异常处理：待理)
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public List<DateByWeek1DTO> getRecodeCondition(RecodeConditionDTO recodeConditionDTO) {
        String dateWay = recodeConditionDTO.getDateWay();
        String userName = recodeConditionDTO.getUserName();
        String message = recodeConditionDTO.getMessage();
        String year = recodeConditionDTO.getYear();
//        List<DateByWeekDTO> recodeList = null;
        if (userName.equals("ALL")) {
            userName = "";
        }
        if (dateWay.equals("按月份查看")) {
            String date = year + message;
            return recodeMapper.selectUserRecodeByMonth(userName, date);
        } else if (dateWay.equals("按季度查看")) {
            int quarter = 0;
            switch (Integer.parseInt(message)) {
                case 103:
                    quarter = 1;
                    break;
                case 406:
                    quarter = 2;
                    break;
                case 709:
                    quarter = 3;
                    break;
                case 1012:
                    quarter = 4;
                    break;
                default:
                    return null;
            }
            return recodeMapper.selectUserRecodeByQuarter(userName, quarter, year);
        } else if (dateWay.equals("按年份查看")) {
            return recodeMapper.selectUserRecodeByYear(userName, year);
        }
        return null;
    }

    /**
     * 得到所有部门人员的指定日期所在周的表和对应的审核
     *
     * @param recodeCondtion2DTO
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public List<UserWeekRecodeReviewDTO> getRecodeCondition2(RecodeCondtion2DTO recodeCondtion2DTO, HttpServletRequest request) throws Exception {
        String departmentName = null;
        User user = (User) request.getSession().getAttribute("userbean");
        if (recodeCondtion2DTO.getDepartmentName() == null) {
            departmentName = departmentMapper.selectByPrimaryKey(user.getDepartmentId()).getDepartmentName();
        } else {
            departmentName = recodeCondtion2DTO.getDepartmentName();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //设置时间格式
        //得到指定日期的星期一日期
        String date = DateUtil.getFirstDayBy(sdf.parse(recodeCondtion2DTO.getDate()));
        List<UserWeekRecodeReviewDTO> dayRecodelist = new ArrayList<>();
        List<Recode> recodelist = null;
        List<String> nameList = null;
        DateByWeekDTO dateByWeekDTO = null;
        if (recodeCondtion2DTO.getUserName().equals("ALL")) {
            //得到所有人员的姓名
            nameList = userMapper.selectAllNameByDepament(null, departmentName);
        } else {
            nameList = new ArrayList<>();
            nameList.add(recodeCondtion2DTO.getUserName());
        }
        String weekId = null;
        for (String name : nameList) {
            UserWeekRecodeReviewDTO userWeekRecodeReviewDTO = new UserWeekRecodeReviewDTO();
            userWeekRecodeReviewDTO.setUserName(name);
            recodelist = recodeMapper.selectUserRecodeByName7days(name, date, departmentName);
            List<DateByWeekDTO> dateByWeekDTOList = new ArrayList<>();
            if (recodelist.size() == 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateUtil.getStringDate(date));
                for (int i = 0; i < 7; i++) {
                    dateByWeekDTO = new DateByWeekDTO();
                    dateByWeekDTO.setWeekDay(WeekEnum.getByValue(i + 2).getMessage());
                    dateByWeekDTO.setDateTime(DateUtil.getCurrentDate(calendar.getTime()));
                    dateByWeekDTOList.add(dateByWeekDTO);
                    calendar.add(Calendar.DATE, 1);
                }
            } else {
                for (int i = 0; i < recodelist.size(); i++) {
                    dateByWeekDTOList.add(new DateByWeekDTO());
                    BeanUtils.copyProperties(recodelist.get(i), dateByWeekDTOList.get(i));
                }
            }
            userWeekRecodeReviewDTO.setWeeks(dateByWeekDTOList);
            weekId = date + StringUtils.leftPad(userMapper.selectIdByName(name), 5, "0");
            List<Review> reviewList = reviewMapper.getReviewByWeekId(weekId);
            List<WeekReviewDTO> weekReviewDTOList = new ArrayList<>();
            for (int i = 0; i < reviewList.size(); i++) {
                weekReviewDTOList.add(new WeekReviewDTO());
                BeanUtils.copyProperties(reviewList.get(i), weekReviewDTOList.get(i));
                weekReviewDTOList.get(i).setReviewUser(userMapper.selectNameById(reviewList.get(i).getUserId()));
            }
            userWeekRecodeReviewDTO.setReviews(weekReviewDTOList);
            dayRecodelist.add(userWeekRecodeReviewDTO);
        }
        return dayRecodelist;
    }
}
