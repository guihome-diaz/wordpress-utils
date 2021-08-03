package eu.daxiongmao.wordpress.db.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Wordpress core table:  COMMENTMETA
 * Each comment features information called the meta data and it is stored in the wp_commentmeta.
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on Wordpress documentation and installation, see https://codex.wordpress.org/Database_Description)
 */
@Data
@Entity
@Table(name = "commentmeta",
        uniqueConstraints = {
                @UniqueConstraint(name = "commentmeta_uq_id", columnNames = "meta_id")
        },
        indexes = {
                @Index(name = "commentmeta_idx_id", columnList = "meta_id", unique = true),
                @Index(name = "commentmeta_idx_comment_id", columnList = "comment_id asc"),
                @Index(name = "commentmeta_idx_meta_key", columnList = "meta_key asc")
        }
)
@NoArgsConstructor
public class WpCommentMeta extends PanacheEntityBase implements Serializable {

    /** technical identifier */
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meta_id", updatable = false, nullable = false)
    private Long id;

    /** Related comment */
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false, foreignKey = @ForeignKey(name = "commentmeta_fk_meta"))
    private WpComment comment;

    /** Metadata key */
    @NonNull
    @Column(name = "meta_key", nullable = false, length = 255)
    private String key;

    /** Metadata value */
    @NonNull
    @Lob @Basic(fetch = FetchType.LAZY)
    //@Column(name = "meta_value", nullable = false, length = 65535)
    @Column(name = "meta_value", nullable = false)
    private String value;

}
