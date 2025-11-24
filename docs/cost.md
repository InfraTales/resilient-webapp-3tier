# Cost Guide

## Baseline monthly estimate (₹, eu-north-1)
- NAT Gateways (2x): ~₹7,000–₹8,000
- EC2 t3.medium (2x on-demand): ~₹7,500–₹8,500
- RDS MySQL t3.micro multi-AZ + 20GB gp2 + backups: ~₹2,000–₹2,500
- S3 (CloudTrail, logs) + CloudWatch metrics: ~₹500–₹1,000 when enabled
- **Total**: ~₹17k–₹20k/month. Traffic, storage growth, and data transfer can move this materially.

## Cost levers
- Reduce NATs (from 2 to 1) if AZ resilience for egress is acceptable
- Scale EC2 down (t3.small/micro) or replace with ASG/Spot for dev/test
- Turn on CloudTrail selectively (single-region, log file validation) to balance audit vs. spend
- Right-size RDS storage and backup retention; disable Multi-AZ outside production
- Use S3 lifecycle for CloudTrail logs (already 90-day expiry) and consider Glacier

## Monitoring spend
- Enable AWS Cost Anomaly Detection and Budgets with alerts to the SNS topic
- Tag-based cost allocation: Project, Environment, Owner, CostCenter already applied
- Capture per-service usage via Cost Explorer; watch NAT data processing and EC2 hours

## Cleanup reminders
- Destroy stacks promptly in non-prod: `cdk destroy --all --force`
- Delete leftover S3 buckets/CloudWatch log groups if a destroy is interrupted
