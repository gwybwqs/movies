package com.liu.movices.controller;

import com.liu.movices.common.Pages;
import com.liu.movices.entity.Movies;
import com.liu.movices.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/movies")
public class MoviesController {
    @Autowired
    MoviesService moviesService;


    /**获取用户信息所有*/
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Pages<Movies> list (Integer current, Integer pageSize){
        return moviesService.list(current,pageSize);
    }


    /**添加*/
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Boolean add(@RequestBody Movies movies){
        return moviesService.add(movies);
    }

    /**修改*/
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Boolean update(@RequestBody Movies movies){
        return moviesService.update(movies);
    }

    /**删除*/
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public boolean del(@RequestBody Movies movies){
        return moviesService.del(movies);
    }


    /**搜索*/
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Pages<Movies> search (String name,Integer current, Integer pageSize){
        return moviesService.search("%"+name+"%",current,pageSize);
    }

    /**用户搜索*/
    @RequestMapping(value = "/searchList", method = RequestMethod.GET)
    public Pages<Movies> search (String name,String performan,Integer current, Integer pageSize){
        return moviesService.search("%"+name+"%","%"+performan+"%",current,pageSize);
    }


    /**用户页面展示*/
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public Pages<Movies> show (Integer current, Integer pageSize){
        return moviesService.show(current,pageSize);
    }

    /**用户类型展示*/
    @RequestMapping(value = "/showType", method = RequestMethod.GET)
    public Pages<Movies> typeShow (String type,Integer current, Integer pageSize){
        return moviesService.typeShow("%"+type+"%",current,pageSize);
    }

    /**高分推荐*/
    @RequestMapping(value = "/highScore", method = RequestMethod.GET)
    public Pages<Movies> highScoreShow (Integer current, Integer pageSize){
        return moviesService.highScoreShow(current,pageSize);
    }

    /** 可能喜欢推荐*/
    @RequestMapping(value = "/like", method = RequestMethod.GET)
    public Pages<Movies> like (String id,Integer current, Integer pageSize){
        return moviesService.like(id,current,pageSize);
    }
}
