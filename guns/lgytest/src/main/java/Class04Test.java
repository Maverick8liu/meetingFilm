

import com.beanold.Bean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Class04Test {

    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
       Bean bean =  context.getBean("bean", Bean.class);
        System.out.println("bean = "+bean);
    }
}
