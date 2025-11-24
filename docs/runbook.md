# Runbook

## Deploy
1) Set environment variables (examples):
   - `ENVIRONMENT_SUFFIX=dev`
   - `AWS_REGION=eu-north-1`
   - `OFFICE_CIDR=198.51.100.0/24`
   - `ALERT_EMAIL=ops@example.com`
2) Bootstrap if needed: `cdk bootstrap --context environmentSuffix=$ENVIRONMENT_SUFFIX`
3) Build & unit test: `gradle clean test`
4) Synthesize: `cdk synth --app "gradle run"`
5) Deploy: `cdk deploy --all --require-approval never --context environmentSuffix=$ENVIRONMENT_SUFFIX`

## Verify
- Check CloudFormation stack status is `CREATE_COMPLETE`
- Confirm SNS subscription email was confirmed
- Validate EC2 instances are running in private subnets
- Retrieve RDS endpoint from stack outputs

## Operations
- **Scaling**: adjust EC2 instance type/count in `createEc2Instances`; re-deploy
- **Patching**: bake AMI updates or use SSM Patch Manager; update ASG (if added) / instance type then deploy
- **Backups**: RDS automated backups enabled; adjust retention in code if policy requires
- **Key rotation**: KMS key rotation is enabled by default; monitor in KMS console

## Logs & Metrics
- EC2/SSM/CloudWatch Agent metrics via managed policy on the instance role
- CloudWatch alarms on EC2 CPU -> SNS topic
- Optional CloudTrail logs to KMS-encrypted S3 bucket when enabled

## Incident Response
- Quarantine EC2: edit web SG to deny ingress; optionally stop instances
- Database credentials: rotate via Secrets Manager entry created by CDK
- Audit: enable CloudTrail and re-deploy if not already enabled

## Teardown
- Destroy stacks: `cdk destroy --all --force --context environmentSuffix=$ENVIRONMENT_SUFFIX`
- Remove remaining buckets/log groups if destroy fails (after confirming contents)
