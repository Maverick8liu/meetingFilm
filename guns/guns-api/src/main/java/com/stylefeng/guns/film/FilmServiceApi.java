package com.stylefeng.guns.film;

import com.stylefeng.guns.film.vo.BannerVO;
import com.stylefeng.guns.film.vo.FilmVO;

import java.util.List;

public interface FilmServiceApi {
    // 获取banners
    List<BannerVO> getBanners();

    // 获取热映影片
    FilmVO getHotFilms(boolean isLimit,int nums,int nowPage,int sortId,int sourceId,int yearId,int catId);

    FilmVO getSoonFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId);

    // 获取经典影片
    FilmVO getClassicFilms(int nums,int nowPage,int sortId,int sourceId,int yearId,int catId);
    /*
        在正式项目中，推荐大家使用的做法
     */
//    // 获取热映影片
//    FilmVO getHotFilms(int nowPage,int nums ...);

    // 获取票房排行榜
    List<FilmInfo> getBoxRanking();
    // 获取人气排行榜
    List<FilmInfo> getExpectRanking();
    // 获取Top100
    List<FilmInfo> getTop();
}
