package es.iesjandula.reaktor.issues_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "es.iesjandula.reaktor.issues_server")
public class ReaktorIssuesServerApplication 
{

	public static void main(String[] args) 
	{
		SpringApplication.run(ReaktorIssuesServerApplication.class, args);
	}

}
