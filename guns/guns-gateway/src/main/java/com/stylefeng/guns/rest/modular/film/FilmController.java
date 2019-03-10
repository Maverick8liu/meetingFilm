package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.stylefeng.guns.film.FilmAsyncServiceApi;
import com.stylefeng.guns.film.FilmServiceApi;
import com.stylefeng.guns.film.vo.*;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.vo.FilmRequestVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/film/")
public class FilmController {

    private static final String img_pre = "http://img.meetingshop.cn/";

    @Reference(interfaceClass = FilmServiceApi.class,check=false)
    FilmServiceApi filmServiceApi;

    @Reference(interfaceClass = FilmAsyncServiceApi.class,async = true)
    FilmAsyncServiceApi filmAsyncServiceApi;

    @RequestMapping(value="getIndex",method= RequestMethod.GET)
    public ResponseVO<FilmIndexVO> gentIndex(){

        FilmIndexVO filmIndexVO = new FilmIndexVO();

        filmIndexVO.setBanners(filmServiceApi.getBanners());
        filmIndexVO.setHotFilm(filmServiceApi.getHotFilms(true,8,1,1,99,99,99));
        filmIndexVO.setSoonFilm(filmServiceApi.getSoonFilms(true,8,1,1,99,99,99));
        filmIndexVO.setBoxRanking(filmServiceApi.getBoxRanking());
        filmIndexVO.setExpectRanking(filmServiceApi.getExpectRanking());
        filmIndexVO.setTop100(filmServiceApi.getTop());

        return ResponseVO.success(img_pre,filmIndexVO);
    }

    @RequestMapping(value="getConditionList",method= RequestMethod.GET)
    public ResponseVO<FilmIndexVO> getConditionList(@RequestParam(name="catId",required = false,defaultValue = "99")String catId,

                                                    @RequestParam(name="sourceId",required = false,defaultValue = "99")String sourceId,
                                                    @RequestParam(name="yearId",required = false,defaultValue = "99")String yearId){

        FilmConditionVO filmConditionVO = new FilmConditionVO();
        //类型集合
        List<CatVO> cats = filmServiceApi.getCats();
        List<CatVO> catResult = new ArrayList<>();
        CatVO cat = null;
        boolean flag = false;
        for(CatVO catVO:cats){
            //判断集合是否存在catid，如果存在，则将对应的实体变成active状态
            if(catVO.getCatId().equals("99")){
                cat = catVO;
                continue;
            }
            if(catVO.getCatId().equals(catId)){
                flag = true;
                catVO.setActive(true);
            }
            catResult.add(catVO);

        }
        if(!flag){
            cat.setActive(true);
            catResult.add(cat);
        }else{
            cat.setActive(false);
            catResult.add(cat);
        }
        //类型集合
        flag = false;
        List<SourceVO> sources = filmServiceApi.getSource();
        List<SourceVO> sourceResult = new ArrayList<>();
        SourceVO source = null;
        for(SourceVO sourceVO:sources){
            //判断集合是否存在catid，如果存在，则将对应的实体变成active状态
            if(sourceVO.getSourceId().equals("99")){
                source = sourceVO;
                continue;
            }
            if(sourceVO.getSourceId().equals(catId)){
                flag = true;
                sourceVO.setActive(true);
            }
            sourceResult.add(sourceVO);

        }
        if(!flag){
            source.setActive(true);
            sourceResult.add(source);
        }else{
            source.setActive(false);
            sourceResult.add(source);
        }

        //类型集合
        flag = false;
        List<YearVO> years = filmServiceApi.getYear();
        List<YearVO> yearResult = new ArrayList<>();
        YearVO year = null;
        for(YearVO yearVO:years){
            //判断集合是否存在catid，如果存在，则将对应的实体变成active状态
            if(yearVO.getYearId().equals("99")){
                year = yearVO;
                continue;
            }
            if(yearVO.getYearId().equals(catId)){
                flag = true;
                yearVO.setActive(true);
            }
            yearResult.add(yearVO);

        }
        if(!flag){
            year.setActive(true);
            yearResult.add(year);
        }else{
            year.setActive(false);
            yearResult.add(year);
        }
        filmConditionVO.setCatInfo(catResult);
        filmConditionVO.setSourceInfo(sourceResult);
        filmConditionVO.setYearInfo(yearResult);
        return ResponseVO.success(filmConditionVO);


    }

