package com.ioc.car;

public class Baojun implements Car{
    @Override
    public void start() {
        System.out.println("Baojun start");
    }

    @Override
    public void turnLeft() {
        System.out.println("Baojun turnLeft");
    }

    @Override
    public void turnRight() {
        System.out.println("Baojun turnRight");
    }

    @Override
    public void stop() {
        System.out.println("Baojun stop");
    }
}
