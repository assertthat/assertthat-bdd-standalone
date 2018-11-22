#!/usr/bin/env bash
if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    openssl aes-256-cbc -K $encrypted_1592987b81cf_key -iv $encrypted_1592987b81cf_iv -in codesigning.asc.enc -out codesigning.asc -d
    gpg --fast-import cd/codesigning.asc
fi