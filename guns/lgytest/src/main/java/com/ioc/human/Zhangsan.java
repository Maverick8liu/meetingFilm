package com.ioc.human;

import com.ioc.car.Car;

public class Zhangsan extends HumenWithCar{

    public Zhangsan(Car car) {
        super(car);
    }

    public void goHome(){
        car.start();
        car.turnLeft();
        car.stop();
    }
}
