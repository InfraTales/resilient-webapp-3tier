#!/bin/bash
# CDK Diff Script

set -e

npm install
cdk diff --all
