# Testing Strategy

## Overview
This project follows a comprehensive testing strategy covering unit, integration, and end-to-end tests.

## Test Levels

### 1. Unit Tests
**Scope:** Individual IaC constructs/modules
**Location:** `tests/unit/`
**Run:** `{{TEST_COMMAND}}`

**Coverage Target:** 80%

**Example:**
- Verify stack synthesis succeeds
- Validate resource properties
- Check conditional logic

### 2. Integration Tests
**Scope:** Deployed infrastructure
**Location:** `tests/integration/`
**Prerequisites:** Deployed stack
**Run:** `{{INTEGRATION_TEST_COMMAND}}`

**Example:**
- Verify VPC exists with correct CIDR
- Check Security Group rules
- Validate RDS encryption

### 3. End-to-End Tests
**Scope:** Full application flow
**Location:** `tests/e2e/`
**Prerequisites:** Deployed + running application

**Example:**
- HTTP request through ALB succeeds
- Database connection works
- CloudWatch alarms trigger correctly

## Running Tests

```bash
# Unit tests (fast, no AWS calls)
{{TEST_COMMAND}}

# Integration tests (requires deployed stack)
{{INTEGRATION_TEST_COMMAND}}

# All tests
npm run test:all  # or equivalent
```

## Coverage Reports

After running tests, view coverage:
```bash
open coverage/index.html  # or equivalent
```

## CI/CD Integration

All tests run automatically in GitHub Actions:
- Unit tests: on every PR
- Integration tests: on merge to main
- E2E tests: scheduled nightly

## Best Practices

1. **Mock External Services:** Use mocks for AWS SDK calls in unit tests
2. **Idempotent Integration Tests:** Tests should be safe to run multiple times
3. **Cleanup:** Always tear down resources after integration tests
4. **Parallel Execution:** Avoid test interdependencies

---

<div align="center">
  <a href="https://infratales.com">InfraTales</a>
</div>
