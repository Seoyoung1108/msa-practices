package storage;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import storage.httpd.SimpleHttpd;

@SpringBootApplication
public class StorageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageServiceApplication.class, args);
	}

	@Bean
	TomcatServletWebServerFactory servletContainer() {
		return new TomcatServletWebServerFactory() {
			@Override
			protected void customizeConnector(Connector connector) {
				super.customizeConnector(connector);
				connector.setParseBodyMethods("POST,PUT,DELETE");
			}
		};
	}

	@Bean
	TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

	@Bean
	ApplicationRunner httpdRunner(TaskExecutor executor, SimpleHttpd httpd) {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {
				executor.execute(httpd);
			}
		};
	}
}
