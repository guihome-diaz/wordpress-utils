package eu.daxiongmao.wordpress.db.dao;

import eu.daxiongmao.wordpress.db.model.WpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class WpUserDaoImpl implements WpUserDao {

    private final EntityManager em;

    @Autowired
    public WpUserDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<WpUser> findByEmail(final String searchEmail) {
        if (StringUtils.isBlank(searchEmail)) {
            return Optional.empty();
        }

        final TypedQuery<WpUser> query = em.createQuery("SELECT u FROM WpUser u WHERE u.email = ?1", WpUser.class);
        query.setParameter(1, searchEmail.toLowerCase().trim());
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<WpUser> findAuthors() {
        final TypedQuery<WpUser> query = em.createQuery("SELECT DISTINCT post.author FROM WpPost post", WpUser.class);
        final List<WpUser> authors = query.getResultList();
        if (authors == null) {
            return new ArrayList<>();
        }
        return authors;
    }

}
