package com.chock.service;

import com.chock.dao.JdbcTemplateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

/**
 * Created by chenwp on 2018/2/28.
 */
@Service
public class QueryService {

    @Autowired
    private JdbcTemplateDao jdbcTemplateDao;

    public List<String> readSql(String sqlPath){
        Resource resource = new ClassPathResource(sqlPath);
        List<String> sqlList = new ArrayList<>();
        try(InputStream inputStream = resource.getInputStream()){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String lineTxt = null;

            while ((lineTxt = bufferedReader.readLine()) != null)
            {
                sqlList.add(lineTxt);
            }
            bufferedReader.close();
        }catch (IOException io){

        }
        return sqlList;
    }

    public Long query(String sqlPath) throws Exception{
        List<String> sqlList = this.readSql(sqlPath);
        List<CompletableFuture<List<Map<String, Object>>>> resultFuture = new ArrayList<>();
        Long startTime = System.currentTimeMillis();
        for(String sql: sqlList){
            resultFuture.add(jdbcTemplateDao.queryAsync(sql));
        }
        CompletableFuture completableFuture = this.sequence(resultFuture);
        completableFuture.join();
        Long endTime = System.currentTimeMillis();
        Long cost = endTime - startTime;
        for(CompletableFuture<List<Map<String, Object>>> result: resultFuture){
            System.out.println(result.get());
        }
        System.out.println("time:" + cost);
        return cost;
    }


    private <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> com) {
        return CompletableFuture.allOf(com.toArray(new CompletableFuture[com.size()]))
                .thenApply(v -> com.stream()
                        .map(CompletableFuture::join)
                        .collect(toList())
                );
    }
}
