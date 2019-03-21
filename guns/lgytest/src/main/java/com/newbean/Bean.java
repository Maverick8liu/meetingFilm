package com.newbean;

import org.springframework.beans.AbstractNestablePropertyAccessor;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Bean {

    private AnotherBean anotherBean;
    private String string;


    private AnotherBean anotherBean1;
    private String string1;

    private List<String> stringList;
    private List<AnotherBean> anotherBeans;

    private Map<String,String> stringStringMap;
    private Map<AnotherBean,AnotherBean> anotherBeanMap;

    private Set<String> stringSet;
    private Set<AnotherBean> anotherBeanSet;

    private Properties properties;

    public Bean(AnotherBean anotherBean, String string) {
        this.anotherBean = anotherBean;
        this.string = string;
    }

    public AnotherBean getAnotherBean() {
        return anotherBean;
    }

    public void setAnotherBean(AnotherBean anotherBean) {
        this.anotherBean = anotherBean;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "Bean{" + "anotherBean=" + anotherBean + ", string='" + string + '\'' + ", anotherBean1=" + anotherBean1 + ", string1='" + string1 + '\'' + ", stringList=" + stringList + ", anotherBeans=" + anotherBeans + ", stringStringMap=" + stringStringMap + ", anotherBeanMap=" + anotherBeanMap + ", stringSet=" + stringSet + ", anotherBeanSet=" + anotherBeanSet + ", properties=" + properties + '}';
    }

    public void setAnotherBean1(AnotherBean anotherBean1) {
        this.anotherBean1 = anotherBean1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public List<AnotherBean> getAnotherBeans() {
        return anotherBeans;
    }

    public void setAnotherBeans(List<AnotherBean> anotherBeans) {
        this.anotherBeans = anotherBeans;
    }

    public Map<String, String> getStringStringMap() {
        return stringStringMap;
    }

    public void setStringStringMap(Map<String, String> stringStringMap) {
        this.stringStringMap = stringStringMap;
    }

    public Map<AnotherBean, AnotherBean> getAnotherBeanMap() {
        return anotherBeanMap;
    }

    public void setAnotherBeanMap(Map<AnotherBean, AnotherBean> anotherBeanMap) {
        this.anotherBeanMap = anotherBeanMap;
    }

    public Set<String> getStringSet() {
        return stringSet;
    }

    public void setStringSet(Set<String> stringSet) {
        this.stringSet = stringSet;
    }

    public Set<AnotherBean> getAnotherBeanSet() {
        return anotherBeanSet;
    }

    public void setAnotherBeanSet(Set<AnotherBean> anotherBeanSet) {
        this.anotherBeanSet = anotherBeanSet;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
