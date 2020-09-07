package com.zht.search.dao;

import com.zht.search.api.pojo.UserInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserMapper extends ElasticsearchRepository<UserInfo, Long> {
}
