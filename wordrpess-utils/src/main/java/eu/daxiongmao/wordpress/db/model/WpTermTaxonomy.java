package eu.daxiongmao.wordpress.db.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>Wordpress core table: TERM_TAXONOMY.<br>
 * This table describes the taxonomy (category, link, or tag) for the entries in the wp_terms table.</p>
 * <p>Once a term has been added in <code>terms</code>,
 * it is also added to the <code>term_taxonomy</code> table which defines the taxonomy the term belongs to.</p>
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on Wordpress documentation and installation, see https://codex.wordpress.org/Database_Description)
 */
@Data
@Entity
@Table(name = "term_taxonomy",
        indexes = {
                @Index(name = "term_taxonomy_idx_id", columnList = "term_taxonomy_id", unique = true),
                @Index(name = "term_taxonomy_idx_taxonomy", columnList = "taxonomy asc"),
                @Index(name = "term_taxonomy_idx_term_taxnmy", columnList = "term_id, taxonomy asc")
        }
)
public class WpTermTaxonomy implements Serializable {

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_taxonomy_id", updatable = false, nullable = false)
    private Integer id;

    /** Related term */
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id", nullable = false)
    private WpTerms term;

    /** The slug of the taxonomy.
     * Many values are possible: category, post_tag, ngg_tag, pdf_lv_tag */
    @NonNull
    @Column(name = "taxonomy", nullable = false, length = 32)
    private String taxonomy;

    @Column(name = "description", nullable = true, length = 65535)
    private String description;

    /** Optional. Id of the parent's taxonomy. Put '0' if it does not apply. */
    @Column(name = "parent")
    private int parentId = 0;

    /** Number of occurrences. */
    @Column(name = "count")
    private int count = 0;

    /**
     * Method called before every persistence operation to sanitize data.
     * Better save some data as lowercase: it will speed up the indexes searches if the case is always the same.
     */
    @PrePersist @PreUpdate
    public void prepareData(){
        this.taxonomy = this.taxonomy.trim().toLowerCase();
    }
}
