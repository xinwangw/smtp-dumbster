package local.smtp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.dumbster.smtp.SimpleSmtpServer;

@SpringBootApplication
@EnableScheduling
public class App extends SpringBootServletInitializer{
	private static final Logger log = LoggerFactory.getLogger(App.class);

	@Value("${smtp.port}")
	private int smtpPort;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public SimpleSmtpServer getSimpleSmtpServer(){

		SimpleSmtpServer server = SimpleSmtpServer.start(smtpPort);
		log.info("SMTP Server starts on port: "+smtpPort);
		return server;
	}
	
	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory() {
	    return new HibernateJpaSessionFactoryBean();
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }
}
