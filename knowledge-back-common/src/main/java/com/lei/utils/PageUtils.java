package com.lei.utils;

import java.util.List;

/**
 * @Author LEI
 * @Date 2021/2/18 17:36
 * @Description 自定义分页工具类 ResponseModel<PageUtils<Topic>>
 */
public class PageUtils<T> {

    private int pageNo;
    private int pageSize;
    private int total;
    private int totalPage;
    private int start;
    private List<T> results;

    public void doPage(List<T> lists) {
        int count = lists.size();
        setTotal(count);
        setTotalPage(count % pageSize == 0 ? count / pageSize : count / pageSize + 1);
        if (getPageNo() == 1) {
            setStart(0);
        } else {
            setStart((getPageNo() - 1) * getPageSize());
        }
        if (getPageSize() > lists.size()) {
            setResults(lists);
        } else {
            setResults(lists.subList(getStart(),
                    (count - getStart()) > getPageSize() ? getStart() + getPageSize() : count));
        }
    }

    public PageUtils() {
        super();
    }

    public PageUtils(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
