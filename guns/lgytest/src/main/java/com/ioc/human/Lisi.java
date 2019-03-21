package com.ioc.human;

import com.ioc.car.Car;

public class Lisi extends HumenWithCar{
    public Lisi(Car car) {
        super(car);
    }

    @Override
    public void goHome() {
        car.start();
        car.stop();
    }
}
