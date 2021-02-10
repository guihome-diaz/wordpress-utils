package eu.daxiongmao.wordpress;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class WordrpessUtilsApplicationIT {

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Test
	void contextLoads() {
		log.info("Context has been successfully loaded.");
		if (StringUtils.isNotBlank(this.activeProfile) && this.activeProfile.contains("h2")) {
			log.info("You can access the H2 database: http://localhost:8080/h2-console");
		}
	}

}
