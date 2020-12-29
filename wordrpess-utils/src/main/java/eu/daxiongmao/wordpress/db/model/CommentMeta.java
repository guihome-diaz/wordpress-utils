package eu.daxiongmao.wordpress.db.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Wordpress "commentmeta".
 * These are the comments' metadata
 * @version 1.0
 * @since 2020/12
 */
@Data
@Entity
@Table(name = "commentmeta",
        uniqueConstraints = {
                @UniqueConstraint(name = "commentmeta_uq_ID", columnNames = "meta_id")
        },
        indexes = {
                @Index(name = "commentmeta_idx_id", columnList = "meta_id", unique = true),
                @Index(name = "commentmeta_idx_comment_id", columnList = "comment_id asc"),
                @Index(name = "commentmeta_idx_meta_key", columnList = "meta_key asc")
        }
)
public class CommentMeta implements Serializable {

    /** technical identifier */
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meta_id", updatable = false, nullable = false)
    private Integer id;

    /** Related comment */
    // FIXME adjust relationship @ManyToOne (Unidirectional association)
    @NonNull
    @Column(name = "comment_id", nullable = false)
    private Integer commentId;

    /** Metadata key */
    @NonNull
    @Column(name = "meta_key", nullable = false, length = 255)
    private String key;

    /** Metadata value */
    @NonNull
    @Column(name = "meta_value", nullable = false, length = 65535)
    private String value;

}
