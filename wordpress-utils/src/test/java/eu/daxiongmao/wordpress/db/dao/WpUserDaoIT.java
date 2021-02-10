package eu.daxiongmao.wordpress.db.dao;


import eu.daxiongmao.wordpress.db.model.WpUser;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class WpUserDaoIT {

    @Autowired
    WpUserDao userDao;

    @Test
    @DisplayName("DB data dependent test: expect 2 authors")
    public void getUsers() {
        final List<WpUser> authors = userDao.findAuthors();
        Assertions.assertNotNull(authors);
        Assertions.assertEquals(2, authors.size());
    }

    @Test
    @DisplayName("DB data dependent test: expect 1 user for this email")
    public void findByEmail() {
        final String searchEmail = "guillaume@qin-diaz.com";
        final Optional<WpUser> dbUser = userDao.findByEmail(searchEmail);
        Assertions.assertTrue(dbUser.isPresent());
        Assertions.assertEquals(searchEmail, dbUser.get().getEmail());
    }

}
