package com.liu.movices.entity;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "relation")
public class Relation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**评分*/
    @Column(name = "re_score", nullable = true, length = 11)
    private BigDecimal score;
    /**历史行为*/
    @Column(name = "re_watch", nullable = true, length = 11)
    private Integer watch;
    @Column(name = "re_download", nullable = true, length = 11)
    private Integer download;
    /** 外键*/
    @Column(name = "au_id", nullable = true, length = 11)
    private String auId;
    @Column(name = "mo_id", nullable = true, length = 11)
    private Integer moId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getWatch() {
        return watch;
    }

    public void setWatch(Integer watch) {
        this.watch = watch;
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(Integer download) {
        this.download = download;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getAuId() {
        return auId;
    }

    public void setAuId(String auId) {
        this.auId = auId;
    }

    public Integer getMoId() {
        return moId;
    }

    public void setMoId(Integer moId) {
        this.moId = moId;
    }
}
