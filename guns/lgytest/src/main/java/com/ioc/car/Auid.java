package com.ioc.car;

public class Auid implements Car{
    @Override
    public void start() {
        System.out.println("audi start");
    }

    @Override
    public void turnLeft() {
        System.out.println("audi turnLeft");
    }

    @Override
    public void turnRight() {
        System.out.println("audi turnRight");
    }

    @Override
    public void stop() {
        System.out.println("audi stop");
    }
}
