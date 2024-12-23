package com.assertthat.plugins.standalone;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (c) 2018 AssertThat
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p>
 * Created by Glib_Briia on 15/05/2018.
 */
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
    private String metadata;
    private String jql;
    private String jiraServerUrl;
    private String type = "cucumber";
    private String tags;
    private String token;
    private boolean numbered;
    private boolean ignoreCertErrors;

    private boolean cleanupFeatures = true;

    Arguments(String accessKey,
              String secretKey,
              String token,
              String projectId,
              String runName,
              String outputFolder,
              String jsonReportFolder,
              String jsonReportIncludePattern,
              String proxyURI,
              String proxyUsername,
              String proxyPassword,
              String mode,
              String jql,
              String tags,
              String type,
              String jiraServerUrl,
              String metadata,
              boolean numbered,
              boolean ignoreCertErrors,
              boolean cleanupFeatures) {
        this.accessKey = System.getenv("ASSERTTHAT_ACCESS_KEY");
        this.secretKey = System.getenv("ASSERTTHAT_SECRET_KEY");
        this.token = System.getenv("ASSERTTHAT_TOKEN");
        if (accessKey != null && !accessKey.trim().isEmpty()) {
            this.accessKey = accessKey;
        }
        if (secretKey != null && !secretKey.trim().isEmpty()) {
            this.secretKey = secretKey;
        }
        if (token != null && !token.trim().isEmpty()) {
            this.token = token;
        }
        this.projectId = projectId;
        if (outputFolder != null && !outputFolder.trim().isEmpty()) {
            this.outputFolder = outputFolder;
        }
        if (jsonReportIncludePattern != null && !jsonReportIncludePattern.trim().isEmpty()) {
            this.jsonReportIncludePattern = jsonReportIncludePattern;
        }
        this.proxyURI = proxyURI;
        this.proxyPassword = proxyPassword;
        this.proxyUsername = proxyUsername;
        this.jiraServerUrl = jiraServerUrl;
        this.numbered = numbered;

        if (runName != null && !runName.isEmpty()) {
            this.runName = runName;
        } else {
            this.runName = "Test run " + new SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(new Date());
        }
        if (type != null && !type.isEmpty()) {
            this.type = type;
        }
        if (tags != null && !tags.isEmpty()) {
            this.tags = tags;
        }

        if (metadata != null && !metadata.isEmpty()) {
            this.metadata = metadata;
        }

        if (jsonReportFolder != null && !jsonReportFolder.trim().isEmpty()) {
            this.jsonReportFolder = jsonReportFolder;
            this.jsonReportFolder += jsonReportFolder.endsWith("/") ? "" : "/";
        }
        this.mode = mode;
        this.jql = jql;
        this.ignoreCertErrors = ignoreCertErrors;
        this.cleanupFeatures = cleanupFeatures;
    }

    public boolean isIgnoreCertErrors() {
        return ignoreCertErrors;
    }

    public void setIgnoreCertErrors(boolean ignoreCertErrors) {
        this.ignoreCertErrors = ignoreCertErrors;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getJiraServerUrl() {
        return jiraServerUrl;
    }

    public void setJiraServerUrl(String jiraServerUrl) {
        this.jiraServerUrl = jiraServerUrl;
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

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isNumbered() {
        return numbered;
    }

    public void setNumbered(boolean numbered) {
        this.numbered = numbered;
    }

    public boolean isCleanupFeatures() {
        return cleanupFeatures;
    }

    public void setCleanupFeatures(boolean cleanupFeatures) {
        this.cleanupFeatures = cleanupFeatures;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
