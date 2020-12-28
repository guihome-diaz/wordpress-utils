package eu.daxiongmao.wordpress.db.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Wordpress "commentmeta".
 * This is the link between comments and related metadata
 * @version 1.0
 * @since 2020/12
 */
@Data
@Entity
@Table(name = "commentmeta")
public class CommentMeta implements Serializable {

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meta_id", updatable = false, nullable = false)
    private Integer id;

}
