package eu.daxiongmao.wordpress.db.model;

public enum WpCapability {

    //region Themes
    /**
     * Allows access to Administration Screens options:
     * Appearance
     * Appearance > Themes
     */
    switch_themes,

    /** Allows access to Appearance > Theme Editor to edit theme files. */
    edit_themes,

    /**
     * Allows access to Administration Screens options:
     * Appearance > Widgets
     * Appearance > Menus
     * Appearance > Customize if they are supported by the current theme
     * Appearance > Header
     */
    edit_theme_options,

    /**
     * Allows access to Administration Screens options:
     * Appearance > Add New Themes
     */
    install_themes,
    update_themes,
    delete_themes,

    /**
     * Allows access to the Customizer.
     */
    customize,

    /**
     * Multi-site only
     * Allows user to upload theme ZIP files from the Network Themes -> Add New menu
     */
    upload_themes,
    //endregion

    //region Plugins
    /**
     * Allows access to Administration Screens options:
     * Plugins
     */
    activate_plugins,

    /**
     * Allows access to Administration Screens options:
     * Plugins > Plugin Editor
     */
    edit_plugins,

    /**
     * Allows access to Administration Screens options:
     * Plugins > Add New
     */
    install_plugins,
    update_plugins,
    delete_plugins,

    /**
     * Multi-site only
     * Allows user to upload plugin ZIP files from the Network Plugins -> Add New menu
     */
    upload_plugins,
    //endregion

    //region Users
    /** Allows access to Administration Screens options:
     * Users
     */
    edit_users,

    delete_users,
    /**
     * Allows creating new users.
     *     Without other capabilities, created users will have your blog’s New User Default Role.
     */
    create_users,

    /**
     * Allows access to Administration Screens options:
     *     Users
     */
    list_users,

    remove_users,

    /** Replaced in 4.4 with promote_users */
    add_users,

    /**
     * Enables the “Change role to…” dropdown in the admin user list.
     * This does not depend on ‘edit_users‘ capability.
     * Enables the ‘Add Existing User’ to function for multi-site installs.
     */
    promote_users,
    //endregion

    //region Posts
    /**
     * Allows access to Administration Screens options:
     *     Posts
     *     Posts > Add New
     *     Comments
     *     Comments > Awaiting Moderation
     */
    edit_posts,

    /**
     * Allows access to Administration Screens options:
     *     Manage > Comments (Lets user delete and edit every comment, see edit_posts above)
     *     user can edit other users’ posts through function get_others_drafts()
     *     user can see other users’ images in inline-uploading [no? see inline-uploading.php]
     *     See exceptions
     */
    edit_others_posts,

    /**
     * User can edit their published posts. This capability is off by default.
     * The core checks the capability edit_posts, but on demand this check is changed to edit_published_posts.
     * If you don’t want a user to be able to edit their published posts, remove this capability.
     */
    edit_published_posts,

    /**
     * See and use the “publish” button when editing their post (otherwise they can only save drafts)
     *     Can use XML-RPC to publish (otherwise they get a “Sorry, you can not post on this weblog or category.”)
     */
    publish_posts,

    delete_posts,
    delete_others_posts,
    delete_published_posts,
    delete_private_posts,
    edit_private_posts,
    read_private_posts,
    //endregion

    //region Comments
    /**
     * Allows users to moderate comments from the Comments Screen
     * (although a user needs the edit_posts Capability in order to access this)
     */
    moderate_comments,
    edit_comment,
    //endregion

    //region Pages
    /**
     * Allows access to Administration Screens options:
     *     Pages
     *     Pages > Add New
     */
    edit_pages,
    publish_pages,
    edit_others_pages,
    edit_published_pages,
    delete_pages,
    delete_others_pages,
    delete_published_pages,
    delete_private_pages,
    edit_private_pages,
    read_private_pages,
    //endregion

    //region Content
    /**
     * Allows access to Administration Screens options:
     * Posts > Categories
     * Links > Categories
     */
    manage_categories,

    /**
     * Allows access to Administration Screens options:
     *     Links
     *     Links > Add New
     */
    manage_links,

    /**
     * Allows access to Administration Screens options:
     *     Media
     *     Media > Add New
     */
    upload_files,
    unfiltered_upload,

    /**
     * Allows user to post HTML markup or even JavaScript code in pages, posts, comments and widgets.
     *     Note: Enabling this option for untrusted users may result in their posting malicious or poorly formatted code.
     *     Note: In WordPress Multisite, only Super Admins have the unfiltered_html capability.
     */
    unfiltered_html,
    //endregion

    //region Site
    /**
     * Allows access to Administration Screens options:
     *     Settings > General
     *     Settings > Writing
     *     Settings > Reading
     *     Settings > Discussion
     *     Settings > Permalinks
     *     Settings > Miscellaneous
     */
    manage_options,

    /**
     * Allows access to Administration Screens options:
     *     Tools > Import
     *     Tools > Export
     */
    import_backup_file,

    export_backup_file,

    /**
     * Allows access to Administration Screens options:
     * Dashboard
     * Users > Your Profile
     * Used nowhere in the core code except the menu.php
     */
    read,

    edit_dashboard,

    /**
     * Allows the user to delete the current site (Multisite only).
     */
    delete_site,

    update_core,

    /**
     * Multi-site only
     * Allows user to create sites on the network
     */
    create_sites,

    /**
     * Multi-site only
     *     Allows user to delete sites on the network
     */
    delete_sites,

    /**
     * Multi-site only
     *     Allows access to Network Sites menu
     *     Allows user to add, edit, delete, archive, unarchive, activate, deactivate, spam and unspam new site/blog in the network
     */
    manage_sites,
    //endregion

    //region Network
    /**
     * Multi-site only
     * Allows access to Super Admin menu
     * Allows user to upgrade network
     */
    manage_network,

    /**
     * Multi-site only
     *     Allows access to Network Users menu
     */
    manage_network_users,

    /**
     * Multi-site only
     *     Allows access to Network Themes menu
     */
    manage_network_themes,

    /**
     * Multi-site only
     *     Allows access to Network Options menu
     */
    manage_network_options,

    /**
     * Multi-site only
     *     Allows access to Network Plugins menu
     */
    manage_network_plugins,

    /**
     * Multi-site only
     *     is used to determine whether a user can access the Network Upgrade page in the network admin.
     *     Related to this, the capability is also checked to determine whether to show the notice that a network upgrade is required.
     *     The capability is not mapped, so it is only granted to network administrators.
     *     See #39205 for background discussion.
     */
    upgrade_network,

    /**
     *  Multi-site only
     *       is used to determine whether a user can setup multisite, i.e. access the Network Setup page.
     *       Before setting up a multisite, the capability is mapped to the `manage_options` capability, so that it is granted to administrators.
     *       Once multisite is setup, it is mapped to `manage_network_options`, so that it is granted to network administrators.
     *       See #39206 for background discussion.
     */
    setup_network
    //endregion
}
