#!/bin/bash

BASE_DIR=$(git rev-parse --show-toplevel)
cd "$BASE_DIR/apps/book-service" || return

./gradlew bootJar
