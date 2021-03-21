package com.lei.obtain.service;

import com.lei.utils.PageUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @Author LEI
 * @Date 2021/3/16 12:46
 * @Description TODO
 */
public interface ISearchService {
    PageUtils<Map<String, Object>> search(Integer pageNum, Integer pageSize, String state) throws IOException;
}
