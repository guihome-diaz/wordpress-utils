package eu.daxiongmao.wordrpess.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class ItemDTO {

    /** Type of item: blog post, attachment, notification, etc. */
    private ItemType itemType;

    /** Name of the item. this is unique, like the slug, and it cannot contains spaces. */
    private String name;

    /** Item title. this is what appears on the website, it can contains spaces. */
    private String title;

    /** Publication date */
    private LocalDateTime publicationDate;

    /** Author / Creator */
    private String author;

    /** Description of the item (optional) */
    private String description;

    /** Content as a string. It can be HTML or plain text. It usually include wordpress data */
    private String content;

    /** Short part of the article to display to tease the blog post. */
    private String excerpt;

    /** Optional: categories related to that item. */
    private final Set<String> categories = new HashSet<>();

    /** Optional: tags related to that item. */
    private final Set<String> tags = new HashSet<>();

}
