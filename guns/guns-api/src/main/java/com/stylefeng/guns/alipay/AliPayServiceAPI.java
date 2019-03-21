package com.stylefeng.guns.alipay;


import com.stylefeng.guns.alipay.vo.AliPayInfoVO;
import com.stylefeng.guns.alipay.vo.AliPayResultVO;

public interface AliPayServiceAPI {

    AliPayInfoVO getQRCode(String orderId);

    AliPayResultVO getOrderStatus(String orderId);

}
