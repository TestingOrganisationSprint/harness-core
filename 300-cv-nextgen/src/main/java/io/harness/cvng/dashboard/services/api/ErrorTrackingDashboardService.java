package io.harness.cvng.dashboard.services.api;

import io.harness.cvng.analysis.beans.LiveMonitoringLogAnalysisClusterDTO;
import io.harness.cvng.core.beans.params.MonitoredServiceParams;
import io.harness.cvng.core.beans.params.PageParams;
import io.harness.cvng.core.beans.params.TimeRangeParams;
import io.harness.cvng.core.beans.params.filterParams.LiveMonitoringLogAnalysisFilter;
import io.harness.cvng.dashboard.beans.AnalyzedLogDataDTO;
import io.harness.ng.beans.PageResponse;

import java.util.List;

public interface ErrorTrackingDashboardService {
  PageResponse<AnalyzedLogDataDTO> getAllLogsData(MonitoredServiceParams monitoredServiceParams,
      TimeRangeParams timeRangeParams, LiveMonitoringLogAnalysisFilter liveMonitoringLogAnalysisFilter,
      PageParams pageParams);
  List<LiveMonitoringLogAnalysisClusterDTO> getLogAnalysisClusters(MonitoredServiceParams monitoredServiceParams,
      TimeRangeParams timeRangeParams, LiveMonitoringLogAnalysisFilter liveMonitoringLogAnalysisFilter);
}
