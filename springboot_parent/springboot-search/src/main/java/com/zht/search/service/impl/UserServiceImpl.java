package com.zht.search.service.impl;

import com.zht.search.dao.UserMapper;
import com.zht.search.api.pojo.UserInfo;
import com.zht.search.service.UserService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void importData() {
        List<UserInfo> userInfoList = new ArrayList<>();
        userInfoList.add(UserInfo.builder().id(10).name("小明").city("成都").build());
        //保存数据
        userMapper.saveAll(userInfoList);
    }

    @Override
    public List<UserInfo> listUserInfo() {
        Iterable<UserInfo> userInfoListAll = userMapper.findAll();
        List<UserInfo> userInfoList = new ArrayList<>();
        userInfoListAll.forEach(userInfoList::add);
        return userInfoList;
    }

    @Override
    public UserInfo getUserInfoById(long id) {
        Optional<UserInfo> userInfoOpt = userMapper.findById(id);
        return userInfoOpt.orElse(null);
    }

    @Override
    public Map<String, Object> searchUserMap(Map<String, String> conditions) {
        //创建搜索构建对象
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();

        //构建条件参数
        if (conditions != null && conditions.size() > 0) {
            String name = conditions.get("name");
            if (!StringUtils.isEmpty(name)){
                //查询名字包含某个字
                searchQueryBuilder.withQuery(QueryBuilders.queryStringQuery(name).field("name"));
            }
        }
        //获取分页的数据
        AggregatedPage<UserInfo> page = elasticsearchTemplate.queryForPage(searchQueryBuilder.build(), UserInfo.class);

        //构建聚合数据分组的，按照城市进行分组，terms定义分组的名称
        searchQueryBuilder.addAggregation(AggregationBuilders.terms("aggregationCity").field("city"));
        //对分组进行查询
        AggregatedPage<UserInfo> aggregationCity = elasticsearchTemplate.queryForPage(searchQueryBuilder.build(), UserInfo.class);
        //根据分组的名称进行取值
        StringTerms stringTerms = aggregationCity.getAggregations().get("aggregationCity");
        List<String> listCityType = new ArrayList<>();
        //循环保存到城市的类型当中
        stringTerms.getBuckets().forEach(bucket -> {
            listCityType.add(bucket.getKeyAsString());
        });

        //取得分页数据
        List<UserInfo> content = page.getContent();
        //总的个数
        long totalElements = page.getTotalElements();
        //总的页数
        int totalPages = page.getTotalPages();
        Map<String, Object> map = new HashMap<>();

        map.put("totalPages", totalPages);
        map.put("totalSize", totalElements);
        map.put("rows", content);
        map.put("listCityType", listCityType);
        return map;
    }


}
