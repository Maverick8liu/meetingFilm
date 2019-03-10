package com.stylefeng.guns.film.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerVO implements Serializable{

    private  String bannerId;
    private String bannerAddress;
    private String bannerUrl;


}
