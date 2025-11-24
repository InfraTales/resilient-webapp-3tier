# Quick Start Guide

## Prerequisites
1. **Java 17+** installed (`java -version`)
2. **Node.js 20+** installed (`node -v`)
3. **AWS CDK** installed (`npm install -g aws-cdk`)
4. **AWS Credentials** configured (`aws configure`)

## 1. Clone & Configure
```bash
git clone https://github.com/infratales/webapp-3tier-resilient-cdk-java.git
cd webapp-3tier-resilient-cdk-java

# Optional: Customize environment
export ENVIRONMENT_SUFFIX=dev
export OFFICE_CIDR=YOUR_IP/32
```

## 2. Build & Test
```bash
./gradlew build
```

## 3. Deploy
```bash
cdk deploy --all --require-approval never
```

## 4. Verify
Check the CloudFormation outputs for the VPC ID and RDS Endpoint.
