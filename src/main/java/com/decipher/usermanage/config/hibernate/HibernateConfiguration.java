package com.decipher.usermanage.config.hibernate;

import com.decipher.usermanage.util.DataBaseConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

import java.util.Properties;

/**
 * Created by decipher16 on 3/3/17.
 */
@Configuration
@ComponentScan("com.decipher.usermanage.util")
public class HibernateConfiguration {

    /****
     * session factory table scanner method
     * @return
     */
    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder builder =
                new LocalSessionFactoryBuilder(dataSource());
        builder.scanPackages("com.decipher.usermanage.entities")
                .addProperties(getHibernateProperties());
        return builder.buildSessionFactory();
    }

    /****
     * database properties initialization method
     * @return
     */

    public BasicDataSource dataSource() {
        BasicDataSource dataSource=new BasicDataSource();
        dataSource.setUrl(DataBaseConfig.URL);
        dataSource.setDriverClassName(DataBaseConfig.DRIVER_CLASS_NAME);
        dataSource.setUsername(DataBaseConfig.DB_USERNAME);
        dataSource.setPassword(DataBaseConfig.DB_PASSWORD);
        return dataSource;
    }

    /****
     * hibernate properties initialization method
     * @return
     */

    public Properties getHibernateProperties(){
        Properties properties=new Properties();
        properties.put("hibernate.dialect", DataBaseConfig.HIBERNATE_DIALECT);
        properties.put("hibernate.show_sql", DataBaseConfig.HIBERNATE_SHOW_SQL);
        properties.put("hibernate.hbm2ddl.auto", DataBaseConfig.HIBERNATE_HBM_2_DDL_AUTO);
        properties.put("hibernate.format_sql", DataBaseConfig.HIBERNATE_FORMAT_SQL);
        return properties;
    }

    /***
     * create transaction for database operation exection
     * @return
     */

    public HibernateTransactionManager hibernateTransaction(){
        return new HibernateTransactionManager(sessionFactory());
    }

}
