package com.assertthat.plugins.internal;

import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.multipart.Boundary;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.MultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.*;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA_TYPE;

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
    private String accessKey;
    private String secretKey;
    private String projectId;
    private String featuresUrl;
    private String reportUrl;
    private DefaultClientConfig config = new DefaultClientConfig();

    public APIUtil(String projectId, String accessKey, String secretKey, String proxyURI, String proxyUsername, String proxyPassword, String jiraServerURL) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.projectId = projectId;
        if (proxyURI != null && !proxyURI.trim().isEmpty()) {
            this.config.getProperties().put("com.sun.jersey.impl.client.httpclient.proxyURI", proxyURI);
        }
        if (proxyUsername != null && !proxyUsername.trim().isEmpty()) {
            this.config.getProperties().put("com.sun.jersey.impl.client.httpclient.proxyUsername", proxyUsername);
        }
        if (proxyPassword != null && !proxyPassword.trim().isEmpty()) {
            this.config.getProperties().put("com.sun.jersey.impl.client.httpclient.proxyPassword", proxyPassword);
        }
        if(jiraServerURL!=null) {
            this.featuresUrl = jiraServerURL+"/rest/assertthat/latest/project/"+projectId+"/client/features";
            this.reportUrl = jiraServerURL+"/rest/assertthat/latest/project/"+projectId+"/client/report";
        }else{
            this.featuresUrl = "https://bdd.assertthat.app/rest/api/1/project/" + projectId + "/features";
            this.reportUrl = "https://bdd.assertthat.app/rest/api/1/project/" + projectId + "/report";
        }
    }

    public static void copyInputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        while (len >= 0) {
            out.write(buffer, 0, len);
            len = in.read(buffer);
        }
        in.close();
        out.close();
    }

    public File download(File targetDir, String mode, String jql,
                         String tags) throws IOException {
        if (targetDir.exists()) {
            for (File f : targetDir.listFiles()) {
                if (f.getName().endsWith(".feature")) {
                    f.delete();
                }
            }
        }else{
            targetDir.mkdirs();
        }
        Client client = ApacheHttpClient4.create(config);
        client.addFilter(new HTTPBasicAuthFilter(this.accessKey, this.secretKey));
        WebResource webResource = client.resource(this.featuresUrl);
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        if (mode != null) {
            queryParams.add("mode", mode.trim());
        }
        if (tags != null) {
            queryParams.add("tags", tags.trim());
        }
        if (jql != null) {
            queryParams.add("jql", jql.trim());
        }
        client.addFilter(new ClientFilter() {
            @Override
            public ClientResponse handle(ClientRequest
                                                 request)
                    throws ClientHandlerException {
                request.getHeaders().add(
                        HttpHeaders.USER_AGENT,
                        USER_AGENT);
                return getNext().handle(request);
            }
        });
        ClientResponse response = webResource.queryParams(queryParams).get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
        }
        InputStream in = new BufferedInputStream(response.getEntity(InputStream.class));
        File zip = File.createTempFile("arc", ".zip", targetDir);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(zip));
        copyInputStream(in, out);
        out.close();
        return zip;
    }

    public Long upload(Long runId, String runName, String filePath, String type, String metadata) throws IOException, JSONException {
        config.getClasses().add(FormDataMultiPart.class);
        config.getClasses().add(MultiPartWriter.class);
        Client client = ApacheHttpClient4.create(config);
        client.addFilter(new HTTPBasicAuthFilter(this.accessKey, this.secretKey));
        WebResource webResource = client.resource(this.reportUrl);
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("runName", runName);
        queryParams.add("runId", runId.toString());
        queryParams.add("type", type);
        queryParams.add("metadata", metadata);
        client.addFilter(new ClientFilter() {
            @Override
            public ClientResponse handle(ClientRequest
                                                 request)
                    throws ClientHandlerException {
                request.getHeaders().add(
                        HttpHeaders.USER_AGENT,
                        USER_AGENT);
                return getNext().handle(request);
            }
        });
        File fileToUpload = new File(filePath);
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file",
                fileToUpload,
                MediaType.APPLICATION_OCTET_STREAM_TYPE);
        fileDataBodyPart.setContentDisposition(
                FormDataContentDisposition.name("file")
                        .fileName(fileToUpload.getName()).build());
        final MultiPart multiPart = new FormDataMultiPart()
                .bodyPart(fileDataBodyPart);
        multiPart.setMediaType(MULTIPART_FORM_DATA_TYPE);
        ClientResponse response = webResource
                .queryParams(queryParams)
                .type(MULTIPART_FORM_DATA_TYPE)
                .type(Boundary.addBoundary(MULTIPART_FORM_DATA_TYPE))
                .post(ClientResponse.class, multiPart);

        if (response.getStatus() == 200) {
            String responseBody = IOUtils.toString(response.getEntityInputStream(), "UTF-8");
            JSONObject responseJson = new JSONObject(responseBody);
            return Long.valueOf(responseJson.getString("runId"));
        } else {
            LOGGER.warning("Failed to process " + filePath);
            return runId;
        }
    }
}
