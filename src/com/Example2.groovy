package com

import jenkins.model.Jenkins

class Example2 {

    String getString() {
        return "example2"
    }


    public void credentials() {
        def creds = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
                com.cloudbees.plugins.credentials.common.StandardUsernameCredentials.class,
                Jenkins.instance,
                null,
                null
        );
        for (c in creds) {
            println(c.id + ": " + c.description)
        }
    }
}
