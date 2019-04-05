package com

import com.michelin.cio.hudson.plugins.rolestrategy.*
import hudson.security.AuthorizationStrategy
import jenkins.model.Jenkins


String userId="User"
String type="globalRoles"

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
            if (sids.contains(userId)) {
                result.add(role.getName())
            }
        }
    }
    return result;
}
catch(Exception ex){

}