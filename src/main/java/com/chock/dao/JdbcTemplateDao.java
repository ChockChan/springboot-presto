package com.chock.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by chenwp on 2018/2/28.
 */
@Repository
public class JdbcTemplateDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Async
    public CompletableFuture<Map> query(String sql){
        Map<String, Object> result = jdbcTemplate.queryForObject(sql, Map.class);
        return CompletableFuture.completedFuture(result);
    }
}
