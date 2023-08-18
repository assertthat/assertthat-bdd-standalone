package com.assertthat.plugins.standalone;

public class ArgumentsReport extends Arguments{

    public ArgumentsReport(String accessKey,
                     String secretKey,
                     String projectId,
                     String runName,
                     String jsonReportFolder,
                     String jsonReportIncludePattern,
                     String proxyURI,
                     String proxyUsername,
                     String proxyPassword,
                     String jql,
                     String type,
                     String jiraServerUrl,
                     String metadata,
                     boolean ignoreCertErrors) {

        super(accessKey,
                secretKey,
                projectId,
                runName,
                null,
                jsonReportFolder,
                jsonReportIncludePattern,
                proxyURI,
                proxyUsername,
                proxyPassword,
                null,
                jql,
                null,
                type,
                jiraServerUrl,
                metadata,
                false,
                ignoreCertErrors,
                false);

    }
}
