package com.stylefeng.guns.film.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatVO implements Serializable{

    private String catId;
    private String catName;
    private boolean isActive;


    public CatVO(String s, String showName) {
        this.catId = s;
        this.catName = showName;
    }
}
