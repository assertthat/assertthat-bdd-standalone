package com.assertthat.plugins.standalone;

public class ArgumentsFeatures extends Arguments{

    public ArgumentsFeatures(String accessKey,
                     String secretKey,
                     String projectId,
                     String outputFolder,
                     String proxyURI,
                     String proxyUsername,
                     String proxyPassword,
                     String mode,
                     String jql,
                     String tags,
                     String jiraServerUrl,
                     boolean numbered,
                     boolean ignoreCertErrors) {
        super(accessKey,
                secretKey,
                projectId,
                null,
                outputFolder,
                null,
                null,
                proxyURI,
                proxyUsername,
                proxyPassword,
                mode,
                jql,
                tags,
                null,
                jiraServerUrl,
                null,
                numbered,
                ignoreCertErrors);
    }
}
