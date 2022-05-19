/*
 * Copyright 2021 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Free Trial 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/05/PolyForm-Free-Trial-1.0.0.txt.
 */

package io.harness.service.infrastructuremapping;

import static io.harness.rule.OwnerRule.PIYUSH_BHUWALKA;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.harness.InstancesTestBase;
import io.harness.category.element.UnitTests;
import io.harness.dtos.InfrastructureMappingDTO;
import io.harness.entities.InfrastructureMapping;
import io.harness.repositories.infrastructuremapping.InfrastructureMappingRepository;
import io.harness.rule.Owner;

import java.util.Optional;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class InfrastructureMappingServiceImplTest extends InstancesTestBase {
  private static final String ACCOUNT_IDENTIFIER = "account_identifier";
  private static final String ORG_IDENTIFIER = "org_identifier";
  private static final String PROJECT_IDENTIFIER = "project_identifier";
  private static final String SERVICE_IDENTIFIER = "service_identifier";
  private static final String ENVIRONMENT_IDENTIFIER = "environment_identifier";
  private static final String INFRASTRUCTURE_IDENTIFIER = "infrastructure_identifier";
  private static final String INFRASTRUCTURE_KIND = "infrastructure_kind";
  private static final String CONNECTOR_REF = "connector_ref";
  @Mock InfrastructureMappingRepository infrastructureMappingRepository;
  @InjectMocks InfrastructureMappingServiceImpl infrastructureMappingService;

  @Test
  @Owner(developers = PIYUSH_BHUWALKA)
  @Category(UnitTests.class)
  public void getByInfrastructureMappingIdTest() {
    InfrastructureMapping infrastructureMapping = InfrastructureMapping.builder()
                                                      .orgIdentifier(ORG_IDENTIFIER)
                                                      .accountIdentifier(ACCOUNT_IDENTIFIER)
                                                      .projectIdentifier(PROJECT_IDENTIFIER)
                                                      .serviceId(SERVICE_IDENTIFIER)
                                                      .envId(ENVIRONMENT_IDENTIFIER)
                                                      .infrastructureKey(INFRASTRUCTURE_IDENTIFIER)
                                                      .infrastructureKind(INFRASTRUCTURE_KIND)
                                                      .connectorRef(CONNECTOR_REF)
                                                      .build();
    InfrastructureMappingDTO infrastructureMappingDTO = InfrastructureMappingDTO.builder()
                                                            .orgIdentifier(ORG_IDENTIFIER)
                                                            .accountIdentifier(ACCOUNT_IDENTIFIER)
                                                            .projectIdentifier(PROJECT_IDENTIFIER)
                                                            .serviceIdentifier(SERVICE_IDENTIFIER)
                                                            .envIdentifier(ENVIRONMENT_IDENTIFIER)
                                                            .infrastructureKey(INFRASTRUCTURE_IDENTIFIER)
                                                            .infrastructureKind(INFRASTRUCTURE_KIND)
                                                            .connectorRef(CONNECTOR_REF)
                                                            .build();
    when(infrastructureMappingRepository.findById(INFRASTRUCTURE_IDENTIFIER))
        .thenReturn(Optional.of(infrastructureMapping));
    assertThat(infrastructureMappingService.getByInfrastructureMappingId(INFRASTRUCTURE_IDENTIFIER))
        .isEqualTo(Optional.of(infrastructureMappingDTO));
  }

  @Test
  @Owner(developers = PIYUSH_BHUWALKA)
  @Category(UnitTests.class)
  public void createNewOrReturnExistingInfrastructureMappingTest() {
    InfrastructureMapping infrastructureMapping = InfrastructureMapping.builder()
                                                      .orgIdentifier(ORG_IDENTIFIER)
                                                      .accountIdentifier(ACCOUNT_IDENTIFIER)
                                                      .projectIdentifier(PROJECT_IDENTIFIER)
                                                      .serviceId(SERVICE_IDENTIFIER)
                                                      .envId(ENVIRONMENT_IDENTIFIER)
                                                      .infrastructureKey(INFRASTRUCTURE_IDENTIFIER)
                                                      .infrastructureKind(INFRASTRUCTURE_KIND)
                                                      .connectorRef(CONNECTOR_REF)
                                                      .build();
    InfrastructureMappingDTO infrastructureMappingDTO = InfrastructureMappingDTO.builder()
                                                            .orgIdentifier(ORG_IDENTIFIER)
                                                            .accountIdentifier(ACCOUNT_IDENTIFIER)
                                                            .projectIdentifier(PROJECT_IDENTIFIER)
                                                            .serviceIdentifier(SERVICE_IDENTIFIER)
                                                            .envIdentifier(ENVIRONMENT_IDENTIFIER)
                                                            .infrastructureKey(INFRASTRUCTURE_IDENTIFIER)
                                                            .infrastructureKind(INFRASTRUCTURE_KIND)
                                                            .connectorRef(CONNECTOR_REF)
                                                            .build();
    when(infrastructureMappingRepository.save(infrastructureMapping)).thenReturn(infrastructureMapping);
    assertThat(infrastructureMappingService.createNewOrReturnExistingInfrastructureMapping(infrastructureMappingDTO))
        .isEqualTo(Optional.of(infrastructureMappingDTO));
  }

  //    @Test
  //    @Owner(developers = PIYUSH_BHUWALKA)
  //    @Category(UnitTests.class)
  //    public void createNewOrReturnExistingInfrastructureMappingTestWhenMappingRepoThrowsError() {
  //
  //        InfrastructureMapping infrastructureMapping = InfrastructureMapping.builder()
  //                .orgIdentifier(ORG_IDENTIFIER).accountIdentifier(ACCOUNT_IDENTIFIER)
  //                .projectIdentifier(PROJECT_IDENTIFIER).serviceId(SERVICE_IDENTIFIER)
  //                .envId(ENVIRONMENT_IDENTIFIER).infrastructureKey(INFRASTRUCTURE_IDENTIFIER)
  //                .infrastructureKind(INFRASTRUCTURE_KIND).connectorRef(CONNECTOR_REF).build();
  //        InfrastructureMappingDTO infrastructureMappingDTO = InfrastructureMappingDTO.builder()
  //                .orgIdentifier(ORG_IDENTIFIER).accountIdentifier(ACCOUNT_IDENTIFIER)
  //                .projectIdentifier(PROJECT_IDENTIFIER).serviceIdentifier(SERVICE_IDENTIFIER)
  //                .envIdentifier(ENVIRONMENT_IDENTIFIER).infrastructureKey(INFRASTRUCTURE_IDENTIFIER)
  //                .infrastructureKind(INFRASTRUCTURE_KIND).connectorRef(CONNECTOR_REF).build();
  //        when(infrastructureMappingRepository.save(infrastructureMapping)).thenThrow(new DuplicateKeyException(new
  //        BsonDocument(), null, null));
  //        when(infrastructureMappingRepository.findByInfrastructureKey(INFRASTRUCTURE_IDENTIFIER)).thenReturn(Optional.of(infrastructureMapping));
  //        assertThat(infrastructureMappingService.createNewOrReturnExistingInfrastructureMapping(infrastructureMappingDTO)).isEqualTo(Optional.of(infrastructureMappingDTO));
  //    }
}
