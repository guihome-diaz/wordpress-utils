package eu.daxiongmao.wordpress.db.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;


/**
 * <p>Wordpress core table: TERM_RELATIONSHIPS.<br>
 * Posts are associated with categories and tags from the wp_terms table and this association is maintained in the wp_term_relationships table.
 * The association of links to their respective categories are also kept in this table.</p>
 * <p>Finally, once a term is assigned to an object like a post, the <code>wp_term_relationships</code> table is used to record it.</p>
 * <p>technically: this is a join table</p>
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on Wordpress documentation and installation, see https://codex.wordpress.org/Database_Description)
 */
@Data
@Entity
@IdClass(WpTermRelationshipId.class)
@Table(name = "term_relationships",
        indexes = {
                @Index(name = "term_relationships_idx_id", columnList = "object_id, term_taxonomy_id", unique = true),
                @Index(name = "term_relationships_idx_taxnmy", columnList = "term_taxonomy_id asc")
        }
)
public class WpTermRelationship implements Serializable {

    /** The ID of the object the term is assigned to (ex: post or page) */
    @Id
    @NonNull
    @Column(name = "object_id", nullable = false)
    private Long objectId;

    /** The term_taxonomy_id (not the term_id) */
    @Id
    @NonNull
    @Column(name = "term_taxonomy_id", nullable = false)
    private Long taxonomyId;

    /** The order in which the terms are assigned.
     * I haven’t seen this used heavily, it can be utilized to make sure terms appear in a given order.
     * Let '0' to keep default order. */
    @Column(name = "term_order")
    private int order = 0;
}
