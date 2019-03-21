package com.stylefeng.guns;

import java.util.Arrays;
import java.util.Random;

public class MainTest {

    public static void main(String[] args) {
        Random random = new Random();

        String a = "1,2,3,4,5,6";


        int[] ary = new int[200];
        for(int i = 0;i < ary.length-1;i++){
            ary[i] =  random.nextInt(99999);
        }

        System.out.println(Arrays.toString(ary));

        Arrays.sort(ary);


        System.out.println(Arrays.toString(ary));


    }
}
