package eu.daxiongmao.wordpress.db.model.dtos;

import eu.daxiongmao.wordpress.db.model.enums.WpCapability;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * WordPress role, based on https://core.trac.wordpress.org/browser/trunk/src/wp-includes/class-wp-role.php
 * @author WordPress team
 * @author add hard coded default profiles
 * @version 1.0
 * @since wp4.4.0
 */
@Data
public class WpRoles implements Serializable {

    /** Subscriber (slug: ‘subscriber’) – somebody who can only manage their profile. */
    public final static WpRole DEFAULT_SUBSCRIBER
            = new WpRole("Subscriber", Set.of(WpCapability.read));

    /** Contributor (slug: ‘contributor’) – somebody who can write and manage their own posts but cannot publish them. */
    public final static WpRole DEFAULT_CONTRIBUTOR
            = new WpRole("Contributor", DEFAULT_SUBSCRIBER.getCapabilities(), Set.of(WpCapability.edit_posts, WpCapability.delete_posts));

    /** Author  (slug: ‘author’)  – somebody who can publish and manage their own posts. */
    public final static WpRole DEFAULT_AUTHOR = new WpRole("Author",
            DEFAULT_CONTRIBUTOR.getCapabilities(),
             Set.of(WpCapability.upload_files, WpCapability.publish_posts, WpCapability.edit_published_posts, WpCapability.delete_published_posts));

    /** Editor (slug: ‘editor’) – somebody who can publish and manage posts including the posts of other users. */
    public final static WpRole DEFAULT_EDITOR = new WpRole("Editor",
            DEFAULT_AUTHOR.getCapabilities(),
            Set.of(WpCapability.unfiltered_html, WpCapability.manage_categories, WpCapability.manage_links,
                   WpCapability.read_private_pages, WpCapability.read_private_posts,
                   WpCapability.edit_others_posts, WpCapability.edit_private_posts,
                   WpCapability.delete_others_posts, WpCapability.delete_private_posts,
                   WpCapability.publish_pages,
                   WpCapability.edit_pages, WpCapability.edit_private_pages, WpCapability.edit_published_pages, WpCapability.edit_others_pages,
                   WpCapability.delete_pages, WpCapability.delete_private_pages, WpCapability.delete_published_posts, WpCapability.delete_others_pages,
                   WpCapability.moderate_comments));

    /** Administrator (slug: ‘administrator’) – somebody who has access to all the administration features within a single site. */
    public final static WpRole DEFAULT_ADMINISTRATOR = new WpRole("Administrator",
            DEFAULT_EDITOR.getCapabilities(),
            Set.of(WpCapability.install_plugins, WpCapability.activate_plugins, WpCapability.edit_plugins, WpCapability.update_plugins, WpCapability.delete_plugins,
                    WpCapability.edit_dashboard,
                    WpCapability.install_themes, WpCapability.edit_themes, WpCapability.edit_theme_options, WpCapability.switch_themes,
                    WpCapability.update_themes, WpCapability.upload_themes, WpCapability.delete_themes, WpCapability.customize,
                    WpCapability.manage_options, WpCapability.import_backup_file, WpCapability.export_backup_file,
                    WpCapability.list_users, WpCapability.promote_users, WpCapability.create_users, WpCapability.add_users,
                    WpCapability.delete_users, WpCapability.edit_users, WpCapability.remove_users,
                    WpCapability.update_core));

    /** Super Admin – somebody with access to the site network administration features and all other features. See the Create a Network article. */
    public final static WpRole DEFAULT_SUPER_ADMIN = new WpRole("Super Admin",
            DEFAULT_ADMINISTRATOR.getCapabilities(),
            Set.of(WpCapability.create_sites, WpCapability.delete_site, WpCapability.manage_sites,
                    WpCapability.manage_network, WpCapability.manage_network_options, WpCapability.manage_network_plugins,
                    WpCapability.manage_network_themes, WpCapability.manage_network_users, WpCapability.setup_network,
                    WpCapability.upgrade_network));



}
