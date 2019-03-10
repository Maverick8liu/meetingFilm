package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.cinema.vo.CinemaInfoVO;
import com.stylefeng.guns.cinema.vo.FilmInfoVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaFieldsResponseVO {

    private CinemaInfoVO cinemaInfo;
    private List<FilmInfoVO> filmInfoList;


}
