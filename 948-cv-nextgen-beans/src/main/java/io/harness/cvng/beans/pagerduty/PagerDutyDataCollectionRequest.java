/*
 * Copyright 2021 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Shield 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/06/PolyForm-Shield-1.0.0.txt.
 */

package io.harness.cvng.beans.pagerduty;

import static io.harness.annotations.dev.HarnessTeam.CV;

import io.harness.annotations.dev.OwnedBy;
import io.harness.cvng.beans.DataCollectionRequest;
import io.harness.delegate.beans.connector.pagerduty.PagerDutyConnectorDTO;
import io.harness.delegate.beans.cvng.pagerduty.PagerDutyUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@OwnedBy(CV)
public abstract class PagerDutyDataCollectionRequest extends DataCollectionRequest<PagerDutyConnectorDTO> {
  @Override
  public Map<String, String> collectionHeaders() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", PagerDutyUtils.getAuthorizationHeader(getConnectorConfigDTO()));
    return headers;
  }

  @Override
  public String getBaseUrl() {
    return PagerDutyUtils.getBaseUrl();
  }
}
