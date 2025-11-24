# Troubleshooting

## Build & Synth
- **`Could not resolve aws-cdk-lib`**: ensure Maven Central reachable; if offline, pre-download or use a cached repo
- **`No AWS credentials`**: export `AWS_PROFILE` or `AWS_ACCESS_KEY_ID` / `AWS_SECRET_ACCESS_KEY` and `AWS_REGION`
- **`Stack name has "null" suffix`**: set `ENVIRONMENT_SUFFIX` (or context) to an empty string or desired suffix

## Deployment
- **S3 bucket name already exists**: tweak `PROJECT_NAME`/`ENVIRONMENT`/`ENVIRONMENT_SUFFIX` to create unique names
- **SNS subscription not delivered**: confirm email and click the confirmation link; check spam folder
- **EC2 launch failures**: verify default VPC limits/AZ availability and instance quotas; check that AMI retrieval works in target region
- **RDS creation stuck**: ensure subnet group subnets are in at least two AZs and security group allows port 3306 from web SG

## Post-Deploy Validation
- **Integration tests skipped**: ensure `cfn-outputs/flat-outputs.json` exists with outputs from the deployed stack
- **Alarms not firing**: ensure SNS subscription is confirmed and CloudWatch alarm state changes observed

## Cleanup
- **Destroy fails due to buckets**: empty CloudTrail bucket (if enabled) before rerunning destroy
- **Orphaned Elastic IP/NAT**: verify VPC deletion; remove leftover NAT gateways manually if needed
