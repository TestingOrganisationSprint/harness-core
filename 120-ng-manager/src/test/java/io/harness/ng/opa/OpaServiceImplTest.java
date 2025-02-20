/*
 * Copyright 2022 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Free Trial 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/05/PolyForm-Free-Trial-1.0.0.txt.
 */

package io.harness.ng.opa;

import static io.harness.annotations.dev.HarnessTeam.PL;
import static io.harness.rule.OwnerRule.UJJAWAL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.harness.NgManagerTestBase;
import io.harness.annotations.dev.OwnedBy;
import io.harness.category.element.UnitTests;
import io.harness.connector.ConnectorDTO;
import io.harness.connector.ConnectorInfoDTO;
import io.harness.network.SafeHttpCall;
import io.harness.opaclient.OpaServiceClient;
import io.harness.opaclient.model.OpaEvaluationResponseHolder;
import io.harness.pms.contracts.governance.GovernanceMetadata;
import io.harness.rule.Owner;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import retrofit2.Call;

@OwnedBy(PL)
@RunWith(PowerMockRunner.class)
@Slf4j
@PrepareForTest({SafeHttpCall.class})
public class OpaServiceImplTest extends NgManagerTestBase {
  @Mock OpaServiceClient opaServiceClient;
  private OpaServiceImpl opaService;
  Call<OpaEvaluationResponseHolder> request;

  @Before
  public void setup() throws IllegalAccessException {
    PowerMockito.mockStatic(SafeHttpCall.class);

    opaService = new OpaServiceImpl(opaServiceClient);
    request = mock(Call.class);
  }

  @Test
  @Owner(developers = UJJAWAL)
  @Category(UnitTests.class)
  public void testEvaluate() throws IOException {
    try {
      ConnectorInfoDTO connectorInfoDTO =
          ConnectorInfoDTO.builder().name("name").identifier("id").orgIdentifier("orgId").build();
      ConnectorDTO connectorDTO = ConnectorDTO.builder().connectorInfo(connectorInfoDTO).build();

      OpaEvaluationContext opaEvaluationContext = OpaEvaluationContext.builder().entity(connectorDTO).build();
      when(opaServiceClient.evaluateWithCredentials(
               anyString(), anyString(), anyString(), anyString(), anyString(), any(), anyString(), anyString(), any()))
          .thenReturn(request);

      OpaEvaluationResponseHolder evaluationResponse =
          OpaEvaluationResponseHolder.builder().status("pass").id("id").build();
      when(SafeHttpCall.executeWithExceptions(request)).thenAnswer(invocationOnMock -> evaluationResponse);

      GovernanceMetadata governanceMetadata = opaService.evaluate(
          opaEvaluationContext, "accountId", "orgIdentifier", "projectIdentifier", "identifier", "onsave", "key");

      assertThat(governanceMetadata.getStatus()).isNotBlank();
      assertThat(governanceMetadata.getStatus()).isEqualTo(evaluationResponse.getStatus());
      assertThat(governanceMetadata.getId()).isEqualTo(evaluationResponse.getId());

    } catch (Exception e) {
      assertThat(e).isNull();
    }
  }

  @Test
  @Owner(developers = UJJAWAL)
  @Category(UnitTests.class)
  public void testEvaluate2() {
    try {
      ConnectorInfoDTO connectorInfoDTO =
          ConnectorInfoDTO.builder().name("name").identifier("id").orgIdentifier("orgId").build();
      ConnectorDTO connectorDTO = ConnectorDTO.builder().connectorInfo(connectorInfoDTO).build();

      OpaEvaluationContext opaEvaluationContext = OpaEvaluationContext.builder().entity(connectorDTO).build();
      when(opaServiceClient.evaluateWithCredentials(
               anyString(), anyString(), anyString(), anyString(), anyString(), any(), anyString(), anyString(), any()))
          .thenReturn(request);

      OpaEvaluationResponseHolder evaluationResponse =
          OpaEvaluationResponseHolder.builder().status("error").id("id").build();
      when(SafeHttpCall.executeWithExceptions(request)).thenAnswer(invocationOnMock -> evaluationResponse);

      GovernanceMetadata governanceMetadata = opaService.evaluate(
          opaEvaluationContext, "accountId", "orgIdentifier", "projectIdentifier", "identifier", "onsave", "key");
      assertThat(governanceMetadata.getStatus()).isNotBlank();
      assertThat(governanceMetadata.getStatus()).isEqualTo(evaluationResponse.getStatus());
      assertThat(governanceMetadata.getStatus()).isEqualTo("error");

      assertThat(governanceMetadata.getId()).isEqualTo(evaluationResponse.getId());

    } catch (Exception e) {
      assertThat(e).isNull();
    }
  }

