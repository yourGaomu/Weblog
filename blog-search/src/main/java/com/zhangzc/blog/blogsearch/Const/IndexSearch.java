package com.zhangzc.blog.blogsearch.Const;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class IndexSearch {

    public static String indexName;


    public static String GetIndexName() {
        return indexName;
    }

    public static void SetIndexName(String indexName) {
        IndexSearch.indexName = indexName;
    }
}
