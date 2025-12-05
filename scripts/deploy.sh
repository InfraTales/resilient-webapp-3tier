#!/bin/bash
# CDK Deployment Script

set -e

ENVIRONMENT=${1:-dev}

echo "Deploying CDK stack to $ENVIRONMENT..."

# Install dependencies
npm install

# Bootstrap (if needed)
# cdk bootstrap

# Synthesize
cdk synth

# Deploy
cdk deploy --all --require-approval never

echo "✓ Deployment complete!"
