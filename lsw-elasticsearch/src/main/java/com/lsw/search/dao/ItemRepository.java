package com.lsw.search.dao;

import com.lsw.search.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

    List<Item> findByPriceBetween(double price1, double price2);

    List<Item> findByCategoryAndBrand(String category, String brand);


}
