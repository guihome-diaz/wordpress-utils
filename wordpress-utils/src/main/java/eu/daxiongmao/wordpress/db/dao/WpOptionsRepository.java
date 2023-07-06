package eu.daxiongmao.wordpress.db.dao;

import eu.daxiongmao.wordpress.db.model.WpOption;
import eu.daxiongmao.wordpress.db.model.dtos.WpRole;
import eu.daxiongmao.wordpress.db.model.enums.WpOptionEnum;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;

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
        return WpOption.find("name like ?1", "%" + optionName).firstResult();
    }

}
