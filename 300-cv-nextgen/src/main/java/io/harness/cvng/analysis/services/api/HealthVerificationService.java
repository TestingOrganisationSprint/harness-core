/*
 * Copyright 2020 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Free Trial 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/05/PolyForm-Free-Trial-1.0.0.txt.
 */

package io.harness.cvng.analysis.services.api;

import io.harness.cvng.analysis.entities.HealthVerificationPeriod;
import io.harness.cvng.statemachine.beans.AnalysisStatus;

import java.time.Instant;

public interface HealthVerificationService {
  Instant aggregateActivityAnalysis(String verificationTaskId, Instant startTime, Instant endTime,
      Instant latestTimeOfAnalysis, HealthVerificationPeriod healthVerificationPeriod);
  void updateProgress(
      String verificationTaskId, Instant latestTimeOfAnalysis, AnalysisStatus status, boolean isFinalState);
}
