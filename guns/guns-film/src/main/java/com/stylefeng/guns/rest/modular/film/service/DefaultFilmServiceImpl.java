package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.film.FilmServiceApi;
import com.stylefeng.guns.film.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = FilmServiceApi.class)
public class DefaultFilmServiceImpl implements FilmServiceApi{

    @Autowired
    private MoocBannerTMapper bannerTMapper;

    @Autowired
    private MoocFilmInfoTMapper filmInfoTMapper;

    @Autowired
    private MoocFilmTMapper filmTMapper;

    @Autowired
    private MoocCatDictTMapper catDictTMapper;

    @Autowired
    private MoocYearDictTMapper yearDictTMapper;

    @Autowired
    private MoocSourceDictTMapper sourceDictTMapper;

    @Autowired
    private MoocActorTMapper actorTMapper;


    @Override
    public List<BannerVO> getBanners() {
        List<MoocBannerT> bannerTList = bannerTMapper.selectList(null);
        List<BannerVO> bannerVOList = new ArrayList<>();
        bannerTList.forEach(moocBannerT -> bannerVOList.add(
                new BannerVO(moocBannerT.getUuid()+"",
                        moocBannerT.getBannerAddress(),
                        moocBannerT.getBannerUrl())
        ));

        return bannerVOList;
    }

    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo>  filmInfos= new ArrayList<>();

        // 热映影片的限制条件
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",1);
        // 判断是否是首页需要的内容
        if(isLimit){
            // 如果是，则限制条数、限制内容为热映影片
            Page<MoocFilmT> page = new Page<>(1,nums);
            List<MoocFilmT> moocFilms = filmTMapper.selectPage(page,entityWrapper);

            //
            filmInfos = getFilmInfos(moocFilms);
            filmVO.setFilmNum(moocFilms.size());
            filmVO.setFilmInfos(filmInfos);


        }else{
            // 如果不是，则是列表页，同样需要限制内容为热映影片
            Page<MoocFilmT> page = null;
            // 根据sortId的不同，来组织不同的Page对象
            // 1-按热门搜索，2-按时间搜索，3-按评价搜索

            switch (sortId){
                case 1:
                    page = new Page<>(nowPage,nums,"film_box_office");
                    break;
                case 2:
                    page = new Page<>(nowPage,nums,"film_box_office");
                    break;
                case 3:
                    page = new Page<>(nowPage,nums,"film_time");
                    break;
                default:
                    page = new Page<>(nowPage,nums,"film_score");
            }

            // 如果sourceId,yearId,catId 不为99 ,则表示要按照对应的编号进行查询
            if(sourceId != 99){
                entityWrapper.eq("film_source",sourceId);
            }
            if(yearId != 99){
                entityWrapper.eq("film_date",yearId);
            }
            if(catId != 99){
                String catStr = "#"+catId+"#";
                entityWrapper.like("film_cats",catStr);
            }

            List<MoocFilmT> moocFilms = filmTMapper.selectPage(page,entityWrapper);
            //组织filmInfos
            filmInfos = getFilmInfos(moocFilms);
            filmVO.setFilmNum(moocFilms.size());

            // 需要总页数 totalCounts/nums -> 0 + 1 = 1
            // 每页10条，我现在有6条 -> 1
            int totalCounts = filmTMapper.selectCount(entityWrapper);
            int totalPages = (totalCounts/nums)+1;
            // 需要总页数 totalCounts/nums -> 0 + 1 = 1
            // 每页10条，我现在有6条 ->
            filmVO.setFilmInfos(filmInfos);
            filmVO.setTotalPage(totalPages);
            filmVO.setNowPage(nowPage);

        }
        return filmVO;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo>  filmInfos= new ArrayList<>();

