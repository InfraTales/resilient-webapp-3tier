# Security Posture

## Built-in controls
- KMS CMK with rotation enabled; used by RDS and (when enabled) CloudTrail bucket
- S3 CloudTrail bucket blocks public access and enforces versioning + 90-day lifecycle
- RDS: storage encryption on, deletion protection true, multi-AZ, generated admin secret
- VPC segmentation: public vs. private-with-egress subnets; RDS in private subnets only
- Security groups: web ingress limited to configurable office CIDR; RDS ingress only from web SG
- Tags for traceability: Project, Environment, Owner, CostCenter, ManagedBy

## Items to harden
- CloudTrail default is off; enable via `enableCloudTrail` context for auditability
- Web SG allows all outbound; restrict egress to required ports/destinations if possible
- EC2 inline S3 access uses `webapp-*` wildcard; scope to specific bucket/paths when known
- Replace default office CIDR (`203.0.113.0/24`) with your real ranges
- Ensure SNS email subscription is set to a monitored list (via `ALERT_EMAIL`/context)

## Secrets and credentials
- RDS credentials generated via Secrets Manager (by CDK) and not hardcoded
- No plaintext secrets in code; configuration driven by env/context

## Compliance recommendations
- Add AWS Config rules / Security Hub standards in the account
- Enable VPC Flow Logs and CloudWatch metric filters for critical events
- Consider WAF/ALB in front of the EC2 tier if internet-facing
