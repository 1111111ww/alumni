package com.university.alumni.admin.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.university.alumni.admin.config"})
@PropertySource(value = "classpath:config/database.properties", ignoreResourceNotFound = false)
public class HibernateConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(new String[]{"com.university.alumni.entity"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) throws PropertyVetoException, SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    public DataSource dataSource() throws PropertyVetoException, SQLException {
        String driver = environment.getRequiredProperty("database.connection.driver");
        String url = environment.getRequiredProperty("database.connection.url");
        String user = environment.getRequiredProperty("database.connection.user");
        String password = environment.getRequiredProperty("database.connection.password");
        return getDataSource(driver, url, user, password);
    }
    public DataSource getDataSource(String driver, String url, String user, String password) throws PropertyVetoException, SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(30);
        dataSource.setMaxIdleTime(30);
        dataSource.setAcquireIncrement(3);
//      dataSource.setMaxStatements(1000);
        dataSource.setInitialPoolSize(10);

        dataSource.setAcquireRetryAttempts(30);
        dataSource.setAcquireRetryDelay(1000);
        dataSource.setTestConnectionOnCheckout(false);
        dataSource.setTestConnectionOnCheckin(true);
        dataSource.setBreakAfterAcquireFailure(false);

        dataSource.setPreferredTestQuery("select id from test where id = 1");
        dataSource.setMaxStatements(0);
        dataSource.setIdleConnectionTestPeriod(60);
        dataSource.setCheckoutTimeout(10000);
        dataSource.setAutoCommitOnClose(false);

        return dataSource;
    }
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.autoReconnect", environment.getProperty("database.hibernate.autoReconnect"));
        properties.put("hibernate.connection.release_mode", environment.getProperty("database.hibernate.connection.release_mode"));
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", environment.getProperty("database.hibernate.temp.use_jdbc_metadata_defaults"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("database.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", environment.getProperty("database.hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getProperty("database.hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getProperty("database.hibernate.format_sql"));
        properties.put("hibernate.use_sql_comments", environment.getProperty("database.hibernate.use_sql_comments"));
        properties.put("hibernate.jdbc.batch_size", environment.getProperty("database.hibernate.jdbc.batch_size"));
        properties.put("hibernate.connection.isolation", environment.getProperty("database.hibernate.connection.isolation"));
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }
}


