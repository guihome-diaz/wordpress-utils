package eu.daxiongmao.wordpress.db.dao;

import eu.daxiongmao.wordpress.db.model.WpOption;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@QuarkusTest
@Log4j2
public class WpOptionsRepositoryIT {

    @Inject
    WpOptionsRepository optionsRepository;


    @Test
    @DisplayName("Retrieve ALL options in real db")
    public void findAll() {
        final List<WpOption> options = optionsRepository.findAll().list();
        Assertions.assertNotNull(options);
        Assertions.assertFalse(options.isEmpty());
        options.forEach(option -> log.debug(option.toString()));
    }

    @Test
    @DisplayName("Retrieve particular name in real db")
    public void findByName() {
        final String optionName = "user_roles";
        final WpOption option = optionsRepository.findByName(optionName);
        Assertions.assertNotNull(option);
        log.debug(option.toString());


    }
}
