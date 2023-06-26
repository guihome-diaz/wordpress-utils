package eu.daxiongmao.wordpress.db.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serializable;

/**
 * WordPress' configuration.
 * The Options set under the "Administration" > "Settings" panel are stored in the wp_options table.
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on WordPress documentation and installation, see https://codex.wordpress.org/Database_Description)
 */
@Data
@Entity
@Table(name = "options",
        uniqueConstraints = {
                @UniqueConstraint(name = "option_uq_id", columnNames = "option_id"),
                @UniqueConstraint(name = "option_uq_name", columnNames = "option_name")
        },
        indexes = {
                @Index(name = "option_idx_id", columnList = "option_id", unique = true),
                @Index(name = "option_idx_name", columnList = "option_name asc", unique = true),
                @Index(name = "option_idx_autoload", columnList = "autoload asc")
        }
)
@NoArgsConstructor
@ToString
public class WpOption extends PanacheEntityBase implements Serializable {

    public static String BOOLEAN_TRUE = "yes";
    public static String BOOLEAN_FALSE = "no";

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id", updatable = false, nullable = false)
    private Long id;

    @NonNull
    @Column(name = "option_name", nullable = false, length = 191, unique = true)
    private String name;

    /**
     * The option value can be anything.
     * From few words to encoded certificates, JSON or whatever.
     */
    @NonNull
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "option_value", nullable = false)
    // @Column(name = "option_value", nullable = false, length = 65535)
    private String value;

    /** Boolean value in cleartext as "yes" or "no". */
    @Column(name = "autoload", nullable = false, length = 20)
    private String autoload = BOOLEAN_TRUE;

}
