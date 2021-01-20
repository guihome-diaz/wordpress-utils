package eu.daxiongmao.wordpress.db.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Wordpress core table: USERMETA
 * These are the users' metadata
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on Wordpress documentation and installation, see https://codex.wordpress.org/Database_Description)
 */
@Data
@Entity
@Table(name = "usermeta",
        indexes = {
                @Index(name = "usermeta_idx_id", columnList = "umeta_id", unique = true),
                @Index(name = "usermeta_idx_user_id", columnList = "user_id asc"),
                @Index(name = "usermeta_idx_meta_key", columnList = "meta_key asc")
        }
)
public class WpUserMeta implements Serializable {

    /** technical identifier */
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "umeta_id", updatable = false, nullable = false)
    private Long id;

    /** Related user */
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey (name = "usermeta_fk_users"))
    private WpUser user;

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
