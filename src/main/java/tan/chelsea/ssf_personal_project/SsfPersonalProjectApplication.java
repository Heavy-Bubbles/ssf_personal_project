package tan.chelsea.ssf_personal_project;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SsfPersonalProjectApplication {

	// setting up logger
	private static final Logger logger = LoggerFactory.getLogger(SsfPersonalProjectApplication.class);

	// set default port number
	private static final String DEFAULT_PORT = "3000";
	public static void main(String[] args) {

		// logger message
		logger.info("main method started......");

		// initialize the spring app
		SpringApplication app = new SpringApplication(SsfPersonalProjectApplication.class);
		
		// read args array and check for "port" parameter
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
		// return the collection of values associated with the arguments option having the given name
		List opsValues = appArgs.getOptionValues("port");
		// Initialize port number variable
		String portNumber = null;

		// if port number is not in argument
		if (opsValues == null || opsValues.get(0) == null){

			// read port number from environment variables (when running spring-boot)
			// mvn spring-boot:run "-Dspring-boot.run.arguments=--port=<number>"
			portNumber = System.getenv("PORT");

			// if never set port in terminal, set port to default 
			if (portNumber == null){
				portNumber = DEFAULT_PORT;
			}
		} else {
			// passing port number from command line terminal
			// powershell - $env:PORT=8082
			// command prompt - set PORT=8082
			portNumber = (String) opsValues.get(0);
		}

		if (portNumber != null){
			// setting port number in the spring-boot configuration
			// The singletonMap() method of java.util.Collections class is used to return an immutable map, 
			// mapping only the specified key to the specified value. 
			// The returned map is serializable.
			app.setDefaultProperties(Collections.singletonMap("server.port", portNumber));
		}

		// logger message stating which port
		logger.info("Port number is : " + portNumber);

		//launch spring boot app
		app.run(args);
		
		
		
	}

}
