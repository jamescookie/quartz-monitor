#!/usr/bin/env bash
EXIT_STATUS=0

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"

rm -rf build
./gradlew clean check --stacktrace || EXIT_STATUS=$?

exit $EXIT_STATUS
