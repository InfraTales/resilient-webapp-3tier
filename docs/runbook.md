# Operations Runbook

## Overview
This runbook provides operational procedures for managing and maintaining this infrastructure.

## Prerequisites
- AWS CLI configured
- Terraform/CDK/Pulumi installed
- Appropriate IAM permissions

## Common Operations

### Deployment
```bash
# Development
./scripts/deploy.sh dev

# Production
./scripts/deploy.sh prod
```

### Monitoring
- CloudWatch Dashboard: Check AWS Console
- Alerts: Configured via SNS
- Logs: CloudWatch Logs

### Troubleshooting

#### Issue: Deployment Fails
**Symptoms**: Terraform/CDK apply fails
**Resolution**:
1. Check AWS credentials
2. Verify IAM permissions
3. Review error logs
4. Check resource quotas

#### Issue: High Costs
**Symptoms**: Unexpected AWS charges
**Resolution**:
1. Review Cost Explorer
2. Check for unused resources
3. Verify auto-scaling policies
4. Review instance types

### Maintenance Windows
- Preferred: Sunday 02:00-06:00 UTC
- Avoid: Business hours (09:00-17:00 local time)

### Escalation
1. Team Lead
2. DevOps Manager
3. On-call Engineer

## Emergency Procedures

### Rollback
```bash
# Terraform
terraform apply -var-file=previous.tfvars

# CDK
cdk deploy --previous-version

# Pulumi
pulumi stack select previous
pulumi up
```

### Disaster Recovery
See [DISASTER_RECOVERY.md](DISASTER_RECOVERY.md)
