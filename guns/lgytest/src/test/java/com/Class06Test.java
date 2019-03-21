package com;

import com.beanold.Bean1;
import com.beanold.Bean2;
import com.beanold.Bean3;
import com.newbean.Bean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Class06Test {

   @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        Bean bean = context.getBean("bean",Bean.class);
        System.out.println("bean "+bean);
    }


}