    @RequestMapping(value="getFilms",method = RequestMethod.GET)
    public ResponseVO getFilms(FilmRequestVO filmRequestVO){
        String img_pre = "http://imig.meetingshop.cn/";

        FilmVO filmVO = null;


        //根据showType 判断影片查询类型
        switch (filmRequestVO.getShowType()){
            case 1:
                filmVO = filmServiceApi.getHotFilms(
                        false,
                        filmRequestVO.getPageSize(),
                        filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(),
                        filmRequestVO.getSourceId(),
                        filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;
            case 2:
                filmVO = filmServiceApi.getSoonFilms(
                        false,
                        filmRequestVO.getPageSize(),
                        filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(),
                        filmRequestVO.getSourceId(),
                        filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;
            case 3:
                filmVO = filmServiceApi.getClassicFilms(
                        filmRequestVO.getPageSize(),
                        filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(),
                        filmRequestVO.getSourceId(),
                        filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;

            default:
                filmVO = filmServiceApi.getHotFilms(
                        false,
                        filmRequestVO.getPageSize(),
                        filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(),
                        filmRequestVO.getSourceId(),
                        filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;
        }

        return ResponseVO.success(filmVO.getNowPage(),filmVO.getTotalPage(),img_pre,filmVO.getFilmInfos());

    }


    @RequestMapping(value="films/{searchParam}",method = RequestMethod.GET)
    public ResponseVO films(@PathVariable("searchParam")String searchParam,int searchType) throws Exception {
        //根据SearchType,判断查询类型
       FilmDetailVO filmDetailVO =  filmServiceApi.getFilmDetail(searchType,searchParam);

       if(filmDetailVO == null){
           return  ResponseVO.appFail("传递信息有误！");
       }else if(filmDetailVO.getFilmId() == null || filmDetailVO.getFilmId().trim().length() == 0){
           return  ResponseVO.appFail("传递信息有误！");
       }

       String filmId = filmDetailVO.getFilmId();
        //不同的查询类型，传入条件不同
        //查询影片的详细信息 -> Dubbo的异步调
        //FilmDescVO filmDescVO = filmAsyncServiceApi.getFilmDesc(filmId);
        filmAsyncServiceApi.getFilmDesc(filmId);
        Future<FilmDescVO> filmDescFuture = RpcContext.getContext().getFuture();
        //image info
        filmAsyncServiceApi.getImgs(filmId);
        Future<ImgVO> imgFuture = RpcContext.getContext().getFuture();
        //direct info
        //ActorVO director = filmAsyncServiceApi.getDectInfo(filmId);
        filmAsyncServiceApi.getDectInfo(filmId);
        Future<ActorVO> directorFuture = RpcContext.getContext().getFuture();
        //actors info
       // List<ActorVO> actors = filmAsyncServiceApi.getActors(filmId);
        filmAsyncServiceApi.getActors(filmId);
        Future<List<ActorVO>> actorFuture = RpcContext.getContext().getFuture();
        //组织info对象
        InfoRequstVO infoRequstVO = new InfoRequstVO();

        //组织Actor属性
        ActorRequestVO actorRequestVO = new ActorRequestVO();
        actorRequestVO.setActors(actorFuture.get());
        actorRequestVO.setDirector(directorFuture.get());

        infoRequstVO.setActors(actorRequestVO);
        infoRequstVO.setBiography( filmDescFuture.get().getBiography());
        infoRequstVO.setImgVO(imgFuture.get());
        infoRequstVO.setFilmId(filmId);

        filmDetailVO.setInfo04(infoRequstVO);

        return ResponseVO.success(img_pre,filmDetailVO);
    }
}
