package com.liu.movices.dao;

import com.liu.movices.entity.Movies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MoviesDao extends JpaRepository<Movies,Integer>{
    Movies findByName(String name);
    /**根据电影名分页查询*/
    Page<Movies> findByNameLike( String name, Pageable pageable);
    /**根据电影类型分页查询*/
    Page<Movies> findByTypeLike( String type, Pageable pageable);
    /**根据电影名活主演分页查询*/
    Page<Movies> findByNameLikeOrPerformerLike(String name, String performer,Pageable pageable);

    Movies findById(int id);
}
