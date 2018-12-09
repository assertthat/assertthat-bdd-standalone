
[![Build Status](https://travis-ci.org/assertthat/assertthat-bdd-standalone.svg?branch=master)](https://travis-ci.org/assertthat/assertthat-bdd-standalone)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.assertthat.plugins/assertthat-bdd-standalone/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.assertthat.plugins/assertthat-bdd-standalone)


## Description

Standalone API client for AssertThat BDD Jira plugin.

Main features are:

- Download feature files before test run
- Filter features to download based on mode (automated/manual/both), or/and JQL
- Upload cucumber json after the run to AsserTthat Jira plugin

## Installation

- Download jar file from the latest release here on GitHub

## Usage

```
java -jar assertthat-bdd-standalone-1.0.jar
Missing required options: a, s, i, [-f Download features, -r Upload report, -h Display help]
usage: assertthat-bdd-standalone
 -a,--accessKey <ASSERTTHAT_ACCESS_KEY>    Access key
 -f,--features                             Download features
 -h,--help                                 Display help
 -i,--projectId <ID>                       Jira project id
 -j,--jsonReportFolder <FOLDER PATH>       Cucumber json files folder
 -m,--mode <automated|manual|both>         Features to download
 -n,--runName <NAME>                       Test run name
 -o,--outputFolder <FOLDER PATH>           Features output folder
 -p,--proxyPassword <PASSWORD>             Proxy password
 -q,--jql <JQL>                            JQL filter for features
 -r,--report                               Upload report
 -s,--secretKey <ASSERTTHAT_SECRET_KEY>    Secret key
 -t,--jsonReportIncludePattern <PATTERN>   Pattern for json file names
 -u,--proxyUsername <USERNAME>             Proxy username
 -x,--proxyURI <URI>                       Proxy URI
```