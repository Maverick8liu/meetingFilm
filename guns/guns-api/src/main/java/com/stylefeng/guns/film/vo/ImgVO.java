package com.stylefeng.guns.film.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgVO implements Serializable{
    private String mainImg;
    private String img01;
    private String img02;
    private String img03;
    private String img04;
}
