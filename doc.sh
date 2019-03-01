#!/bin/sh

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"

cd plugin

rm -rf docs/manual
grails doc --pdf --stacktrace | grep -v javadoc
rm -rf docs/manual/api
rm -rf docs/manual/gapi
grails add-tracking
cd ..
