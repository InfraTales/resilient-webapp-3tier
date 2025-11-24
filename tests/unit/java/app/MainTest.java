package app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.assertions.Template;

/**
 * Unit tests for the Main CDK application.
 * 
 * These tests verify the basic structure and configuration of the TapStack
 * without requiring actual AWS resources to be created.
 */
public class MainTest {

  /**
   * Test that the TapStack can be instantiated successfully with default
   * properties.
   */
  @Test
  public void testStackCreation() {
    App app = new App();
    TapStack stack = new TapStack(app, "TestStack", StackProps.builder()
        .env(Environment.builder()
            .region("us-east-2")
            .build())
        .build());

    // Verify stack was created
    assertThat(stack).isNotNull();
    assertThat(stack.getStackName()).isEqualTo("TestStack");
  }

  /**
   * Test that the TapStack can be created with minimal configuration.
   */
  @Test
  public void testMinimalStackCreation() {
    App app = new App();
    TapStack stack = new TapStack(app, "TestStack", StackProps.builder().build());

    // Verify stack was created
    assertThat(stack).isNotNull();
    assertThat(stack.getStackName()).isEqualTo("TestStack");
  }

  /**
   * Test that the TapStack synthesizes without errors.
   */
  @Test
  public void testStackSynthesis() {
    App app = new App();
    TapStack stack = new TapStack(app, "TestStack", StackProps.builder()
        .env(Environment.builder()
            .region("us-east-2")
            .build())
        .build());

    // Create template from the stack
    Template template = Template.fromStack(stack);

    // Verify template can be created (basic synthesis test)
    assertThat(template).isNotNull();
  }

  /**
   * Test that the TapStack creates the expected AWS resources.
   */
  @Test
  public void testResourceCreation() {
    App app = new App();
    TapStack stack = new TapStack(app, "TestStack", StackProps.builder()
        .env(Environment.builder()
            .region("us-east-2")
            .build())
        .build());

    Template template = Template.fromStack(stack);

    // Verify that key resources are created (CloudTrail is disabled by default)
    template.hasResourceProperties("AWS::EC2::VPC", new java.util.HashMap<>());
    template.hasResourceProperties("AWS::KMS::Key", new java.util.HashMap<>());
    // Note: S3 bucket and CloudTrail are not created when enableCloudTrail is false
    template.hasResourceProperties("AWS::SNS::Topic", new java.util.HashMap<>());
    template.hasResourceProperties("AWS::EC2::Instance", new java.util.HashMap<>());
    template.hasResourceProperties("AWS::RDS::DBInstance", new java.util.HashMap<>());
  }

  /**
   * Test that the TapStack creates CloudTrail resources when enabled.
   */
  @Test
  public void testCloudTrailResourceCreation() {
    App app = new App();

    // Set context to enable CloudTrail
    app.getNode().setContext("enableCloudTrail", true);

    TapStack stack = new TapStack(app, "TestStackCloudTrail", StackProps.builder()
        .env(Environment.builder()
            .region("us-east-2")
            .build())
        .build());

    Template template = Template.fromStack(stack);

    // Verify that CloudTrail resources are created when enabled
    template.hasResourceProperties("AWS::EC2::VPC", new java.util.HashMap<>());
    template.hasResourceProperties("AWS::KMS::Key", new java.util.HashMap<>());
    template.hasResourceProperties("AWS::S3::Bucket", new java.util.HashMap<>());
    template.hasResourceProperties("AWS::CloudTrail::Trail", new java.util.HashMap<>());
    template.hasResourceProperties("AWS::SNS::Topic", new java.util.HashMap<>());
    template.hasResourceProperties("AWS::EC2::Instance", new java.util.HashMap<>());
    template.hasResourceProperties("AWS::RDS::DBInstance", new java.util.HashMap<>());
  }
}