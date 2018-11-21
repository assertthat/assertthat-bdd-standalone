package com.assertthat.jira.bdd.standalone;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Arguments {
    private String accessKey;
    private String secretKey;
    private String projectId;
    private String runName;
    private String outputFolder = "./features/";
    private String jsonReportFolder = "./reports/";
    private String jsonReportIncludePattern;
    private String proxyURI;
    private String proxyUsername;
    private String proxyPassword;
    private String mode;
    private String jql;

    public Arguments(String accessKey,
                     String secretKey,
                     String projectId,
                     String proxyURI,
                     String proxyUsername,
                     String proxyPassword,
                     String mode,
                     String jql) {
        this(accessKey,
                secretKey,
                projectId,
                null,
                null,
                null,
                null,
                proxyURI,
                proxyUsername,
                proxyPassword,
                mode,
                jql);
    }

    public Arguments(String accessKey,
                     String secretKey,
                     String projectId,
                     String runName,
                     String outputFolder,
                     String jsonReportFolder,
                     String jsonReportIncludePattern,
                     String proxyURI,
                     String proxyUsername,
                     String proxyPassword,
                     String mode,
                     String jql) {
        this.accessKey = System.getenv("ASSERTTHAT_ACCESS_KEY");
        this.secretKey = System.getenv("ASSERTTHAT_SECRET_KEY");
        if (accessKey != null && !accessKey.trim().isEmpty()) {
            this.accessKey = accessKey;
        }
        if (secretKey != null && !secretKey.trim().isEmpty()) {
            this.secretKey = secretKey;
        }
        this.projectId = projectId;
        if (outputFolder != null) {
            this.outputFolder = outputFolder;
        }
        this.jsonReportIncludePattern = jsonReportIncludePattern;
        this.proxyURI = proxyURI;
        this.proxyPassword = proxyPassword;
        this.proxyUsername = proxyUsername;

        if (runName != null) {
            this.runName = runName;
        } else {
            this.runName = "Test run " + new SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(new Date());
        }

        if (jsonReportFolder != null) {
            this.jsonReportFolder = jsonReportFolder;
            this.jsonReportFolder += jsonReportFolder.endsWith("/") ? "" : "/";
        }
        this.mode = mode;
        this.jql = jql;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getJql() {
        return jql;
    }

    public void setJql(String jql) {
        this.jql = jql;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRunName() {
        return runName;
    }

    public void setRunName(String runName) {
        this.runName = runName;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public String getJsonReportFolder() {
        return jsonReportFolder;
    }

    public void setJsonReportFolder(String jsonReportFolder) {
        this.jsonReportFolder = jsonReportFolder;
    }

    public String getJsonReportIncludePattern() {
        return jsonReportIncludePattern;
    }

    public void setJsonReportIncludePattern(String jsonReportIncludePattern) {
        this.jsonReportIncludePattern = jsonReportIncludePattern;
    }

    public String getProxyURI() {
        return proxyURI;
    }

    public void setProxyURI(String proxyURI) {
        this.proxyURI = proxyURI;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

}
