#!/bin/bash

# Used to run unit tests on library

ATTLIB_DIR_NAME='codekit'

script_dir=$(dirname "$0")
full_dir="$script_dir/$ATTLIB_DIR_NAME"

cd "$full_dir"
mvn clean test
