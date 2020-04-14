package com.lsw.search;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lsw.search.dao.ItemRepository;
import com.lsw.search.pojo.Item;
import lombok.SneakyThrows;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.function.Consumer;

@SpringBootTest(classes = SearchApplication.class)
@RunWith(SpringRunner.class)
public class TestDataElasticSearch {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * redistemplate使用excutePipeline可以实现批量插入
     */
    @Test
    public void test() {
//        Item build = Item.builder().id(1L).category("手机").brand("apple").price(50000.0).images("/pic/phone/apple/1")
//                .title("apple iphone6s 128g 深空灰").build();
//        redisTemplate.opsForValue().set("phones:phone1", build);

//        for (int i = 1; i < 10; i++) {
//            Item build = Item.builder().id(Long.valueOf(String.valueOf(i))).category("手机").brand("apple").price(50000.0 + i * 200).images("/pic/phone/apple/" + i)
//                    .title("apple iphone" + i + "s 128g 深空灰").build();
//            redisTemplate.opsForValue().set("phones:phone" + i, build);
//        }

        // 批量插入
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @SneakyThrows
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {

                for (int i = 1; i < 10; i++) {
                    Item build = Item.builder().id(Long.valueOf(String.valueOf(i + 30))).category("手机").brand("红米").price(500.0 + i * 200).images("/pic/phone/redmi/" + i)
                            .title("红米" + i + " 12g 金色 灰色 粉色").build();
                    connection.set(redisTemplate.getStringSerializer().serialize("phones:phone3" + i),
                            new GenericJackson2JsonRedisSerializer().serialize(build));
                }
                return null;
            }
        });
    }

    @Test
    public void testCreate() {
        // 创建索引，会根据Item类的@Document注解信息来创建
        boolean index = elasticsearchTemplate.createIndex(Item.class);
        System.out.println("创建索引 = " + index);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        boolean b = elasticsearchTemplate.putMapping(Item.class);
        System.out.println("创建映射 = " + b);
    }

    @Test
    public void test2() {

        Item item = (Item) redisTemplate.opsForValue().get("phones:phone1");
        if (item != null) {
            System.out.println("item = " + item);
        }

        Item save = itemRepository.save(item);
        System.out.println("save = " + save);
    }

    /**
     * 从redis里边取一些数据然后初始化到es中
     */
    @Test
    public void test3() {
        List<Object> objects = redisTemplate.executePipelined(new RedisCallback<Item>() {
            @Override
            public Item doInRedis(RedisConnection connection) throws DataAccessException {
                for (int i = 1; i < 10; i++) {
                    String key = "phones:phone3" + i;
                    connection.get(redisTemplate.getStringSerializer().serialize(key));
                }
                return null;
            }
        });

        long start = System.currentTimeMillis();
        String jsonString = JSON.toJSONString(objects);
        List<Item> items = JSONArray.parseArray(jsonString, Item.class);
        long end = System.currentTimeMillis();
//        System.out.println("jsonString = " + jsonString);
        System.out.println("items = " + items);
        System.out.println("start-end = " + (end - start));

        itemRepository.saveAll(items);

    }

    @Test
    public void test4() {
        Object o = redisTemplate.opsForValue().get("phones:phone21");
        String jsonString = JSON.toJSONString(o);
        Item item = JSONObject.parseObject(jsonString, Item.class);
        System.out.println("item = " + item);
//        for (int i = 1; i < 10; i++) {
//            redisTemplate.get
//        }
    }

    @Test
    public void test5() {
//        Iterable<Item> price = itemRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION, "price"));
//        for (Item item : price) {
//            System.out.println("item = " + item);
//        }

//        List<Item> byPriceBetween = itemRepository.findByPriceBetween(100.0, 9999.0);
//        byPriceBetween.forEach(System.out::println);

        List<Item> byCategoryAndBrand = itemRepository.findByCategoryAndBrand("手机", "apple");
        byCategoryAndBrand.forEach(System.out::println);

    }

    /**
     * 测试高级查询 _search springdata自带的一些查询
     */
    @Test
    public void test6() {

        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("title", "aple");
        Iterable<Item> search = itemRepository.search(fuzzyQueryBuilder);
        search.forEach(item -> System.out.println("item = " + item));

    }

    /**
     * 自定义查询 + 排序
     */
    @Test
    public void test7() {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery build = nativeSearchQueryBuilder.withQuery(QueryBuilders.fuzzyQuery("title", "aple"))
                .withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC))
                .withHighlightFields(new HighlightBuilder.Field("title"))
                .withPageable(PageRequest.of(0, 20)) // 分页实现
                .build();
        Page<Item> search = itemRepository.search(build);
        long totalElements = search.getTotalElements();
        System.out.println("totalElements = " + totalElements);
        int totalPages = search.getTotalPages();
        System.out.println("totalPages = " + totalPages);
        int size = search.getSize();
        System.out.println("每一页大小size = " + size);
        int number = search.getNumber();
        System.out.println("当前页number = " + number);
        search.forEach(item -> System.out.println(item));
    }

    /**
     * 聚合操作
     */
    @Test
    public void test8() {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery agg = nativeSearchQueryBuilder
                .withSourceFilter(new FetchSourceFilter(new String[]{""}, null)) // 不查询任何结果
                .addAggregation(AggregationBuilders.terms("brands_bucket").field("brand"))
                .build();
        AggregatedPage<Item> search = (AggregatedPage<Item>) itemRepository.search(agg);
        Aggregation brands = search.getAggregation("brands_bucket");//得到bucket名字
        // 强转成string类型的桶
        StringTerms stringTerms = (StringTerms) brands;

        // 便利桶
        List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
        buckets.forEach(new Consumer<StringTerms.Bucket>() {
            @Override
            public void accept(StringTerms.Bucket bucket) {
                System.out.println("bucket.getKey() = " + bucket.getKey());
                System.out.println("bucket.getKeyAsString() = " + bucket.getKeyAsString());
                System.out.println("bucket.getDocCount() = " + bucket.getDocCount());
            }
        });

    }

    /**
     * 聚合嵌套 求平均值
     */
    @Test
    public void test10() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder
                .withSourceFilter(new FetchSourceFilter(new String[]{""}, null))
                .addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price"))
        );

//        queryBuilder.addAggregation(
//                AggregationBuilders.terms("brands").field("brand")
//                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price")) //
//                //在品牌聚合桶内进行嵌套聚合，求平均值
//        );

        AggregatedPage<Item> search = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        StringTerms brandsBucketResult = (StringTerms) search.getAggregation("brands");
        List<StringTerms.Bucket> buckets = brandsBucketResult.getBuckets();
        // 要遍历之后才能进取到 子聚合的
        for (StringTerms.Bucket bucket : buckets) {
            System.out.println("bucket.getKeyAsString() = " + bucket.getKeyAsString());
            System.out.println("bucket.getDocCount() = " + bucket.getDocCount());
            InternalAvg priceAvg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("priceAvg.getValue() = " + priceAvg.getValue());
            System.out.println("priceAvg.getWriteableName() = " + priceAvg.getWriteableName());
        }
    }

    @Test
    public void test11() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder(); // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""},
                null));
// 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price")) //
                //在品牌聚合桶内进行嵌套聚合，求平均值
        );
// 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
// 3、解析
// 3.1、从结果中取出名为brands的那个聚合，
// 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands"); // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
// 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
// 3.4、获取桶中的key，即品牌名称 3.5、获取桶中的文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");
// 3.6.获取子聚合结果:
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价:" + avg.getValue());
        }
    }
}
