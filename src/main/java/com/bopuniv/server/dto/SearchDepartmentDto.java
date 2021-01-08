package com.bopuniv.server.dto;

import java.util.List;

public class SearchDepartmentDto {
    private int offset;
    private int limit;
    private long total;
    private List<DepartmentDto> departments;

    public SearchDepartmentDto(){}

    public SearchDepartmentDto(int offset, int limit, long total, List<DepartmentDto> departments){
        this.offset = offset;
        this.limit = limit;
        this.total = total;
        this.departments = departments;
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

    public List<DepartmentDto> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentDto> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return "SearchDepartmentDto{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", total=" + total +
                ", departments=" + departments +
                '}';
    }
}
