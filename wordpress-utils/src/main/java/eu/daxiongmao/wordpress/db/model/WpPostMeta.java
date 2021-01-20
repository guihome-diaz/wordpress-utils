package eu.daxiongmao.wordpress.db.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Wordpress core table: POSTMETA.
 * Each post features information called the meta data and it is stored in the wp_postmeta.
 * Some plugins may add their own information to this table.
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on Wordpress documentation and installation, see https://codex.wordpress.org/Database_Description)
 */
@Data
@Entity
@Table(name = "postmeta",
        uniqueConstraints = {
                @UniqueConstraint(name = "postmeta_uq_id", columnNames = "meta_id")
        },
        indexes = {
                @Index(name = "postmeta_idx_id", columnList = "meta_id", unique = true),
                @Index(name = "postmeta_idx_post_id", columnList = "post_id asc"),
                @Index(name = "postmeta_idx_meta_key", columnList = "meta_key asc")
        }
)
public class WpPostMeta implements Serializable {

    /** technical identifier */
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meta_id", updatable = false, nullable = false)
    private Long id;

    /** Related post */
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, foreignKey = @ForeignKey(name = "postmeta_fk_post"))
    private WpPost post;

    /** Metadata key */
    @NonNull
    @Column(name = "meta_key", nullable = false, length = 255)
    private String key;

    /** Metadata value */
    @NonNull
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "meta_value", nullable = false)
    //@Column(name = "meta_value", nullable = false, length = 65535)
    private String value;

}
