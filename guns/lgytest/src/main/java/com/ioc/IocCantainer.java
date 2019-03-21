package com.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实例化bean
 * 保存bean
 * 提供bean
 */
public class IocCantainer {

    private Map<String,Object> beans = new ConcurrentHashMap<>();

    /**
     * 根据beanID获取bean
     * @param beanId
     * @return
     */
    public Object getBean(String beanId){
        return beans.get(beanId);
    }


    public void setBean(Class<?> clazz,String beanId,String... paramBeanIds){

        Object[] paramValues = new Object[paramBeanIds.length];

        for(int i = 0;i < paramBeanIds.length;i++){
            paramValues[i] = beans.get(paramBeanIds[i]);
        }

        Object bean = null;
        for(Constructor<?> constructor : clazz.getConstructors()){
            try {
                bean = constructor.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        if(bean == null){
            throw new RuntimeException("Not found method serizial bean!");
        }

        beans.put(beanId,bean);

    }
}
