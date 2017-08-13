package com.Configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.model.Blog;
import com.model.Comment;
import com.model.Friend;
import com.model.Job;
import com.model.ProfilePicture;
import com.model.User;

//same as hibernate.cohfiguration.xml file

@Configuration
@EnableTransactionManagement
public class BDBConfig {
	@Bean
	public SessionFactory sessionFactory()
	{
		
		LocalSessionFactoryBuilder lsfb = new LocalSessionFactoryBuilder(getDataSource());
		Properties hibernateproperties = new Properties();
		hibernateproperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		hibernateproperties.setProperty("hibernate.hbm2ddl.auto", "update");
		lsfb.addProperties(hibernateproperties);
		Class classes[] =new Class[]{User.class,Job.class,Friend.class,Blog.class,Comment.class,ProfilePicture.class};
		return lsfb.addAnnotatedClasses(classes).buildSessionFactory();
		
		
	}
  
	
 @Bean
	public DataSource getDataSource() {
		BasicDataSource datasource=new BasicDataSource();
		datasource.setDriverClassName("oracle.jdbc.OracleDriver");
		datasource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
		datasource.setUsername("NIKITA");
		datasource.setPassword("nikita");
	    return datasource;
	}
    
    @Bean
    public HibernateTransactionManager transactionmanager(){
		
    	
    	return  new HibernateTransactionManager(sessionFactory());
    	
    }

}
