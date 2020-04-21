package com.liu.movices.service;

import com.liu.movices.dao.RelationDao;
import com.liu.movices.entity.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationService {
    @Autowired
    RelationDao relationDao;

    public String add(Relation relation){
        Relation relation1 = relationDao.findByMoIdAndAndAuId(relation.getMoId(),relation.getAuId());
        String str = "";
        if (relation1 == null){
            relation1 = new Relation();
            relation1.setAuId(relation.getAuId());
            relation1.setMoId(relation.getMoId());
            if (relation.getScore() != null){
                relation1.setScore(relation.getScore());
                str = "评分成功";
            }
            if (relation.getWatch() != null){
                relation1.setWatch(relation.getWatch());
            }
            if (relation.getDownload() != null){
                relation1.setDownload(relation.getDownload());
            }
            relationDao.save(relation1);
        } else {
            if (relation1.getDownload() == null && relation.getDownload() != null){
                relation1.setDownload(relation.getDownload());
            }
            if (relation1.getWatch() == null && relation.getWatch() != null){
                relation1.setWatch(relation.getWatch());
            }
            if (relation1.getScore() == null){
                if (relation.getScore() != null){
                    relation1.setScore(relation.getScore());
                    str = "评分成功";
                }
            } else {
                str = "您已评过分";
            }
            relationDao.save(relation1);
        }
        return str;
    }
}
