package com.stylefeng.guns.rest.modular.order.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.cinema.CinemaServiceAPI;
import com.stylefeng.guns.order.OrderServiceAPI;
import com.stylefeng.guns.order.vo.OrderVO;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Service(interfaceClass = OrderServiceAPI.class,group = "default")
public class DefaultOrderServiceImpl implements OrderServiceAPI {

    @Autowired
    private MoocOrderTMapper moocOrderTMapper;

    @Reference(interfaceClass = CinemaServiceAPI.class,check = false)
    private CinemaServiceAPI cinemaServiceAPI;



    @Override
    public boolean isTrueSeats(String fieldId, String seats) {
        return false;
    }

    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {
        return false;
    }

    @Override
    public OrderVO saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId) {
        return null;
    }

    @Override
    public Page<OrderVO> getOrderByUserId(Integer userId, Page<OrderVO> page) {
        return null;
    }

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        return null;
    }
}
