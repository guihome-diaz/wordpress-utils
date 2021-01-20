package eu.daxiongmao.wordpress;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class WordrpessUtilsApplicationTests {

	@Test
	void contextLoads() {
		log.info("Context has been successfully loaded. You can access the H2 database: http://localhost:8080/h2-console");
	}

}
