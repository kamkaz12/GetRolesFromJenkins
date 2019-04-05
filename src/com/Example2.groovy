package com

import com.michelin.cio.hudson.plugins.rolestrategy.*
import hudson.security.Permission
import jenkins.model.Jenkins

class Example2 {

    private RoleBasedAuthorizationStrategy roleBasedAuthorizationStrategy;

    final String GLOBAL_ROLES_TYPE = 'globalRoles';

    String getString(Script script) {
        script.echo("Czesc swiat")
        return "example2"
    }

    void getRoles(String userId, String type = GLOBAL_ROLES_TYPE, Script script) {
        final Set<Role> result = [] as Set

        final RoleMap roleMap = roleBasedAuthorizationStrategy.getRoleMap(type);
        final Set<Role> roles = roleMap.getRoles();

        if (userId) {
            roles.each { role ->
                final Set<String> sids = roleMap.getSidsForRole(role.getName())
                if (sids.contains(userId)) {
                    result.add(role)
                    script.echo(role.toString())
                }
            }
        }

    }



}
