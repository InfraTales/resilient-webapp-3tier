#!/bin/bash
# CDK Destroy Script

set -e

ENVIRONMENT=${1:-dev}

echo "⚠️  WARNING: This will destroy all CDK stacks!"
read -p "Are you sure? (yes/no): " CONFIRM

if [ "$CONFIRM" != "yes" ]; then
  echo "Cancelled."
  exit 0
fi

cdk destroy --all --force

echo "✓ Destruction complete!"
