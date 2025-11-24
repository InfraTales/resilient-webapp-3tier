# Contributing

Thank you for improving this InfraTales reference. Please follow these guidelines to keep the project production-ready and safe.

## How to contribute
1. Fork and branch from `main`.
2. Make focused changes; avoid destructive infra alterations without discussion.
3. Keep configuration-driven behavior (env vars / CDK context) and do not introduce hardcoded secrets or CIDRs.
4. Update docs/diagrams/tests when behavior changes.
5. Open a PR with a clear description and testing evidence.

## Development workflow
- Install: Java 17+, Gradle 8+, Node.js 20+ with CDK CLI, AWS CLI configured.
- Build and unit tests: `gradle clean test`
- Integration tests (requires deployed stack and outputs): `gradle integrationTest`
- Synth: `cdk synth --app "gradle run"`
- Deploy (example dev): `cdk deploy --all --require-approval never --context environmentSuffix=dev`
- Destroy: `cdk destroy --all --force --context environmentSuffix=dev`

## Coding standards
- Java 17, CDK v2; keep resources encrypted, tagged (Project, Environment, Owner, CostCenter, ManagedBy).
- Maintain least privilege: scope security groups, IAM, and S3 ARNs to known resources.
- Keep defaults safe for public use: no real emails, CIDRs, or secrets checked in.

## Docs and diagrams
- Update `README.md`, `docs/`, and `diagrams/` to reflect changes.
- Mermaid files (`.mmd`) should match the actual IaC resources and flows.

## Security
- Do not commit credentials, secrets, or private endpoints.
- Prefer parameterization over hardcoding; keep CloudTrail enabled unless explicitly justified.
- If you find a security issue, open a private disclosure if possible; otherwise, file an issue without sensitive details.

## CI expectations
- PRs should include passing unit tests and a clean `cdk synth`.
- Integration tests may run against a test account; clearly mark if skipped and why.
