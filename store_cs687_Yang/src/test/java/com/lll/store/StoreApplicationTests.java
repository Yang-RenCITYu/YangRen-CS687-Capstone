package com.lll.store;

import com.lll.store.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;

@SpringBootTest
class StoreApplicationTests {
    @Autowired//自动装配
    private DataSource dataSource;

    @Test
    void contextLoads() {

    }

    /**
     * 数据库连接池：
     *
     * HikariProxyConnection@766681183 wrapping com.mysql.cj.jdbc.ConnectionImpl@695c938d
     */
    @Test
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }



}
