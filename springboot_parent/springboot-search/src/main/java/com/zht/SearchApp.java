package com.zht;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableElasticsearchRepositories(basePackages = "com.zht.search.dao")
public class SearchApp {

    public static void main(String[] args) {
        /**
         * Springboot整合Elasticsearch在项日启动前设置一下的属性，防止报错
         * 解决netty冲突后初始化client时还会抛出异常
         * availablcProccssors is alrcady sct to [i2]， rcjecting [12]
         */
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SearchApp.class, args);
    }

}