        // 热映影片的限制条件
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",2);
        // 判断是否是首页需要的内容
        if(isLimit){
            // 如果是，则限制条数、限制内容为热映影片
            Page<MoocFilmT> page = new Page<>(1,nums);
            List<MoocFilmT> moocFilms = filmTMapper.selectPage(page,entityWrapper);

            //
            filmInfos = getFilmInfos(moocFilms);
            filmVO.setFilmNum(moocFilms.size());
            filmVO.setFilmInfos(filmInfos);


        }else{
            // 如果不是，则是列表页，同样需要限制内容为热映影片
            Page<MoocFilmT> page = null;
            // 根据sortId的不同，来组织不同的Page对象
            // 1-按热门搜索，2-按时间搜索，3-按评价搜索

            switch (sortId){
                case 1:
                    page = new Page<>(nowPage,nums,"film_preSaleNumber");
                    break;
                case 2:
                    page = new Page<>(nowPage,nums,"film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage,nums,"film_preSaleNumber");
                    break;
                default:
                    page = new Page<>(nowPage,nums,"film_preSaleNumber");
            }

            // 如果sourceId,yearId,catId 不为99 ,则表示要按照对应的编号进行查询
            if(sourceId != 99){
                entityWrapper.eq("film_source",sourceId);
            }
            if(yearId != 99){
                entityWrapper.eq("film_date",yearId);
            }
            if(catId != 99){
                String catStr = "#"+catId+"#";
                entityWrapper.like("film_cats",catStr);
            }

            List<MoocFilmT> moocFilms = filmTMapper.selectPage(page,entityWrapper);
            //组织filmInfos
            filmInfos = getFilmInfos(moocFilms);
            filmVO.setFilmNum(moocFilms.size());

            // 需要总页数 totalCounts/nums -> 0 + 1 = 1
            // 每页10条，我现在有6条 -> 1
            int totalCounts = filmTMapper.selectCount(entityWrapper);
            int totalPages = (totalCounts/nums)+1;
            // 需要总页数 totalCounts/nums -> 0 + 1 = 1
            // 每页10条，我现在有6条 ->
            filmVO.setFilmInfos(filmInfos);
            filmVO.setTotalPage(totalPages);
            filmVO.setNowPage(nowPage);

        }



        return filmVO;
    }


    private List<FilmInfo> getFilmInfos(List<MoocFilmT> moocFilms){

        List<FilmInfo> filmInfos = new ArrayList<>();
        for(MoocFilmT moocFilmT : moocFilms){
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setScore(moocFilmT.getFilmScore());
            filmInfo.setImgAddress(moocFilmT.getImgAddress());
            filmInfo.setFilmType(moocFilmT.getFilmType());
            filmInfo.setFilmScore(moocFilmT.getFilmScore());
            filmInfo.setFilmName(moocFilmT.getFilmName());
            filmInfo.setFilmId(moocFilmT.getUuid()+"");
            filmInfo.setExpectNum(moocFilmT.getFilmPresalenum());
            filmInfo.setBoxNum(moocFilmT.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(moocFilmT.getFilmTime()));

            // 将转换的对象放入结果集
            filmInfos.add(filmInfo);
        }

        return filmInfos;

    }


    @Override
    public FilmVO getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        // 即将上映影片的限制条件
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("filmStatus","3");

        // 如果不是，则是列表页，同样需要限制内容为即将上映影片
        Page<MoocFilmT> page = null;
        // 根据sortId的不同，来组织不同的Page对象
        // 1-按热门搜索，2-按时间搜索，3-按评价搜索

        switch (sortId){
            case 1:
                page = new Page<>(nowPage,nums,"film_box_office");
                break;
            case 2:
                page = new Page<>(nowPage,nums,"film_box_office");
                break;
            case 3:
                page = new Page<>(nowPage,nums,"film_time");
                break;
            default:
                page = new Page<>(nowPage,nums,"film_score");
        }

        // 如果sourceId,yearId,catId 不为99 ,则表示要按照对应的编号进行查询
        if(sourceId != 99){
            entityWrapper.eq("film_source",sourceId);
        }
        if(yearId != 99){
            entityWrapper.eq("film_date",yearId);
        }
        if(catId != 99){
            String catStr = "#"+catId+"#";
            entityWrapper.like("film_cats",catStr);
        }

        List<MoocFilmT> moocFilms = filmTMapper.selectPage(page,entityWrapper);
        //组织filmInfos
        filmInfos = getFilmInfos(moocFilms);
        filmVO.setFilmNum(moocFilms.size());

        // 需要总页数 totalCounts/nums -> 0 + 1 = 1
        // 每页10条，我现在有6条 -> 1
        int totalCounts = filmTMapper.selectCount(entityWrapper);
        int totalPages = (totalCounts/nums)+1;
        // 需要总页数 totalCounts/nums -> 0 + 1 = 1
        // 每页10条，我现在有6条 ->
        filmVO.setFilmInfos(filmInfos);
        filmVO.setTotalPage(totalPages);
        filmVO.setNowPage(nowPage);

