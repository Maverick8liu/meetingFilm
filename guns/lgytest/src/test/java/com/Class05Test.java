package com;

import com.beanold.Bean1;
import com.beanold.Bean2;
import com.beanold.Bean3;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Class05Test {

   // @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        Bean2 bean2 = (Bean2) context.getBean("bean2");
        System.out.println("bean2 "+ bean2);

        Bean3 bean3 = (Bean3) context.getBean("bean3");
        System.out.println("bean3 "+ bean3);
    }

   // @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        Bean1 bean1 = (Bean1) context.getBean("bean1_1");
        System.out.println("bean2 "+ bean1);


    }

    @Test
    public void test2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        Bean1 bean1 =  context.getBean("bean1_1", Bean1.class);
        System.out.println("bean = "+bean1);

        Bean1 bean2 =  context.getBean("bean1_2", Bean1.class);
        System.out.println("bean = "+bean2);

        Bean1 bean3 =  context.getBean("bean1_3", Bean1.class);
        System.out.println("bean = "+bean3);
    }
}
