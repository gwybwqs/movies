package com.liu.movices.common;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pages<T>  {
    private Long total;
    private Integer current;
    private Integer pageSize;
    private List<T> data;

    public Pages() {
    }

    public Pages(Page<?> page) {
        if (page == null || page.getTotalElements() == 0) {
            this.total = 0L;
            this.current = 0;
            this.pageSize = 10;
            this.data = new ArrayList<T>(0);
        } else {
            this.total = page.getTotalElements();
            this.current = page.getNumber() + 1;
            this.pageSize = page.getNumberOfElements();
        }
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
