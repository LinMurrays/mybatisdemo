package com.mybatisdemo;


import com.mybatisdemo.elasticPackage.bean.Article;
import com.mybatisdemo.elasticPackage.bean.Book;
import com.mybatisdemo.elasticPackage.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ElasticSearchApplicationTests {

    @Autowired
    JestClient jestClient;

    @Autowired
    BookRepository bookRepository;

    @Test//这个方法是插入进去
    public void test02(){
		Book book = new Book();
		book.setId(1);
		book.setBookName("西游记");
		book.setAuthor("吴承恩");
		//#这个版本是适应继承那个测试使用的版本,暂时docker容器启动不了那个elasticsearch，所以这个测试无法完成
        // bookRepository.index(book);
    }

    @Test//这个方法是查询含有"游"的方法
    public void test03(){
        //#这个版本是适应继承那个测试使用的版本,暂时docker容器启动不了那个elasticsearch，所以这个测试无法完成
//        for (Book book : bookRepository.findByBookNameLike("游")) {
//            System.out.println(book);
//        };
    }

    @Test
    public void contextLoads() {
        //1、给Es中索引（保存）一个文档；
        Article article = new Article();
        article.setId(1);
        article.setTitle("haoxiaoxi");
        article.setAuthor("zhangsan");
        article.setContent("Hello World");

        //构建一个索引功能
        Index index = new Index.Builder(article).index("atguigu").type("news").build();

        try {
            //执行
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //测试搜索
    @Test
    public void search(){

        //查询表达式
        String json ="{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"content\" : \"hello\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        //更多操作：https://github.com/searchbox-io/Jest/tree/master/jest
        //构建搜索功能
        Search search = new Search.Builder(json).addIndex("atguigu").addType("news").build();

        //执行
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
