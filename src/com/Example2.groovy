package com

import com.michelin.cio.hudson.plugins.rolestrategy.*
import hudson.model.Item
import hudson.security.AuthorizationStrategy
import hudson.security.Permission
import jenkins.model.Jenkins

class Example2 {

    private RoleBasedAuthorizationStrategy roleBasedAuthorizationStrategy;
    final String GLOBAL_ROLES_TYPE = 'globalRoles';

    String getString(Script script) {
        script.echo("Czesc swiat")
    }

    Set<Role> getRoles(String userId, String type, Script script) {
        script.echo(userId)
        if (!userId) {
            return null
        }

        final Set<Role> result = [] as Set
        final RoleMap roleMap = roleBasedAuthorizationStrategy.getRoleMap(type)
        final Set<Role> roles = roleMap.getRoles()

        script.echo(userId)
        if (userId) {
            roles.each { role ->
                final Set<String> sids = roleMap.getSidsForRole(role.getName())
                if (sids.contains(userId)) {
                    result.add(role)
                }
            }
        }
        return result
    }



}
