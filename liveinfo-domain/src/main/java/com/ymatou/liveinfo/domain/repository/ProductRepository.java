package com.ymatou.liveinfo.domain.repository;

import com.mongodb.MongoClient;
import com.ymatou.liveinfo.domain.model.Product;
import com.ymatou.liveinfo.infrastructure.mongodb.MongoRepository;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.ArraySlice;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gejianhua on 2017/4/10.
 */
@Component
public class ProductRepository extends MongoRepository {
    @Resource(name = "productMongoClient")
    private MongoClient mongoClient;

    private static final String dbName = "YmtProducts";

    /**
     * 获取到MongoClient
     *
     * @return
     */
    @Override
    protected MongoClient getMongoClient() {
        return mongoClient;
    }

    /**
     * 插入
     * @param product
     */
    public void insert(Product product){
        this.insertEntiy(this.dbName, product);
    }

    /**
     * 根据商品Id获取商品信息
     * @param productIds
     * @return
     */
    public List<Product> getProducts(List<String> productIds){

        if(productIds == null || productIds.size() == 0){
            return new ArrayList<>();
        }

        Datastore datastore = this.getDatastore(this.dbName);
        return datastore.find(Product.class).disableValidation()
                .field("spid").in(productIds)
                .project("spid", true)
                .project("ispsp", true)
                .project("pics", new ArraySlice(1))
                .project("minp", true)
                .project("_id", false)
                .asList();
    }


}















































