package com.stylefeng.guns.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SourceVO implements Serializable{
    private String sourceId;
    private String sourceName;
    private boolean isActive;

    public SourceVO(String s, String showName) {
        this.sourceId = s;
        this.sourceName = showName;
    }
}
