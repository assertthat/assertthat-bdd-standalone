package com.assertthat.plugins;

import com.assertthat.plugins.standalone.APIUtil;
import org.codehaus.jettison.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class APIUtilIntegrationTest {

    private APIUtil apiUtil;
    private static final String PROJECT_ID = System.getenv("PROJECT_ID");
    private static final String ACCESS_KEY = System.getenv("ACCESS_KEY");
    private static final String SECRET_KEY = System.getenv("SECRET_KEY");
    private static final String TOKEN = System.getenv("TOKEN");
    private static final String JIRA_SERVER_URL = System.getenv("JIRA_SERVER_URL");

   // @Before
    public void setUp() {
        assertNotNull("PROJECT_ID environment variable is required", PROJECT_ID);
        assertNotNull("ACCESS_KEY environment variable is required", ACCESS_KEY);
        assertNotNull("SECRET_KEY environment variable is required", SECRET_KEY);
        assertNotNull("TOKEN environment variable is required", TOKEN);
        assertNotNull("JIRA_SERVER_URL environment variable is required", JIRA_SERVER_URL);

        apiUtil = new APIUtil(PROJECT_ID, ACCESS_KEY, SECRET_KEY, TOKEN, null, null, null, JIRA_SERVER_URL, false);
    }

   //@Test
    public void testDownload() throws IOException {
        // Define target directory for downloading files
        File targetDir = new File("target/features");
        if (targetDir.exists()) {
            for (File f : targetDir.listFiles()) {
                if (f.getName().endsWith(".feature")) {
                    f.delete();
                }
            }
        } else {
            targetDir.mkdirs();
        }

        // Download features with specific parameters
        File downloadedFile = apiUtil.download(targetDir, "automated", null, null, true, true);

        // Verify the downloaded file
        assertNotNull("Downloaded file should not be null", downloadedFile);
        assertTrue("Downloaded file should exist", downloadedFile.exists());
        assertTrue("Downloaded file should be a zip file", downloadedFile.getName().endsWith(".zip"));

        System.out.println("Downloaded file path: " + downloadedFile.getAbsolutePath());
    }

    //@Test
    public void testUpload() throws IOException, JSONException {
        // Prepare a test file to upload
        File testFile = new File("src/test/java/com/assertthat/plugins/cucumber.json");
        if (!testFile.exists()) {
            // Assuming this is a test integration, create a small sample file
            testFile.createNewFile();
        }

        // Upload the file
        Long runId = -1L; // Example runId
        String runName = "IntegrationTestRun";
        String type = "type";
        String metadata = "{\"sample\":\"data\"}";

        Long resultRunId = apiUtil.upload(runId, runName, testFile.getAbsolutePath(), type, metadata, null);

        // Verify that upload was successful
        assertNotNull("Returned runId should not be null", resultRunId);
        assertEquals("Returned runId should match the expected", runId, resultRunId);

        System.out.println("Uploaded run ID: " + resultRunId);
    }
}