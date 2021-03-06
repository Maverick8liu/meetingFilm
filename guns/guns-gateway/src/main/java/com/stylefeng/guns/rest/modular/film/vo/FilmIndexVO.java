package com.stylefeng.guns.rest.modular.film.vo;

import com.stylefeng.guns.film.vo.BannerVO;
import com.stylefeng.guns.film.vo.FilmInfo;
import com.stylefeng.guns.film.vo.FilmVO;
import lombok.Data;

import java.util.List;

@Data
public class FilmIndexVO {

    private List<BannerVO> banners;
    private FilmVO hotFilm;
    private FilmVO soonFilm;
    private List<FilmInfo> boxRanking;
    private List<FilmInfo> expectRanking;
    private List<FilmInfo> top100;


}
