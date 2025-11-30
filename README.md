# InfraTales | Resilient Web Architecture – Fault-Tolerant 3-Tier on AWS

Public, production-focused CDK (Java) reference that stands up a resilient three-tier foundation: VPC with public/private subnets, private EC2 web tier, multi-AZ MySQL on RDS, optional CloudTrail, SNS alerts, KMS encryption, and CloudWatch alarms. Everything is parameterized via environment variables or CDK context to keep the repo safe for open distribution and multi-environment use.

## Repository Layout
- `lib/src/main/java/app/Main.java` — CDK app/stack (VPC, EC2, RDS, SNS, CloudTrail, KMS)
- `lib/AWS_REGION` — optional default region file
- `tests/unit/java/...` — CDK assertions
- `tests/integration/java/...` — live AWS validations (require deployed stack + outputs)
- `cdk.json` — CDK entrypoint (uses `gradle run`)
- `build.gradle` / `settings.gradle` — build/test wiring
- `docs/` — cost, security, runbook, troubleshooting
- `diagrams/` — Mermaid architecture and sequence diagrams

## Architecture at a Glance
- VPC `/16` with 2x public + 2x private-with-egress subnets, Internet + NAT gateways
- EC2 web tier (2x t3.medium) in private subnets; ingress limited to configurable office CIDR
- RDS MySQL (t3.micro, multi-AZ, KMS-encrypted) with subnet and parameter groups
- Optional CloudTrail -> KMS-encrypted S3 bucket with lifecycle
- SNS topic for alerts; CloudWatch CPU alarms targeting SNS
- KMS CMK with rotation for RDS/CloudTrail

## Configuration (env vars or CDK context)
- `PROJECT_NAME` / `projectName` (default `webapp`)
- `ENVIRONMENT` / `environment` (default `prod`)
- `ENVIRONMENT_SUFFIX` / `environmentSuffix` (default ``; appended to stack name)
- `AWS_REGION` / `AWS_DEFAULT_REGION` / `region` / file `lib/AWS_REGION` (default `eu-north-1`)
- `OFFICE_CIDR` / `officeCidr` (default `203.0.113.0/24`) — replace with your ranges
- `ALERT_EMAIL` / `alertEmail` (default `devops@company.com`) — replace with monitored list
- `OWNER` / `owner` (default `cloud-team`)
- `COST_CENTER` / `costCenter` (default `DevOps`)
- `enableCloudTrail` (context flag, default `true`)
- `ARTIFACT_BUCKET_NAME` / `artifactBucketName` — optional fixed bucket name for EC2 S3 access policy (objects: `arn:aws:s3:::<name>/*`)
- `EXTRA_EGRESS_PORTS` / `extraEgressPorts` — optional comma/space-separated ports to allow additional egress from web SG (default only 80/443)

## Prerequisites
- Java 17+, Gradle 8+, Node.js 20+ (for CDK CLI)
- AWS credentials with VPC/EC2/RDS/S3/SNS/KMS/CloudTrail permissions
- CDK bootstrap in target account/region

## Deployment (example for dev)
For a detailed walkthrough, refer to the Quick Start section above.
```bash
export ENVIRONMENT_SUFFIX=dev
export AWS_REGION=eu-north-1
export OFFICE_CIDR=198.51.100.0/24
export ALERT_EMAIL=ops@example.com

gradle clean build           # unit tests only
cdk synth --app "gradle run"
cdk deploy --all --require-approval never \
  --context environmentSuffix=$ENVIRONMENT_SUFFIX \
  --context officeCidr=$OFFICE_CIDR \
  --context alertEmail=$ALERT_EMAIL
```

## Testing
See the Testing section above for more details.
- Unit: `gradle test`
- Integration: `gradle integrationTest` (requires deployed stack and `cfn-outputs/flat-outputs.json` from stack outputs)

## Teardown
- `cdk destroy --all --force --context environmentSuffix=$ENVIRONMENT_SUFFIX`
- `gradle clean` to remove build artifacts

## Diagrams
- Architecture: `diagrams/architecture.mmd`
- Sequence (deploy/alert flow): `diagrams/sequence.mmd`

## Cost Estimate (monthly, ₹, eu-north-1 rough)
- NAT Gateways (2x): ~₹7,000–₹8,000
- RDS MySQL t3.micro multi-AZ: ~₹2,000–₹2,500
- EC2 t3.medium (2x): ~₹7,500–₹8,500
- S3/CloudWatch/CloudTrail: ~₹500–₹1,000
- **Total**: ~₹17k–₹20k. Reduce by downsizing EC2/NAT count, disabling CloudTrail in non-prod, or switching to spot/ASG.

## Security Notes
- KMS encryption for RDS and (optional) CloudTrail bucket; public access blocked on S3
- Office ingress and alert email are configurable; defaults are placeholders
- Tags: Project, Environment, Owner, CostCenter, ManagedBy
- Web SG now restricts egress to HTTP/HTTPS only; add ports via `EXTRA_EGRESS_PORTS`/context if workloads need more
- Inline S3 access scoped to project/environment buckets (`<project>-<env>-*`) or a fixed bucket via `ARTIFACT_BUCKET_NAME`
- CloudTrail enabled by default; disable in non-prod if cost-sensitive (set `enableCloudTrail=false`)

## Troubleshooting
- **Gradle build fails**: verify Java 17/Gradle 8 (`gradle --version`)
- **CDK synth needs creds**: set `AWS_PROFILE` or `AWS_ACCESS_KEY_ID`/`AWS_SECRET_ACCESS_KEY` + region
- **Bucket name collision**: adjust `PROJECT_NAME`/`ENVIRONMENT`/`ENVIRONMENT_SUFFIX`
- **Integration tests skipped**: ensure `cfn-outputs/flat-outputs.json` is present after deploy

## 👤 Author

**Rahul Ladumor** - Founder of InfraTales

- 🌐 Portfolio: [rahulladumor.in](https://www.rahulladumor.in)
- ☁️ Blog: [acloudwithrahul.in](https://www.acloudwithrahul.in)
- 💼 GitHub: [@rahulladumor](https://github.com/rahulladumor)
- 🏢 Organization: [InfraTales](https://github.com/InfraTales)
- 📧 Email: rahul.ladumor@infratales.com
- 💬 LinkedIn: [linkedin.com/in/rahulladumor](https://www.linkedin.com/in/rahulladumor)

## 🤝 Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for workflow, testing, and security guidelines.

## 📄 License

This repository is released under the MIT License (see [LICENSE](LICENSE)).

---

<div align="center">

**Built with ❤️ by [InfraTales](https://github.com/InfraTales)**

<a href="https://infratales.com">Website</a> •
<a href="https://infratales.com/projects">Projects</a> •
<a href="https://infratales.com/premium">Premium</a> •
<a href="https://infratales.com/newsletter">Newsletter</a>

</div>
