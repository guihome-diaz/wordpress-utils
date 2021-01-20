package eu.daxiongmao.wordpress.db.dao;

import eu.daxiongmao.wordpress.db.model.WpUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WpUserRepository extends CrudRepository<WpUser, Integer> {

}
