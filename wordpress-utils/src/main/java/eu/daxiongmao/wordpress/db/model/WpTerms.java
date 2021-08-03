package eu.daxiongmao.wordpress.db.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>Wordpress core table: TERMS.<br>
 * The categories for both posts and links and the tags for posts are found within the wp_terms table.</p>
 * <p>When a term is created it is first added to the <code>terms</code> then it will be inserted in <code>term_taxonomy</code></p>
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on Wordpress documentation and installation, see https://codex.wordpress.org/Database_Description)
 */
@Data
@Entity
@Table(name = "terms",
        uniqueConstraints = {
                @UniqueConstraint(name = "terms_uq_id", columnNames = "term_id")
        },
        indexes = {
                @Index(name = "terms_idx_id", columnList = "term_id", unique = true),
                @Index(name = "terms_idx_name", columnList = "name asc"),
                @Index(name = "terms_idx_slug", columnList = "slug asc")
        }
)
@NoArgsConstructor
public class WpTerms extends PanacheEntityBase implements Serializable {

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id", updatable = false, nullable = false)
    private Long id;

    /** The readable name of the term */
    @NonNull
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    /** A term slug used in URLs */
    @NonNull
    @Column(name = "slug", nullable = false, length = 200)
    private String slug;

    /** A mechanism for creating aliases, this isn’t really used as far as I know.
     * Put '0' as default */
    @Column(name = "term_group")
    private long group = 0;

    /**
     * Method called before every persistence operation to sanitize data.
     * Better save some data as lowercase: it will speed up the indexes searches if the case is always the same.
     */
    @PrePersist @PreUpdate
    public void prepareData(){
        this.slug = this.slug.trim().toLowerCase();
    }
}
