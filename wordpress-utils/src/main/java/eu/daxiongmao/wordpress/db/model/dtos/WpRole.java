package eu.daxiongmao.wordpress.db.model.dtos;

import eu.daxiongmao.wordpress.db.model.enums.WpCapability;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * WordPress role, based on https://core.trac.wordpress.org/browser/trunk/src/wp-includes/class-wp-role.php
 * @author WordPress team
 * @version 1.0
 * @since wp4.4.0
 */
@Data
public class WpRole implements Serializable {

    /** Role name */
    private String name;

    /** Permissions (grants) for that role */
    private final Set<WpCapability> capabilities = new HashSet<>();


    public WpRole(final String name, final Set<WpCapability> ... capabilities ) {
        this.name = name;
        if (capabilities != null) {
            for (Set<WpCapability> items : capabilities) {
                this.capabilities.addAll(items);
            }
        }
    }

    /**
     * Assign role a capability.
     * @param capability Capability name
     */
    public void addCapability(final WpCapability capability) {
        if (!hasCapability(capability)) {
            capabilities.add(capability);
        }
    }

    /**
     * Removes a capability from a role
     * @param capability Capability name
     */
    public void removeCapability(final WpCapability capability) {
        if (hasCapability(capability)) {
            capabilities.remove(capability);
        }
    }

    /**
     * Determines whether the role has the given capability.
     * @param capability searched capability
     * @return "true" if the current role has this privilege
     */
    public boolean hasCapability(final WpCapability capability) {
        return capabilities.contains(capability);
    }

}
