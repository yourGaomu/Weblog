package com.zhangzc.blog.blogsearch.Service.Impl;

import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.RedisUtil;
import com.zhangzc.blog.blogsearch.Const.IndexSearch;
import com.zhangzc.blog.blogsearch.Index.ArticleIndex;
import com.zhangzc.blog.blogsearch.Pojo.Vo.SearchArticlePageListReqVO;
import com.zhangzc.blog.blogsearch.Pojo.Vo.SearchArticlePageListRspVO;
import com.zhangzc.blog.blogsearch.Service.SearchService;
import com.zhangzc.blog.blogsearch.Utils.HelightUtil;
import com.zhangzc.blog.blogsearch.Utils.LuceneUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchServiceImpl implements SearchService {

    private final LuceneUtil luceneUtil;
    private final RedisUtil redisUtil;
    private final HelightUtil helightUtil;

    @Override

    public PageResult searchArticlePageList(SearchArticlePageListReqVO searchArticlePageListReqVO) {
        //获取当前的存储目录
        String indexName = IndexSearch.GetIndexName();
        //获取查询的字段
        String[] columns = {ArticleIndex.COLUMN_TITLE, ArticleIndex.COLUMN_SUMMARY};
        // 查询记录，分页查询
        List<Document> searchResult = luceneUtil.search(indexName
                , searchArticlePageListReqVO.getWord()
                , columns
                , Math.toIntExact(searchArticlePageListReqVO.getCurrent())
                , Math.toIntExact(searchArticlePageListReqVO.getSize()));
        //如果没有记录
        if (searchResult == null || searchResult.isEmpty()) {
            return PageResult.Success(null, 0L, 0L, 0L);
        }
        //获取加上了高亮显示的结果
        List<SearchArticlePageListRspVO> searchArticlePageListRspVOS = helightUtil.setHelight(searchArticlePageListReqVO.getWord(), searchResult);
        //优先查询有没有缓存
        Long total = 0L;
        String searchKey = indexName + "::" + ArticleIndex.COLUMN_TITLE + "&&" + ArticleIndex.COLUMN_SUMMARY;
        Object value = redisUtil.get(searchKey);
        if (value != null) {
            try {
                total = value instanceof Long
                        ? (Long) value
                        : Long.valueOf(value.toString());
            } catch (NumberFormatException e) {
                // 处理缓存值格式异常的情况
                log.error("Redis缓存值格式错误，key={}, value={}", searchKey, value, e);
                total = luceneUtil.searchTotal(indexName, searchArticlePageListReqVO.getWord(), columns);
                redisUtil.set(searchKey, total);
            }
        } else {
            total = luceneUtil.searchTotal(indexName, searchArticlePageListReqVO.getWord(), columns);
            redisUtil.set(searchKey, total,60*60);
        }
        return PageResult.Success(searchArticlePageListRspVOS, total, searchArticlePageListReqVO.getSize(), searchArticlePageListReqVO.getCurrent());
    }
}
