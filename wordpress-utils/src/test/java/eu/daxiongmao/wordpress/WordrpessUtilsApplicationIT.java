package eu.daxiongmao.wordpress;

import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@QuarkusTest
@Log4j2
public class WordrpessUtilsApplicationIT {

	@Test
	void contextLoads() {
		log.info("Context has been successfully loaded.");
	}

}
