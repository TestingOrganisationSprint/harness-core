/*
 * Copyright 2021 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Free Trial 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/05/PolyForm-Free-Trial-1.0.0.txt.
 */

package io.harness.connector.heartbeat;

import static io.harness.data.structure.EmptyPredicate.isNotEmpty;

import io.harness.beans.DecryptableEntity;
import io.harness.connector.ConnectorInfoDTO;
import io.harness.connector.helper.EncryptionHelper;
import io.harness.delegate.beans.connector.ConnectorValidationParams;
import io.harness.delegate.beans.connector.artifactoryconnector.ArtifactoryConnectorDTO;
import io.harness.delegate.beans.connector.artifactoryconnector.ArtifactoryValidationParams;
import io.harness.security.encryption.EncryptedDataDetail;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;

@Singleton
public class ArtifactoryValidationParamsProvider implements ConnectorValidationParamsProvider {
  @Inject EncryptionHelper encryptionHelper;

  @Override
  public ConnectorValidationParams getConnectorValidationParams(ConnectorInfoDTO connectorInfoDTO, String connectorName,
      String accountIdentifier, String orgIdentifier, String projectIdentifier) {
    final ArtifactoryConnectorDTO connectorConfig = (ArtifactoryConnectorDTO) connectorInfoDTO.getConnectorConfig();
    DecryptableEntity decryptableEntity = null;
    if (isNotEmpty(connectorConfig.getDecryptableEntities())) {
      decryptableEntity = connectorConfig.getDecryptableEntities().get(0);
    }
    final List<EncryptedDataDetail> encryptionDetail =
        encryptionHelper.getEncryptionDetail(decryptableEntity, accountIdentifier, orgIdentifier, projectIdentifier);
    return ArtifactoryValidationParams.builder()
        .artifactoryConnectorDTO(connectorConfig)
        .connectorName(connectorName)
        .encryptedDataDetails(encryptionDetail)
        .build();
  }
}
