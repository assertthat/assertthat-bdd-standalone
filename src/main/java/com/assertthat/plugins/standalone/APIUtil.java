package com.assertthat.plugins.standalone;

import okhttp3.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;

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
public class APIUtil {

    private final static Logger LOGGER = Logger.getLogger(APIUtil.class.getName());
    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0";
    private String featuresUrl;
    private String reportUrl;
    private OkHttpClient client;

    public APIUtil(String projectId, String accessKey, String secretKey, String proxyURI, String proxyUsername, String proxyPassword, String jiraServerURL, boolean ignoreCertErrors) {
        if (jiraServerURL != null) {
            this.featuresUrl = jiraServerURL + "/rest/assertthat/latest/project/" + projectId + "/client/features";
            this.reportUrl = jiraServerURL + "/rest/assertthat/latest/project/" + projectId + "/client/report";
        } else {
            this.featuresUrl = "https://bdd.assertthat.app/rest/api/1/project/" + projectId + "/features";
            this.reportUrl = "https://bdd.assertthat.app/rest/api/1/project/" + projectId + "/report";
        }
        OkHttpClientBuilder builder = new OkHttpClientBuilder();
        builder.authenticated(accessKey, secretKey);
        if (ignoreCertErrors) {
            builder.ignoringCertificate();
        }
        if (proxyURI != null && !proxyURI.trim().isEmpty()) {
            URL url;
            try {
                url = new URL(proxyURI);
            } catch (MalformedURLException e) {
                throw new RuntimeException("[ERROR] Parsing proxy URL: " + e.getMessage());
            }
            builder.withProxy(url.getHost(), url.getPort());
        }
        if (proxyUsername != null && !proxyUsername.trim().isEmpty() && proxyPassword != null
                && !proxyPassword.trim().isEmpty()) {
            builder.withProxyAuth(proxyUsername, proxyPassword);
        }
        client = builder.build();
    }

    public File download(File targetDir, String mode, String jql,
                         String tags, boolean isNumbered, boolean cleanupFeatures) throws IOException {
        if (cleanupFeatures && targetDir.exists()) {
            for (File f : targetDir.listFiles()) {
                if (f.getName().endsWith(".feature")) {
                    f.delete();
                }
            }
        } else {
            targetDir.mkdirs();
        }
        HttpUrl.Builder httpBuilder = HttpUrl.parse(this.featuresUrl).newBuilder();
        if (mode != null) {
            httpBuilder.addQueryParameter("mode", mode.trim());
        }
        if (tags != null) {
            httpBuilder.addQueryParameter("tags", tags.trim());
        }
        if (jql != null) {
            httpBuilder.addQueryParameter("jql", jql.trim());
        }
        httpBuilder.addQueryParameter("numbered", String.valueOf(isNumbered));
        Request.Builder request = new Request.Builder().url(httpBuilder.build()).addHeader("User-Agent",
                USER_AGENT);
        Response response = client.newCall(request.build()).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Failed to download file: " + response);
        }
        File zip = File.createTempFile("arc", ".zip", targetDir);
        FileOutputStream fos = new FileOutputStream(zip);
        fos.write(response.body().bytes());
        fos.close();
        return zip;
    }

    public Long upload(Long runId, String runName, String filePath, String type, String metadata, String jql) throws IOException, JSONException {
        File fileToUpload = new File(filePath);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", fileToUpload.getName(),
                        RequestBody.create(MediaType.parse("application/json"), fileToUpload))
                .addFormDataPart("some-field", "some-value")
                .build();
        HttpUrl.Builder httpBuilder = HttpUrl.parse(this.reportUrl).newBuilder();
        httpBuilder.addQueryParameter("runName", runName);
        httpBuilder.addQueryParameter("runId", runId.toString());
        httpBuilder.addQueryParameter("type", type);
        if (jql != null) {
            httpBuilder.addQueryParameter("jql", jql.trim());
        }
        if (metadata != null) {
            httpBuilder.addQueryParameter("metadata", URLEncoder.encode(metadata, "UTF-8"));
        }
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .post(requestBody)
                .addHeader("User-Agent", USER_AGENT)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            JSONObject responseJson = new JSONObject(response.body().string());
            return Long.valueOf(responseJson.getString("runId"));
        } else {
            LOGGER.warning(response.body().string());
            LOGGER.warning("Failed to process " + filePath);
            return runId;
        }
    }

}
