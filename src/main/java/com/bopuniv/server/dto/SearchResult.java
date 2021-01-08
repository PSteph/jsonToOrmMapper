package com.bopuniv.server.dto;

import java.util.List;

public class SearchResult<T> {
    private int offset;
    private int limit;
    private long total;
    private List<T> data;

    public SearchResult() { }

    public SearchResult(int offset, int limit, long total, List<T> data) {
        this.offset = offset;
        this.limit = limit;
        this.total = total;
        this.data = data;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", total=" + total +
                ", data=" + data +
                '}';
    }
}
