package com.example.serviceimpl;

import com.example.common.util.DateUtil;
import com.example.dto.UserDayRecodeDTO;
import com.example.entity.Recode;
import com.example.entity.User;
import com.example.mapper.RecodeMapper;
import com.example.service.IRecodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
public class RecodeServiceImpl implements IRecodeService {

    @Autowired
    private RecodeMapper recodeMapper;

    /**
     * 日清插入数据更新
     *
     * @param userDayRecodeDTO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateUserRecode(UserDayRecodeDTO userDayRecodeDTO, User user) {
        int id = user.getId();
        List<Recode> list = userDayRecodeDTO.getWeeks();
        for (Recode recode : list) {
            if (recodeMapper.selectNumByDay(recode) == 0) {
                recode.setId(id);
                recodeMapper.insert(recode);
            } else {
                recodeMapper.updateRecodeByIdDay(recode);
            }
        }
        return 1;
    }

    @Override
    public List<Recode> getUserRecode(int userid) {
        //1代表礼拜天，2代表礼拜一;
//        Integer date = Integer.parseInt(DateUtil.getCurrentDate()) + 2 - Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
//        date.toString();
        int dayWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(dayWeek==1){
            dayWeek = 8;
        }
        Integer date = Integer.parseInt(DateUtil.getCurrentDate()) + 2-dayWeek;
        return  recodeMapper.getUserRecodeByUid7days(userid,date.toString());
    }
}
