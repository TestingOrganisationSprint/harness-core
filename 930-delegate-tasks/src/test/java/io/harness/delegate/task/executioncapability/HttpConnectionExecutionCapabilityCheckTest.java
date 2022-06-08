package io.harness.delegate.task.executioncapability;

import static io.harness.rule.OwnerRule.ABHISHEK;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import io.harness.beans.KeyValuePair;
import io.harness.capability.CapabilityParameters;
import io.harness.capability.CapabilitySubjectPermission;
import io.harness.capability.HttpConnectionParameters;
import io.harness.category.element.UnitTests;
import io.harness.delegate.beans.executioncapability.CapabilityResponse;
import io.harness.delegate.beans.executioncapability.HttpConnectionExecutionCapability;
import io.harness.network.Http;
import io.harness.rule.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HttpConnectionExecutionCapabilityCheckTest {
  @InjectMocks HttpConnectionExecutionCapabilityCheck httpConnectionExecutionCapabilityCheck;
  @Mock HttpConnectionExecutionCapability httpConnectionExecutionCapability;
  @Mock CapabilityParameters parameters;

  @Test
  @Owner(developers = ABHISHEK)
  @Category(UnitTests.class)
  public void performCapabilityCheck_NG_False_Headers_Null_IgnoreRedirect_Valid() {
    when(httpConnectionExecutionCapability.fetchConnectableUrl()).thenReturn("abc");
    when(httpConnectionExecutionCapability.getHeaders()).thenReturn(null);
    when(httpConnectionExecutionCapability.isIgnoreRedirect()).thenReturn(true);

    MockedStatic<StringUtils> mockStringUtils = mockStatic(StringUtils.class);
    mockStringUtils.when(() -> StringUtils.isNotBlank(anyString())).thenReturn(false);

    MockedStatic<Http> mockHttp = mockStatic(Http.class);
    mockHttp
        .when(()
                  -> Http.connectableHttpUrlWithoutFollowingRedirect(
                      httpConnectionExecutionCapability.fetchConnectableUrl()))
        .thenReturn(true);

    CapabilityResponse response =
        httpConnectionExecutionCapabilityCheck.performCapabilityCheck(httpConnectionExecutionCapability);

    mockStringUtils.close();
    mockHttp.close();

    assertThat(response.isValidated()).isEqualTo(true);
  }

  @Test
  @Owner(developers = ABHISHEK)
  @Category(UnitTests.class)
  public void performCapabilityCheck_NG_False_Headers_Null_IgnoreRedirect_NotValid() {
    when(httpConnectionExecutionCapability.fetchConnectableUrl()).thenReturn("abc");
    when(httpConnectionExecutionCapability.getHeaders()).thenReturn(null);
    when(httpConnectionExecutionCapability.isIgnoreRedirect()).thenReturn(true);

    MockedStatic<StringUtils> mockStringUtils = mockStatic(StringUtils.class);
    mockStringUtils.when(() -> StringUtils.isNotBlank(anyString())).thenReturn(false);

    MockedStatic<Http> mockHttp = mockStatic(Http.class);
    mockHttp
        .when(()
                  -> Http.connectableHttpUrlWithoutFollowingRedirect(
                      httpConnectionExecutionCapability.fetchConnectableUrl()))
        .thenReturn(false);

    CapabilityResponse response =
        httpConnectionExecutionCapabilityCheck.performCapabilityCheck(httpConnectionExecutionCapability);

    mockStringUtils.close();
    mockHttp.close();

    assertThat(response.isValidated()).isEqualTo(false);
  }

  @Test
  @Owner(developers = ABHISHEK)
  @Category(UnitTests.class)
  public void performCapabilityCheck_NG_False_Headers_Null_Redirect_Valid() {
    when(httpConnectionExecutionCapability.fetchConnectableUrl()).thenReturn("abc");
    when(httpConnectionExecutionCapability.getHeaders()).thenReturn(null);
    when(httpConnectionExecutionCapability.isIgnoreRedirect()).thenReturn(false);

    MockedStatic<StringUtils> mockStringUtils = mockStatic(StringUtils.class);
    mockStringUtils.when(() -> StringUtils.isNotBlank(anyString())).thenReturn(false);

    MockedStatic<Http> mockHttp = mockStatic(Http.class);
    mockHttp.when(() -> Http.connectableHttpUrl(httpConnectionExecutionCapability.fetchConnectableUrl()))
        .thenReturn(true);

    CapabilityResponse response =
        httpConnectionExecutionCapabilityCheck.performCapabilityCheck(httpConnectionExecutionCapability);

    mockStringUtils.close();
    mockHttp.close();

    assertThat(response.isValidated()).isEqualTo(true);
  }

  @Test
  @Owner(developers = ABHISHEK)
  @Category(UnitTests.class)
  public void performCapabilityCheck_NG_False_Headers_Null_Redirect_NotValid() {
    when(httpConnectionExecutionCapability.fetchConnectableUrl()).thenReturn("abc");
    when(httpConnectionExecutionCapability.getHeaders()).thenReturn(null);
    when(httpConnectionExecutionCapability.isIgnoreRedirect()).thenReturn(false);

    MockedStatic<StringUtils> mockStringUtils = mockStatic(StringUtils.class);
    mockStringUtils.when(() -> StringUtils.isNotBlank(anyString())).thenReturn(false);

    MockedStatic<Http> mockHttp = mockStatic(Http.class);
    mockHttp.when(() -> Http.connectableHttpUrl(httpConnectionExecutionCapability.fetchConnectableUrl()))
        .thenReturn(false);

    CapabilityResponse response =
        httpConnectionExecutionCapabilityCheck.performCapabilityCheck(httpConnectionExecutionCapability);

    mockStringUtils.close();
    mockHttp.close();

    assertThat(response.isValidated()).isEqualTo(false);
  }

  @Test
  @Owner(developers = ABHISHEK)
  @Category(UnitTests.class)
  public void performCapabilityCheck_NG_False_Headers_Valid() {
    List<KeyValuePair> temp = new ArrayList<>();

    when(httpConnectionExecutionCapability.fetchConnectableUrl()).thenReturn("abc");
    when(httpConnectionExecutionCapability.getHeaders()).thenReturn(temp);

    MockedStatic<StringUtils> mockStringUtils = mockStatic(StringUtils.class);
    mockStringUtils.when(() -> StringUtils.isNotBlank(anyString())).thenReturn(false);

    MockedStatic<Http> mockHttp = mockStatic(Http.class);
    mockHttp
        .when(()
                  -> Http.connectableHttpUrlWithHeaders(httpConnectionExecutionCapability.fetchConnectableUrl(),
                      httpConnectionExecutionCapability.getHeaders()))
        .thenReturn(true);

    CapabilityResponse response =
        httpConnectionExecutionCapabilityCheck.performCapabilityCheck(httpConnectionExecutionCapability);

    mockStringUtils.close();
    mockHttp.close();

    assertThat(response.isValidated()).isEqualTo(true);
  }

  @Test
  @Owner(developers = ABHISHEK)
  @Category(UnitTests.class)
  public void performCapabilityCheck_NG_False_Headers_NotValid() {
    List<KeyValuePair> temp = new ArrayList<>();

    when(httpConnectionExecutionCapability.fetchConnectableUrl()).thenReturn("abc");
    when(httpConnectionExecutionCapability.getHeaders()).thenReturn(temp);

    MockedStatic<StringUtils> mockStringUtils = mockStatic(StringUtils.class);
    mockStringUtils.when(() -> StringUtils.isNotBlank(anyString())).thenReturn(false);

    MockedStatic<Http> mockHttp = mockStatic(Http.class);
    mockHttp
        .when(()
                  -> Http.connectableHttpUrlWithHeaders(httpConnectionExecutionCapability.fetchConnectableUrl(),
                      httpConnectionExecutionCapability.getHeaders()))
        .thenReturn(false);

    CapabilityResponse response =
        httpConnectionExecutionCapabilityCheck.performCapabilityCheck(httpConnectionExecutionCapability);

    mockStringUtils.close();
    mockHttp.close();

    assertThat(response.isValidated()).isEqualTo(false);
  }

  @Test
  @Owner(developers = ABHISHEK)
  @Category(UnitTests.class)
  public void performCapabilityCheck_NG_Headers_Valid() {
    List<KeyValuePair> temp = new ArrayList<>();

    when(httpConnectionExecutionCapability.fetchConnectableUrl()).thenReturn("abc");
    when(httpConnectionExecutionCapability.getHeaders()).thenReturn(temp);

    MockedStatic<Http> mockHttp = mockStatic(Http.class);
    mockHttp
        .when(()
                  -> Http.connectableHttpUrlWithHeaders(httpConnectionExecutionCapability.fetchConnectableUrl(),
                      httpConnectionExecutionCapability.getHeaders()))
        .thenReturn(true);
    mockHttp
        .when(()
                  -> Http.connectableHttpUrlWithoutFollowingRedirect(
                      httpConnectionExecutionCapability.fetchConnectableUrl()))
        .thenReturn(true);

    CapabilityResponse response =
        httpConnectionExecutionCapabilityCheck.performCapabilityCheck(httpConnectionExecutionCapability);

    mockHttp.close();

    assertThat(response.isValidated()).isEqualTo(true);
  }

  @Test
  @Owner(developers = ABHISHEK)
  @Category(UnitTests.class)
  public void performCapabilityCheck_NG_Headers_NotValid() {
    List<KeyValuePair> temp = new ArrayList<>();

    when(httpConnectionExecutionCapability.fetchConnectableUrl()).thenReturn("abc");
    when(httpConnectionExecutionCapability.getHeaders()).thenReturn(temp);

    MockedStatic<Http> mockHttp = mockStatic(Http.class);
    mockHttp
        .when(()
                  -> Http.connectableHttpUrlWithHeaders(httpConnectionExecutionCapability.fetchConnectableUrl(),
                      httpConnectionExecutionCapability.getHeaders()))
        .thenReturn(false);
    mockHttp
        .when(()
                  -> Http.connectableHttpUrlWithoutFollowingRedirect(
                      httpConnectionExecutionCapability.fetchConnectableUrl()))
        .thenReturn(false);

    CapabilityResponse response =
        httpConnectionExecutionCapabilityCheck.performCapabilityCheck(httpConnectionExecutionCapability);

    mockHttp.close();

    assertThat(response.isValidated()).isEqualTo(false);
  }

  @Test
  @Owner(developers = ABHISHEK)
  @Category(UnitTests.class)
  public void performCapabilityCheckWithProto_CapabilityCase_Null() {
    when(parameters.getCapabilityCase()).thenReturn(null);

    CapabilitySubjectPermission result =
        httpConnectionExecutionCapabilityCheck.performCapabilityCheckWithProto(parameters);

    assertThat(result.getPermissionResult()).isEqualTo(CapabilitySubjectPermission.PermissionResult.DENIED);
  }

  @Test
  @Owner(developers = ABHISHEK)
  @Category(UnitTests.class)
  public void performCapabilityCheckWithProto_HeaderList_Null_Invalid() {
    HttpConnectionParameters temp = HttpConnectionParameters.newBuilder().getDefaultInstanceForType();
    when(parameters.getCapabilityCase()).thenReturn(CapabilityParameters.CapabilityCase.HTTP_CONNECTION_PARAMETERS);
    when(parameters.getHttpConnectionParameters()).thenReturn(temp);

    MockedStatic<Http> mockHttp = mockStatic(Http.class);
    mockHttp.when(() -> Http.connectableHttpUrl(temp.getUrl())).thenReturn(false);

    CapabilitySubjectPermission result =
        httpConnectionExecutionCapabilityCheck.performCapabilityCheckWithProto(parameters);

    mockHttp.close();

    assertThat(result.getPermissionResult()).isEqualTo(CapabilitySubjectPermission.PermissionResult.DENIED);
  }

  @Test
  @Owner(developers = ABHISHEK)
  @Category(UnitTests.class)
  public void performCapabilityCheckWithProto_HeaderList_Null_Valid() {
    HttpConnectionParameters temp = HttpConnectionParameters.newBuilder().getDefaultInstanceForType();
    when(parameters.getCapabilityCase()).thenReturn(CapabilityParameters.CapabilityCase.HTTP_CONNECTION_PARAMETERS);
    when(parameters.getHttpConnectionParameters()).thenReturn(temp);

    MockedStatic<Http> mockHttp = mockStatic(Http.class);
    mockHttp.when(() -> Http.connectableHttpUrl(temp.getUrl())).thenReturn(true);

    CapabilitySubjectPermission result =
        httpConnectionExecutionCapabilityCheck.performCapabilityCheckWithProto(parameters);

    mockHttp.close();

    assertThat(result.getPermissionResult()).isEqualTo(CapabilitySubjectPermission.PermissionResult.ALLOWED);
  }

  @Test
  @Owner(developers = ABHISHEK)
  @Category(UnitTests.class)
  public void performCapabilityCheckWithProto_HeaderList_Invalid() {
    HttpConnectionParameters.Header header =
        HttpConnectionParameters.Header.newBuilder().setKey("first").setValue("firstValue").build();

    HttpConnectionParameters temp = HttpConnectionParameters.newBuilder().addHeaders(header).build();

    when(parameters.getCapabilityCase()).thenReturn(CapabilityParameters.CapabilityCase.HTTP_CONNECTION_PARAMETERS);
    when(parameters.getHttpConnectionParameters()).thenReturn(temp);

    MockedStatic<Http> mockHttp = mockStatic(Http.class);
    mockHttp
        .when(()
                  -> Http.connectableHttpUrlWithHeaders(temp.getUrl(),
                      temp.getHeadersList()
                          .stream()
                          .map(entry -> KeyValuePair.builder().key(entry.getKey()).value(entry.getValue()).build())
                          .collect(Collectors.toList())))
        .thenReturn(false);

    CapabilitySubjectPermission result =
        httpConnectionExecutionCapabilityCheck.performCapabilityCheckWithProto(parameters);

    mockHttp.close();

    assertThat(result.getPermissionResult()).isEqualTo(CapabilitySubjectPermission.PermissionResult.DENIED);
  }

  @Test
  @Owner(developers = ABHISHEK)
  @Category(UnitTests.class)
  public void performCapabilityCheckWithProto_HeaderList_Valid() {
    HttpConnectionParameters.Header header =
        HttpConnectionParameters.Header.newBuilder().setKey("first").setValue("firstValue").build();

    HttpConnectionParameters temp = HttpConnectionParameters.newBuilder().addHeaders(header).build();
    when(parameters.getCapabilityCase()).thenReturn(CapabilityParameters.CapabilityCase.HTTP_CONNECTION_PARAMETERS);
    when(parameters.getHttpConnectionParameters()).thenReturn(temp);

    MockedStatic<Http> mockHttp = mockStatic(Http.class);

    mockHttp
        .when(()
                  -> Http.connectableHttpUrlWithHeaders(temp.getUrl(),
                      temp.getHeadersList()
                          .stream()
                          .map(entry -> KeyValuePair.builder().key(entry.getKey()).value(entry.getValue()).build())
                          .collect(Collectors.toList())))
        .thenReturn(true);

    CapabilitySubjectPermission result =
        httpConnectionExecutionCapabilityCheck.performCapabilityCheckWithProto(parameters);

    mockHttp.close();

    assertThat(result.getPermissionResult()).isEqualTo(CapabilitySubjectPermission.PermissionResult.ALLOWED);
  }
}