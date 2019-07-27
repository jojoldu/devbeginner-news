package com.jojoldu.devbeginnernews.batch.common;

import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;

public class JpaItemListPersistWriter<T> implements ItemWriter<List<T>> {

    private JpaItemPersistWriter<T> jpaItemWriter;

    public JpaItemListPersistWriter(JpaItemPersistWriter<T> jpaItemWriter) {
        this.jpaItemWriter = jpaItemWriter;
    }

    @Override
    public void write(List<? extends List<T>> items) {
        List<T> totalList = new ArrayList<>();

        for(List<T> list : items){
            totalList.addAll(list);
        }

        jpaItemWriter.write(totalList);
    }

}
