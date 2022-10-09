
[![Build Status](https://travis-ci.org/assertthat/assertthat-bdd-standalone.svg?branch=master)](https://travis-ci.org/assertthat/assertthat-bdd-standalone)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.assertthat.plugins/assertthat-bdd-standalone/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.assertthat.plugins/assertthat-bdd-standalone)


## Description

Standalone API client for [AssertThat BDD Jira plugin](https://marketplace.atlassian.com/apps/1219033/assertthat-bdd-test-management-in-jira?hosting=cloud&tab=overview).

Main features are:

- Download feature files before test run
- Filter features to download based on mode (automated/manual/both), or/and JQL
- Upload cucumber json after the run to AsserTthat Jira plugin

## Installation

- Download jar file from the latest [release assets section](https://github.com/assertthat/assertthat-bdd-standalone/releases)

## Usage

```
java -jar assertthat-bdd-standalone-1.9.5.jar

Required options: accessKey, secretKey, projectId, [-features Download features, -report Upload report]

usage: assertthat-bdd-standalone-1.9.5.jar
 -accessKey <ASSERTTHAT_ACCESS_KEY>    Access key
 -features                             Download features
 -h,--help                             Display help
 -ignoreCertErrors <arg>               Ignore ssl certificate eerors
                                       (default is false)
 -jiraServerUrl <arg>                  Jira server URL
 -jql <JQL>                            JQL filter for features and Jira
                                       ticket to be updated with report
                                       upload
 -jsonReportFolder <FOLDER PATH>       Cucumber json files folder
 -jsonReportIncludePattern <PATTERN>   Pattern for json file names
 -metadata <{ "key" : "value"}>        Report metadata json
 -mode <automated|manual|both>         Features to download
 -numbered <true|false>                Prepend ordinal to feature name
                                       (default is true)
 -outputFolder <JIRA SERVER URL>       Features output folder
 -projectId <ID>                       Jira project id
 -proxyPassword <PASSWORD>             Proxy password
 -proxyURI <URI>                       Proxy URI
 -proxyUsername <USERNAME>             Proxy username
 -report                               Upload report
 -runName <NAME>                       Test run name
 -secretKey <ASSERTTHAT_SECRET_KEY>    Secret key
 -tags <tags>                          Tags filter for scenarios
 -type <cucumber|karate>               Report type

```
