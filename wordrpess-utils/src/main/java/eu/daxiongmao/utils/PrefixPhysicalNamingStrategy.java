package eu.daxiongmao.utils;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * To set a table name prefix to all JPA entities.
 * @version 1.0
 * @since 2020/01
 * @author Guillaume Diaz (project version), based on https://stackoverflow.com/questions/4313095/jpa-hibernate-and-custom-table-prefixes/52781557
 */
public class PrefixPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    /**
     * TODO Make this an injectable application property
     */
    public static final String TABLE_NAME_PREFIX = "baby_";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        Identifier newIdentifier = new Identifier(TABLE_NAME_PREFIX + name.getText(), name.isQuoted());
        return super.toPhysicalTableName(newIdentifier, context);
    }
}
