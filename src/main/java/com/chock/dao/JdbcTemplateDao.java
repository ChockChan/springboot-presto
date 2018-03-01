package com.chock.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by chenwp on 2018/2/28.
 */
@Repository
public class JdbcTemplateDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Async
    public CompletableFuture<List<Map<String, Object>>> queryAsync(String sql){
        Long startTime;
        Long endTime;
        List<Map<String, Object>> result;
        startTime = System.currentTimeMillis();
        result = jdbcTemplate.queryForList(sql);
        endTime = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        map.put("queryTime", endTime-startTime);
        result.add(map);
        return CompletableFuture.completedFuture(result);
    }

    public List<Map<String, Object>> querySync(String sql){
        return jdbcTemplate.queryForList(sql);
    }
}
