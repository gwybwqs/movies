package com.liu.movices.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "touristSetting")
public class TouristSetting {
    @Id
    private Integer id;

    /**最高*/
    @Column(name = "max_score", nullable = false, length = 64)
    private BigDecimal maxScore;
    /**最低*/
    @Column(name = "min_score", nullable = false, length = 64)
    private BigDecimal minScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(BigDecimal maxScore) {
        this.maxScore = maxScore;
    }

    public BigDecimal getMinScore() {
        return minScore;
    }

    public void setMinScore(BigDecimal minScore) {
        this.minScore = minScore;
    }
}
