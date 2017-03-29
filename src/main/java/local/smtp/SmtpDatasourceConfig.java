/**
 * Copyright 2017 BPAY Pty Ltd, all rights reserved.
 *
 * This software is the proprietary information of BPAY Pty Ltd.
 * Use is subject to license terms.
 */
package local.smtp;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * This class is responsible for registering RCC data source and JPa repositories for RCC application 
 * 
 * @author agupta
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = 	
						{	
							"local.smtp.repository", 
						}, 
		entityManagerFactoryRef = "entityManagerFactory", 
        transactionManagerRef = "transactionManager")
@EntityScan(basePackages = 	
						{
							"local.smtp.model",
						})
@EnableTransactionManagement
public class SmtpDatasourceConfig {

	protected final Logger mLogger = LoggerFactory.getLogger(getClass());
	private static final String PERSISTENT_UNIT = "RCC";
    /**
     * @return
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        DataSource datasource = DataSourceBuilder.create().build();
        return datasource;
    }

    /**
     * @param aBuilder
     * @return
     */
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final EntityManagerFactoryBuilder aBuilder) {
      LocalContainerEntityManagerFactoryBean entityManagerFactory = aBuilder
    		  	.dataSource(dataSource())
    		  	.persistenceUnit(PERSISTENT_UNIT)
    		  	.build();
      return entityManagerFactory;
    }

    /**
     * @param aFactory
     * @return
     */
    @Bean
    @Primary
    public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory") final EntityManagerFactory aFactory) {
      JpaTransactionManager transactionManager = new JpaTransactionManager(aFactory);
      return transactionManager;
    }
    
}
