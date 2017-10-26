package pe.limatambo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 *
 * @author dev-out-04
 */
@Configuration
@EnableTransactionManagement
//@PropertySource(value = {"file:/opt/aplicaciones/limatambo/limatambo.properties"})
public class DatabaseConfig {
    
    @Autowired
    private Environment environment;
    @Autowired
    DataSource dataSource;
    private final Logger loggerConfig = LoggerFactory.getLogger(getClass());
    
    @Bean
    public LocalSessionFactoryBean sessionFactory() throws PropertyVetoException{
        try {
            LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            sessionFactory.setPackagesToScan(environment.getRequiredProperty("entitymanager.packagesToScan"));//(new String[]{"pe.cajapaita.backerp.contabilidad.entidad"});
//            sessionFactory.setHibernateProperties(hibernateProperties());
            return sessionFactory;
        } catch (IllegalStateException e) {
            loggerConfig.error(e.getMessage());
            throw e;
        }
    }
    
//    @Bean
//    public DataSource dataSource() throws PropertyVetoException{          
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setDriverClass(environment.getRequiredProperty("db.driver"));
//        dataSource.setJdbcUrl(environment.getRequiredProperty("db.url"));
//        dataSource.setUser(environment.getRequiredProperty("db.username"));
//        dataSource.setPassword(environment.getRequiredProperty("db.password"));
//        dataSource.setAcquireIncrement(Integer.parseInt(environment.getRequiredProperty("dataSource.acquireIncrementcquireIncrement")));
//        dataSource.setMinPoolSize(Integer.parseInt(environment.getRequiredProperty("dataSource.minPoolSize")));
//        dataSource.setMaxPoolSize(Integer.parseInt(environment.getRequiredProperty("dataSource.maxPoolSize")));
//        dataSource.setMaxIdleTime(Integer.parseInt(environment.getRequiredProperty("dataSource.maxIdleTime")));
//        dataSource.setCheckoutTimeout(Integer.parseInt(environment.getRequiredProperty("dataSource.checkoutTimeout")));
//        dataSource.setNumHelperThreads(Integer.parseInt(environment.getRequiredProperty("dataSource.mumHelperThreads")));
//        dataSource.setMaxStatements(Integer.parseInt(environment.getRequiredProperty("dataSource.maxStatements")));
//        dataSource.setUnreturnedConnectionTimeout(Integer.parseInt(environment.getRequiredProperty("dataSource.unreturnedConnectionTimeout")));
//        return dataSource;
//    }
//    
//    private Properties hibernateProperties() {
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
//        return properties;
//    }
//    
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    } 

}
