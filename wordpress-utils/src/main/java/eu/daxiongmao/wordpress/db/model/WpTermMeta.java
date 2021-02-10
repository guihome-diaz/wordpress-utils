package eu.daxiongmao.wordpress.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Wordpress core table: TERMMETA.
 * Each term features information called the meta data and it is stored in wp_termmeta.
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on Wordpress documentation and installation, see https://codex.wordpress.org/Database_Description)
 */
@Data
@Entity
@Table(name = "termmeta",
        uniqueConstraints = {
                @UniqueConstraint(name = "termmeta_uq_id", columnNames = "meta_id")
        },
        indexes = {
                @Index(name = "termmeta_idx_id", columnList = "meta_id", unique = true),
                @Index(name = "termmeta_idx_term_id", columnList = "term_id asc"),
                @Index(name = "termmeta_idx_meta_key", columnList = "meta_key asc")
        }
)
@NoArgsConstructor
public class WpTermMeta implements Serializable {

    /** technical identifier */
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meta_id", updatable = false, nullable = false)
    private Long id;

    /** Related term */
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id", nullable = false, foreignKey = @ForeignKey(name = "termmeta_fk_term"))
    private WpTerms term;

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