        return filmVO;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        // 条件 -> 正在上映的，票房前10名
        // 热映影片的限制条件
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",2);

        Page<MoocFilmT> page = new Page<>(1,10,"film_box_office");

        List<MoocFilmT> moocFilms = filmTMapper.selectPage(page,entityWrapper);
        List<FilmInfo> filmInfos = getFilmInfos(moocFilms);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        // 条件 -> 即将上映的，预售前10名
        // 热映影片的限制条件
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",2);
        Page<MoocFilmT> page = new Page<>(1,10,"film_preSaleNum");

        List<MoocFilmT> moocFilms = filmTMapper.selectPage(page,entityWrapper);
        List<FilmInfo> filmInfos = getFilmInfos(moocFilms);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getTop() {
        // 条件 -> 正在上映的，评分前10名
        // 热映影片的限制条件
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",2);
        Page<MoocFilmT> page = new Page<>(1,10,"film_score");
        List<MoocFilmT> moocFilms = filmTMapper.selectPage(page,entityWrapper);
        List<FilmInfo> filmInfos = getFilmInfos(moocFilms);
        return filmInfos;
    }

    @Override
    public List<CatVO> getCats() {
        //查询实体对象
        List<MoocCatDictT> cats = catDictTMapper.selectList(null);
        //转换为VO对象
        List<CatVO> catVOS = new ArrayList<>();
        cats.forEach(cat -> catVOS.add(
                new CatVO(
                        cat.getUuid()+"'",
                        cat.getShowName()
                )
        ));


        return catVOS;
    }

    @Override
    public List<SourceVO> getSource() {
        List<MoocSourceDictT> sourceDictTList = sourceDictTMapper.selectList(null);

        List<SourceVO> sourceVOList = new ArrayList<>();
        sourceDictTList.forEach(source -> sourceVOList.add(
                new SourceVO(
                        source.getUuid()+"",
                        source.getShowName()
                )
        ));


        return sourceVOList;
    }

    @Override
    public List<YearVO> getYear() {

        List<MoocYearDictT> yearDictTList = yearDictTMapper.selectList(null);

        List<YearVO> yearVOList = new ArrayList<>();
        yearDictTList.forEach(year -> yearVOList.add(
                new YearVO(
                        year.getUuid()+"",
                        year.getShowName()
                )
        ));

        return yearVOList;
    }

    @Override
    public FilmDetailVO getFilmDetail(int searchType, String searchParam) {
        FilmDetailVO filmDetailVO = null;

        //searchType 1 按名称 2 按ID来查找
        if(searchType == 1){
            //filmDetailVO = filmTMapper
            filmDetailVO = filmTMapper.getFilmDetailById("%"+searchParam+"%");
        }else{
            filmDetailVO = filmTMapper.getFilmDetailById(searchParam);
        }

        return filmDetailVO;
    }

    @Override
    public FilmDescVO getFilmDesc(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);
        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setBiography(moocFilmInfoT.getBiography());
        filmDescVO.setFilmId(filmId);

        return filmDescVO;
    }

    private MoocFilmInfoT getFilmInfo(String filmId){
        MoocFilmInfoT moocFilmInfoT = new MoocFilmInfoT();
        moocFilmInfoT.setFilmId(filmId);

        moocFilmInfoT = filmInfoTMapper.selectOne(moocFilmInfoT);
        return moocFilmInfoT;
    }
    @Override
    public ImgVO getImgs(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);

        String filmImgStr = moocFilmInfoT.getFilmImgs();
        String[] filmImgs = filmImgStr.split(",");

        ImgVO imgVO = new ImgVO(
                filmImgs[0],
                filmImgs[1],
                filmImgs[2],
                filmImgs[3],
                filmImgs[4]
        );
        return imgVO;
    }

    @Override
    public ActorVO getDectInfo(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);

        Integer directId = moocFilmInfoT.getDirectorId();

        MoocActorT moocActorT = actorTMapper.selectById(directId);
        ActorVO actorVO = new ActorVO();
        actorVO.setImgAddress(moocActorT.getActorImg());
        actorVO.setDirectorName(moocActorT.getActorName());

        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {
        List<ActorVO> actors = actorTMapper.getActors(filmId);
        return actors;
    }
}
