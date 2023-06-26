package eu.daxiongmao.wordpress.db.services;

import eu.daxiongmao.wordpress.db.dao.WpUserDao;
import eu.daxiongmao.wordpress.db.dao.WpUserRepository;
import eu.daxiongmao.wordpress.db.model.WpUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = { Exception.class })
public class WpUserTransactionalService {

    private final WpUserDao wpUserDao;
    private final WpUserRepository wpUserRepository;

    @Inject
    public WpUserTransactionalService(WpUserDao wpUserDao, WpUserRepository wpUserRepository) {
        this.wpUserDao = wpUserDao;
        this.wpUserRepository = wpUserRepository;
    }

    public List<WpUser> getAuthors() {
        return wpUserDao.findAuthors();
    }
}
