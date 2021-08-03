package eu.daxiongmao.wordpress.db.dao;

import eu.daxiongmao.wordpress.db.model.WpUser;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WpUserRepository implements PanacheRepository<WpUser> {

}
