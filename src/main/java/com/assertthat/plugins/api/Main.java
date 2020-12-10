package com.assertthat.plugins.api;

import com.assertthat.plugins.internal.APIUtil;
import com.assertthat.plugins.internal.Arguments;
import com.assertthat.plugins.internal.FileUtil;
import org.apache.commons.cli.*;
import org.codehaus.jettison.json.JSONException;

import java.io.File;
import java.io.IOException;

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
public class Main {

    public static void main(String[] args) throws IOException, JSONException {
        String ASSERTTHAT_ACCESS_KEY = System.getenv("ASSERTTHAT_ACCESS_KEY");
        String ASSERTTHAT_SECRET_KEY = System.getenv("ASSERTTHAT_SECRET_KEY");

        Options options = new Options();

        Option accessKeyOption = new Option("a", "accessKey", true, "Access key");
        if (ASSERTTHAT_ACCESS_KEY == null || ASSERTTHAT_ACCESS_KEY.trim().isEmpty()) {
            accessKeyOption.setRequired(true);
        }
        accessKeyOption.setArgName("ASSERTTHAT_ACCESS_KEY");
        options.addOption(accessKeyOption);

        Option secretKeyOption = new Option("s", "secretKey", true, "Secret key");
        if (ASSERTTHAT_SECRET_KEY == null || ASSERTTHAT_SECRET_KEY.trim().isEmpty()) {
            secretKeyOption.setRequired(true);
        }
        secretKeyOption.setArgName("ASSERTTHAT_SECRET_KEY");
        options.addOption(secretKeyOption);

        Option projectIdOption = new Option("i", "projectId", true, "Jira project id");
        projectIdOption.setRequired(true);
        projectIdOption.setArgName("ID");
        options.addOption(projectIdOption);

        Option runNameOption = new Option("n", "runName", true, "Test run name");
        runNameOption.setRequired(false);
        runNameOption.setArgName("NAME");
        options.addOption(runNameOption);

        Option outputFolderOption = new Option("o", "outputFolder", true, "Features output folder");
        outputFolderOption.setRequired(false);
        outputFolderOption.setArgName("FOLDER PATH");
        options.addOption(outputFolderOption);

        Option jiraServerUrl = new Option("l", "jiraServerUrl", true, "Jira server URL");
        outputFolderOption.setRequired(false);
        outputFolderOption.setArgName("JIRA SERVER URL");
        options.addOption(jiraServerUrl);

        Option inputFolderOption = new Option("j", "jsonReportFolder", true, "Cucumber json files folder");
        inputFolderOption.setRequired(false);
        inputFolderOption.setArgName("FOLDER PATH");
        options.addOption(inputFolderOption);

        Option jsonPatternOption = new Option("t", "jsonReportIncludePattern", true, "Pattern for json file names");
        jsonPatternOption.setRequired(false);
        jsonPatternOption.setArgName("PATTERN");
        options.addOption(jsonPatternOption);

        Option proxyURLOption = new Option("x", "proxyURI", true, "Proxy URI");
        proxyURLOption.setRequired(false);
        proxyURLOption.setArgName("URI");
        options.addOption(proxyURLOption);

        Option proxyUsernameOption = new Option("u", "proxyUsername", true, "Proxy username");
        proxyUsernameOption.setRequired(false);
        proxyUsernameOption.setArgName("USERNAME");
        options.addOption(proxyUsernameOption);

        Option proxyPasswordOption = new Option("p", "proxyPassword", true, "Proxy password");
        proxyPasswordOption.setRequired(false);
        proxyPasswordOption.setArgName("PASSWORD");
        options.addOption(proxyPasswordOption);

        Option modeOption = new Option("m", "mode", true, "Features to download");
        modeOption.setRequired(false);
        modeOption.setArgName("automated|manual|both");
        options.addOption(modeOption);

        Option jqlOption = new Option("q", "jql", true, "JQL filter for features");
        jqlOption.setRequired(false);
        jqlOption.setArgName("JQL");
        options.addOption(jqlOption);

        Option tagsOption = new Option("b", "tags", true, "Tags filter for " +
                "scenarios");
        tagsOption.setRequired(false);
        tagsOption.setArgName("tags");
        options.addOption(tagsOption);

        Option typeOption = new Option("k", "type", true, "Report type");
        typeOption.setRequired(false);
        typeOption.setArgName("cucumber|karate");
        options.addOption(typeOption);

        Option featuresOption = new Option("f", "features", false, "Download features");
        featuresOption.setRequired(false);

        Option numberedOption = new Option("d", "numbered", false, "Prepend ordinal to feature name");
        numberedOption.setRequired(false);
        options.addOption(numberedOption);

        Option reportOption = new Option("r", "report", false, "Upload report");
        reportOption.setRequired(false);

        Option helpOption = new Option("h", "help", false, "Display help");
        helpOption.setRequired(false);
        options.addOption(helpOption);

        OptionGroup actionGroup = new OptionGroup();
        actionGroup.addOption(featuresOption);
        actionGroup.addOption(reportOption);

        actionGroup.setRequired(true);

        options.addOptionGroup(actionGroup);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            formatter.printHelp("assertthat-bdd-standalone-1.3.jar", options);
            System.exit(1);
        }
        if (cmd.hasOption("help")) {
            formatter.printHelp("assertthat-bdd-standalone-1.3.jar", options);
            System.exit(0);
        }

        Arguments arguments = new Arguments(
                cmd.getOptionValue("accessKey"),
                cmd.getOptionValue("secretKey"),
                cmd.getOptionValue("projectId"),
                cmd.getOptionValue("runName"),
                cmd.getOptionValue("outputFolder"),
                cmd.getOptionValue("jsonReportFolder"),
                cmd.getOptionValue("jsonReportIncludePattern"),
                cmd.getOptionValue("proxyURI"),
                cmd.getOptionValue("proxyUsername"),
                cmd.getOptionValue("proxyPassword"),
                cmd.getOptionValue("mode"),
                cmd.getOptionValue("jql"),
                cmd.getOptionValue("tags"),
                cmd.getOptionValue("type"),
                cmd.getOptionValue("jiraServerUrl"),
                cmd.hasOption("numbered")
        );

        APIUtil apiUtil = new APIUtil(arguments.getProjectId(), arguments.getAccessKey(), arguments.getSecretKey(), arguments.getProxyURI(), arguments.getProxyUsername(), arguments.getProxyPassword(), arguments.getJiraServerUrl());

        if (cmd.hasOption("features")) {
            File inZip =
                    apiUtil.download(new File(arguments.getOutputFolder()),
                            arguments.getMode(), arguments.getJql() ,
                            arguments.getTags(), arguments.isNumbered());
            File zip = new FileUtil().unpackArchive(inZip, new File(arguments.getOutputFolder()));
            zip.delete();
        }
        if (cmd.hasOption("report")) {
            String[] files = new FileUtil().findJsonFiles(new File(arguments.getJsonReportFolder()), arguments.getJsonReportIncludePattern(), null);
            Long runid = -1L;
            for (String f : files) {
                runid = apiUtil.upload(runid, arguments.getRunName(), arguments.getJsonReportFolder() + f, arguments.getType(),null);
            }
        }
    }
}
