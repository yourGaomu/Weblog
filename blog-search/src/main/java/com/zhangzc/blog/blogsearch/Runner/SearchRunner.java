package com.zhangzc.blog.blogsearch.Runner;

import com.zhangzc.blog.blogcommon.Service.TArticleContentService;
import com.zhangzc.blog.blogcommon.Service.TArticleService;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.BizException;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticle;
import com.zhangzc.blog.blogcommon.pojo.domain.TArticleContent;
import com.zhangzc.blog.blogsearch.Config.LuceneYml;
import com.zhangzc.blog.blogsearch.Const.IndexSearch;
import com.zhangzc.blog.blogsearch.Index.ArticleIndex;
import com.zhangzc.blog.blogsearch.Utils.LuceneUtil;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class SearchRunner implements CommandLineRunner {

    private final LuceneYml luceneYml;
    private final LuceneUtil luceneUtil;
    private final TArticleService tarticleService;
    private final TArticleContentService tarticleContentService;

    //初始化索引
    @Override
    public void run(String... args) throws Exception {
        System.out.println("开始初始化——————————————————");
        //时间序列化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<TArticle> articles = tarticleService.lambdaQuery().list();
        //有数据可以创立
        if (!articles.isEmpty()) {
            List<Long> collect = articles.stream().map(TArticle::getId).collect(Collectors.toList());
            //获取文章内容
            Map<Long, String> map = tarticleContentService.lambdaQuery()
                    .in(TArticleContent::getId, collect)
                    .list().stream()
                    .collect(Collectors.toMap(TArticleContent::getId, TArticleContent::getContent));
            String indexDir = luceneYml.getIndexDir();
            if (indexDir.isEmpty()) {
                throw new BizException("500", "没有定于索引的创建位置");
            }
            //建立索引创建的目录
            //先获取当前项目的目录位置
            String directoryName = System.getProperty("user.dir");
            //拼接当前需要生成的目录位置
            String indexPath = directoryName + "/" + indexDir;
            System.out.println("文件位置是" + indexPath);
            //存储起来
            IndexSearch.SetIndexName(indexPath);
            //进行索引的创建,遍历赋值
            List<Document> collect1 = articles.stream().map(sigen -> {
                Document document = new Document();
                // 设置文档字段 Field
                document.add(new TextField(ArticleIndex.COLUMN_ID, String.valueOf(sigen.getId()), Field.Store.YES));
                document.add(new TextField(ArticleIndex.COLUMN_TITLE, sigen.getTitle(), Field.Store.YES));
                document.add(new TextField(ArticleIndex.COLUMN_COVER, sigen.getCover(), Field.Store.YES));
                document.add(new TextField(ArticleIndex.COLUMN_SUMMARY, sigen.getSummary(), Field.Store.YES));
                document.add(new TextField(ArticleIndex.COLUMN_CONTENT, map.get(sigen.getId()), Field.Store.YES));
                document.add(new TextField(ArticleIndex.COLUMN_CREATE_TIME, sdf.format(sigen.getCreateTime()), Field.Store.YES));
                return document;
            }).collect(Collectors.toList());
            luceneUtil.createIndex(indexPath, collect1);
            System.out.println("初始化结束——————————————");
        }
    }
}
