package eu.daxiongmao.wordpress.db.dao;

import eu.daxiongmao.wordpress.db.model.WpOption;
import eu.daxiongmao.wordpress.db.model.dtos.WpRole;
import eu.daxiongmao.wordpress.db.model.enums.WpOptionEnum;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class WpOptionsRepository implements PanacheRepository<WpOption> {

    private final EntityManager em;

    @Inject
    public WpOptionsRepository(EntityManager em) {
        this.em = em;
    }

    public List<WpRole> loadRoles() {
        // Get DB value
        final WpOption userRoles = findByName(WpOptionEnum.user_roles.name());
        if (userRoles == null) {
            return new ArrayList<>();
        }
        // FIXME
        return new ArrayList<>();
    }

    public WpOption findByName(final String optionName) {
        if (StringUtils.isBlank(optionName)) {
            return null;
        }
        // use the LIKE operator in case the option is prefixed (ex: baby_user_rights / user_rights)
        final TypedQuery<WpOption> query = em.createQuery("SELECT option FROM WpOption option where option.name like :optionName", WpOption.class);
        query.setParameter("optionName", "%" + optionName);
        return query.getSingleResult();
    }

}
