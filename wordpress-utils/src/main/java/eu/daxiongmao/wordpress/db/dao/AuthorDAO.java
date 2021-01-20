package eu.daxiongmao.wordpress.db.dao;

import eu.daxiongmao.wordpress.wxr.model.Author;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * To manage wordpress authors
 */
public class AuthorDAO {

    /**
     * To retrieve the list of blog's authors.
     * This query will only retrieve users that have published something.<br>
     *     This is based on <a href="https://github.com/wp-cli/export-command.git">WP-CLI source code, WP_Export_Query.php</a>
     * @param tablePrefix (optional) table prefix, ex: wp_
     * @return authors
     */
    public List<Author> getAuthors(final String tablePrefix) {
        String tableName = "posts";
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tablePrefix + tableName;
        }

        final String sqlQuery = "SELECT DISTINCT post_author FROM " + tableName + " WHERE post_status != 'auto-draft'";
        // FIXME get corresponding users
        return new ArrayList<>();
    }

}
