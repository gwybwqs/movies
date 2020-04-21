package com.liu.movices.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "movie",uniqueConstraints = {@UniqueConstraint(columnNames={"mo_id", "mo_name"})})
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mo_id", nullable = false, length = 11)
    private Integer id;
    /**电影名*/
    @Column(name = "mo_name", nullable = false, length = 64)
    private String name;
    /**主演*/
    @Column(name = "mo_performer", nullable = false, length = 255)
    private String performer;
    /**海报*/
    @Column(name = "mo_msg", nullable = false, length = 255)
    private String msg;
    /**类型*/
    @Column(name = "mo_type", nullable = false, length = 255)
    private String type;
    /**详细信息*/
    @Column(name = "mo_information", nullable = false, length = 255)
    private String information;
    /**资源地址*/
    @Column(name = "mo_url", nullable = false, length = 255)
    private String url;

    @Column(name = "mo_avgScore", nullable = true, length = 11)
    private BigDecimal avgScore;


    public Integer getId() {
        return id;
    }
    public Integer geId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public BigDecimal getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(BigDecimal avgScore) {
        this.avgScore = avgScore;
    }

}
