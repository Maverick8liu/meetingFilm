package com.test;


import com.ioc.IocCantainer;
import com.ioc.car.Auid;
import com.ioc.car.Baojun;
import com.ioc.human.Humen;
import com.ioc.human.Lisi;
import com.ioc.human.Zhangsan;
import org.junit.Before;
import org.junit.Test;

public class Class03Test {
    private IocCantainer iocCantainer = new IocCantainer();

    @Before
    public void before(){
        iocCantainer.setBean(Auid.class,"audi");
        iocCantainer.setBean(Baojun.class,"baojun");
        iocCantainer.setBean(Zhangsan.class,"zhangsan","audi");
        iocCantainer.setBean(Lisi.class,"lisi","baojun");
    }

    @Test
    public void test(){
        Humen zhangsan = (Humen) iocCantainer.getBean("zhangsan");
        zhangsan.goHome();

        Humen lisi = (Humen) iocCantainer.getBean("lisi");
        lisi.goHome();

    }
}
