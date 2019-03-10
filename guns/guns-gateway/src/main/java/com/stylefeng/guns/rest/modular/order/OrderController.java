package com.stylefeng.guns.rest.modular.order;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/order/")
public class OrderController {

    public ResponseVO error(Integer fileld,String soldSeats,String seatsName){
        return  ResponseVO.serviceFail("Result Maps collection already contains value for");
    }

    @HystrixCommand(fallbackMethod = "error",commandProperties = {
            @HystrixProperty(name="execution.isolation.strategy",value="THREAD"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")},
            threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "1"),
            @HystrixProperty(name = "maxQueueSize", value = "10"),
            @HystrixProperty(name = "keepAliveTimeMinutes", value = "1000"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "8"),
            @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1500")
    }
    )
    @RequestMapping(value="buyTickets",method = RequestMethod.POST)
    public ResponseVO buyTickets(Integer fieldId,String soldSeats,String seatsName){
        return null;
    }

    @RequestMapping(value="getOrderInfo",method = RequestMethod.POST)
    public ResponseVO getOrderInfo(){
        return null;
    }

}
