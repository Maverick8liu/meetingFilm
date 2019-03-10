package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.cinema.CinemaServiceAPI;
import com.stylefeng.guns.cinema.vo.*;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaFieldResponseVO;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaFieldsResponseVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cinema/")
public class CinemaController {

    @Reference(interfaceClass = CinemaServiceAPI.class,connections = 10,cache = "lru",check = false)
    private CinemaServiceAPI cinemaServiceAPI;

    private static final String IMG_PRE = "http://img.meetingshop.cn/";

    @RequestMapping(value = "getCinemas",method = RequestMethod.GET)
    public ResponseVO getCinemas(CinemaQueryVO cinemaQueryVO){
        try{
            // 按照五个条件进行筛选
            Page<CinemaVO> cinemas = cinemaServiceAPI.getCinemas(cinemaQueryVO);
            // 判断是否有满足条件的影院
            if(cinemas.getRecords() == null || cinemas.getRecords().size() == 0){
                return ResponseVO.success("No more infomation!");
            }else {
                return ResponseVO.success(cinemas.getCurrent(),(int)cinemas.getPages(),"",cinemas.getRecords());
            }
        }catch (Exception e){
            // 如果出现异常，应该如何处理
            log.error("获取影院列表异常",e);
            return ResponseVO.serviceFail("查询影院列表失败");
        }


    }


    @RequestMapping(value = "getCondition",method = RequestMethod.GET)
    public ResponseVO getCondition(CinemaQueryVO cinemaQueryVO){
        return null;
    }


    @RequestMapping(value = "getFields",method = RequestMethod.GET)
    public ResponseVO getFields(Integer cinemaId){

        try{
            CinemaInfoVO cinemaInfoById = cinemaServiceAPI.getCinemaInfoById(cinemaId);

            List<FilmInfoVO> filmInfoByCinemaIds = cinemaServiceAPI.getFilmInfoByCinemaId(cinemaId);

            CinemaFieldsResponseVO cinemaFieldResponseVO = new CinemaFieldsResponseVO();
            cinemaFieldResponseVO.setCinemaInfo(cinemaInfoById);
            cinemaFieldResponseVO.setFilmInfoList(filmInfoByCinemaIds);

            return ResponseVO.success(IMG_PRE,cinemaFieldResponseVO);

        }catch (Exception e){
            log.error("获取播放场次失败",e);
            return ResponseVO.serviceFail("获取播放场次失败");
        }

    }

    @RequestMapping(value = "getFieldInfo",method = RequestMethod.POST)
    public ResponseVO getFieldInfo(Integer cinemaId,Integer fieldId){
        try{
            CinemaInfoVO cinemaInfoById = cinemaServiceAPI.getCinemaInfoById(cinemaId);
            FilmInfoVO filmInfoByFieldId = cinemaServiceAPI.getFilmInfoByFieldId(fieldId);
            HallInfoVO filmFieldInfo = cinemaServiceAPI.getFilmFieldInfo(fieldId);

            //造几个销售的假数据，后续会对接订单接口

            filmFieldInfo.setSoldSeats("1,2,3");
            CinemaFieldResponseVO cinemaFieldResponseVO = new CinemaFieldResponseVO();
            cinemaFieldResponseVO.setCinemaInfo(cinemaInfoById);
            cinemaFieldResponseVO.setFilmInfo(filmInfoByFieldId);
            cinemaFieldResponseVO.setHallInfo(filmFieldInfo);

            return ResponseVO.success(IMG_PRE,cinemaFieldResponseVO);

        }catch (Exception e){
            log.error("获取选座信息失败",e);
            return ResponseVO.serviceFail("获取选座信息失败");
        }

    }
}
