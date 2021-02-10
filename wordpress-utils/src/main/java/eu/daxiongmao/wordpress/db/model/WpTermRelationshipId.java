package eu.daxiongmao.wordpress.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * JPA constraints: to use a primary key on multiple fields we have to declared it in dedicated class.
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz
 */
@Data
@NoArgsConstructor
public class WpTermRelationshipId implements Serializable {

    /** The ID of the object the term is assigned to (ex: post or page) */
    private Long objectId;

    /** The term_taxonomy_id (not the term_id) */
    private Long taxonomyId;

}
