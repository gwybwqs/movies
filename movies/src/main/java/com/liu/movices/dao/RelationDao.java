package com.liu.movices.dao;

import com.liu.movices.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationDao extends JpaRepository<Relation,Integer> {
    Relation findByMoIdAndAndAuId(Integer moId,String auId);
    List<Relation> findByAuId(String id);
    List<Relation> findByMoId(Integer id);
}
