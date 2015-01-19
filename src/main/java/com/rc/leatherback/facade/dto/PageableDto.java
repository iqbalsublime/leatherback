package com.rc.leatherback.facade.dto;

import java.io.Serializable;
import java.util.List;

public class PageableDto<TEntity> implements Serializable {
    private static final long serialVersionUID = 1316370295288973116L;

    private int totalItems;
    private int currentPage;
    private List<TEntity> data;

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<TEntity> getData() {
        return data;
    }

    public void setData(List<TEntity> data) {
        this.data = data;
    }
}
