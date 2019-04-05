package com

import com.michelin.cio.hudson.plugins.rolestrategy.*
import hudson.security.AuthorizationStrategy
import jenkins.model.Jenkins

class Example2 {

    private RoleBasedAuthorizationStrategy roleBasedAuthorizationStrategy;
    final String GLOBAL_ROLES_TYPE = 'globalRoles';

    void setVariables() {
        // Set RoleBasedAuthorizationStrategy
        final AuthorizationStrategy authorizationStrategy = Jenkins.get().getAuthorizationStrategy();
        roleBasedAuthorizationStrategy = (RoleBasedAuthorizationStrategy) authorizationStrategy;
    }

    String getString(Script script) {
        script.echo("Czesc swiat")
    }

    LinkedList<String> getRoles(String userId, String type, Script script) {
        if (!userId) {
            return null
        }

        final List<String> result = new LinkedList<>()
        final RoleMap roleMap = roleBasedAuthorizationStrategy.getRoleMap(type)
        final Set<Role> roles = roleMap.getRoles()

        script.echo(userId)
        if (userId) {
            roles.each { role ->
                final Set<String> sids = roleMap.getSidsForRole(role.getName())
                if (sids.contains(userId)) {
                    result.add(role.getName())
                }
            }
        }
        return result
    }


    void testScript(Script script) {
        String userId = "User"
        String type = "globalRoles"

        try {
            RoleBasedAuthorizationStrategy roleBasedAuthorizationStrategy;
            final AuthorizationStrategy authorizationStrategy = Jenkins.get().getAuthorizationStrategy();
            roleBasedAuthorizationStrategy = (RoleBasedAuthorizationStrategy) authorizationStrategy;

            final List<String> result = new LinkedList<>()
            final RoleMap roleMap = roleBasedAuthorizationStrategy.getRoleMap(type)
            final Set<Role> roles = roleMap.getRoles()
            if (userId) {
                roles.each { role ->
                    final Set<String> sids = roleMap.getSidsForRole(role.getName())
                    script.echo("${role.getName()}")
                }
            }
        }
        catch (Exception ex) {
        }
    }

    void getCredentials(Script script){
        def creds = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
                com.cloudbees.plugins.credentials.common.StandardUsernameCredentials.class,
                Jenkins.instance,
                null,
                null);

        for (c in creds) {
            script.echo(c.id + ": " + c.username + "  "  + c.password)
        }
    }
}