  @Test
  @Owner(developers = UJJAWAL)
  @Category(UnitTests.class)
  public void testEvaluate3() {
    try {
      ConnectorInfoDTO connectorInfoDTO =
          ConnectorInfoDTO.builder().name("name").identifier("id").orgIdentifier("orgId").build();
      ConnectorDTO connectorDTO = ConnectorDTO.builder().connectorInfo(connectorInfoDTO).build();

      OpaEvaluationContext opaEvaluationContext = OpaEvaluationContext.builder().entity(connectorDTO).build();
      when(opaServiceClient.evaluateWithCredentials(
               anyString(), anyString(), anyString(), anyString(), anyString(), any(), anyString(), anyString(), any()))
          .thenReturn(request);

      OpaEvaluationResponseHolder evaluationResponse =
          OpaEvaluationResponseHolder.builder().status("error").id("id").build();
      when(SafeHttpCall.executeWithExceptions(request)).thenAnswer(invocationOnMock -> null);

      GovernanceMetadata governanceMetadata = opaService.evaluate(
          opaEvaluationContext, "accountId", "orgIdentifier", "projectIdentifier", "identifier", "onsave", "key");
      assertThat(governanceMetadata.getStatus()).isNotBlank();
      assertThat(governanceMetadata.getStatus()).isEqualTo(evaluationResponse.getStatus());
      assertThat(governanceMetadata.getStatus()).isEqualTo("error");

      assertThat(governanceMetadata.getId()).isEqualTo(evaluationResponse.getId());

    } catch (Exception e) {
      assertThat(e).isNotNull();
    }
  }

  @Test
  @Owner(developers = UJJAWAL)
  @Category(UnitTests.class)
  public void testEvaluate4() {
    try {
      when(opaServiceClient.evaluateWithCredentials(
               anyString(), anyString(), anyString(), anyString(), anyString(), any(), anyString(), anyString(), any()))
          .thenReturn(request);

      OpaEvaluationResponseHolder evaluationResponse =
          OpaEvaluationResponseHolder.builder().status("error").id("id").build();
      when(SafeHttpCall.executeWithExceptions(request)).thenAnswer(invocationOnMock -> evaluationResponse);

      GovernanceMetadata governanceMetadata =
          opaService.evaluate(null, "accountId", "orgIdentifier", "projectIdentifier", "identifier", "onsave", "key");
      assertThat(governanceMetadata.getStatus()).isNotBlank();
      assertThat(governanceMetadata.getStatus()).isEqualTo(evaluationResponse.getStatus());
      assertThat(governanceMetadata.getStatus()).isEqualTo("error");

      assertThat(governanceMetadata.getId()).isEqualTo(evaluationResponse.getId());

    } catch (Exception e) {
      assertThat(e).isNotNull();
    }
  }

  @Test
  @Owner(developers = UJJAWAL)
  @Category(UnitTests.class)
  public void testEvaluate5() {
    try {
      ConnectorInfoDTO connectorInfoDTO =
          ConnectorInfoDTO.builder().name("name").identifier("id").orgIdentifier("orgId").build();
      ConnectorDTO connectorDTO = ConnectorDTO.builder().connectorInfo(connectorInfoDTO).build();

      OpaEvaluationContext opaEvaluationContext = OpaEvaluationContext.builder().entity(connectorDTO).build();
      when(opaServiceClient.evaluateWithCredentials(
               anyString(), anyString(), anyString(), anyString(), anyString(), any(), anyString(), anyString(), any()))
          .thenReturn(request);

      OpaEvaluationResponseHolder evaluationResponse =
          OpaEvaluationResponseHolder.builder().status("pass").id("id").build();
      when(SafeHttpCall.executeWithExceptions(request)).thenAnswer(invocationOnMock -> evaluationResponse);

      GovernanceMetadata governanceMetadata = opaService.evaluate(
          opaEvaluationContext, "accountId", "orgIdentifier", "projectIdentifier", "identifier", "onsave", "key");
      assertThat(governanceMetadata.getStatus()).isNotBlank();
      assertThat(governanceMetadata.getStatus()).isEqualTo(evaluationResponse.getStatus());
      assertThat(governanceMetadata.getStatus()).isEqualTo("pass");

      assertThat(governanceMetadata.getId()).isEqualTo(evaluationResponse.getId());
      assertThat(evaluationResponse.getId()).isEqualTo(connectorInfoDTO.getIdentifier());

    } catch (Exception e) {
      assertThat(e).isNotNull();
    }
  }
}
