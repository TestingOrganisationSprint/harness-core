/*
 * Copyright 2022 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Shield 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/06/PolyForm-Shield-1.0.0.txt.
 */

package io.harness.cdng.infra.beans;

import io.harness.annotation.RecasterAlias;
import io.harness.annotations.dev.HarnessTeam;
import io.harness.annotations.dev.OwnedBy;
import io.harness.ng.core.infrastructure.InfrastructureKind;
import io.harness.steps.environment.EnvironmentOutcome;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.TypeAlias;

@Value
@Builder
@JsonTypeName(InfrastructureKind.AZURE_WEB_APP)
@TypeAlias("io.harness.cdng.infra.beans.AzureWebAppInfrastructureOutcome")
@OwnedBy(HarnessTeam.CDP)
@RecasterAlias("io.harness.cdng.infra.beans.AzureWebAppInfrastructureOutcome")
public class AzureWebAppInfrastructureOutcome implements InfrastructureOutcome {
  String connectorRef;
  EnvironmentOutcome environment;
  String infrastructureKey;
  String subscription;
  String resourceGroup;
  String appService;
  String deploymentSlot;
  String targetSlot;

  @Override
  public String getKind() {
    return InfrastructureKind.AZURE_WEB_APP;
  }
}
