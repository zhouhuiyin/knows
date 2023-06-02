package com.example.knows.search.repository;
import com.example.knows.search.vo.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

// Spring家族框架都将数据访问层称之为Repository
@Repository
public interface ItemRepository extends ElasticsearchRepository<Item,Long> {

    //MybatisPlus框架继承BaseMapper父接口实现自带很多功能
    // 这里继承的ElasticsearchRepository接口效果类似,自带基本增删改查
    // ElasticsearchRepository<[VO类型],[id类型]>


    // 自定义查询方法
    // SpringData框架都支持使用方法名称来表示查询逻辑
    //              所以方法名称必须严格按照格式要求,不能有任何错误
    //   query:查询 Item是查询索引 Items表示返回多个
    //   By:类似于sql的where后面跟条件   Title:条件属性名称 Matches:模糊匹配

    Iterable<Item> queryItemsByTitleMatches(String title);

    //  多条件查询
    Iterable<Item> queryItemsByTitleMatchesAndBrandMatches(String title,String brand);

    // 排序查询
    Iterable<Item> queryItemsByTitleMatchesOrBrandMatchesOrderByPriceDesc(
            String title,String brand);

    // 分页查询
    Page<Item> queryItemsByTitleMatchesOrBrandMatchesOrderByPriceDesc(
            String title, String brand, Pageable pageable);
}
