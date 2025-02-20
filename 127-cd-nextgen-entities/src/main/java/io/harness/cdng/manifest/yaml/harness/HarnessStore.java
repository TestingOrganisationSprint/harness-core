/*
 * Copyright 2022 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Free Trial 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/05/PolyForm-Free-Trial-1.0.0.txt.
 */

package io.harness.cdng.manifest.yaml.harness;

import static io.harness.annotations.dev.HarnessTeam.CDP;
import static io.harness.cdng.manifest.yaml.harness.HarnessStoreConstants.HARNESS_STORE_TYPE;
import static io.harness.data.structure.EmptyPredicate.isNotEmpty;
import static io.harness.yaml.schema.beans.SupportedPossibleFieldTypes.runtime;

import io.harness.annotation.RecasterAlias;
import io.harness.annotations.dev.OwnedBy;
import io.harness.cdng.manifest.yaml.storeConfig.StoreConfig;
import io.harness.common.ParameterFieldHelper;
import io.harness.filters.FileRefExtractorHelper;
import io.harness.pms.yaml.ParameterField;
import io.harness.pms.yaml.YAMLFieldNameConstants;
import io.harness.pms.yaml.YamlNode;
import io.harness.walktree.beans.VisitableChildren;
import io.harness.walktree.visitor.SimpleVisitorHelper;
import io.harness.walktree.visitor.Visitable;
import io.harness.yaml.YamlSchemaTypes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Wither;
import org.springframework.data.annotation.TypeAlias;

@OwnedBy(CDP)
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonTypeName(HARNESS_STORE_TYPE)
@SimpleVisitorHelper(helperClass = FileRefExtractorHelper.class)
@TypeAlias("harnessStore")
@RecasterAlias("io.harness.cdng.manifest.yaml.harness.HarnessStore")
public class HarnessStore implements HarnessStoreConfig, Visitable {
  @JsonProperty(YamlNode.UUID_FIELD_NAME)
  @Getter(onMethod_ = { @ApiModelProperty(hidden = true) })
  @ApiModelProperty(hidden = true)
  private String uuid;

  @Wither
  @YamlSchemaTypes(value = {runtime})
  @ApiModelProperty(dataType = "[Lio.harness.cdng.manifest.yaml.harness.HarnessStoreFile;")
  @JsonProperty("files")
  private ParameterField<List<HarnessStoreFile>> files;

  // For Visitor Framework Impl
  @Getter(onMethod_ = { @ApiModelProperty(hidden = true) }) @ApiModelProperty(hidden = true) String metadata;

  @Override
  public String getKind() {
    return HARNESS_STORE_TYPE;
  }

  @Override
  public List<ParameterField<String>> getFileReferences() {
    return files != null && isNotEmpty(files.getValue())
        ? files.getValue().stream().map(HarnessStoreFile::getRef).collect(Collectors.toList())
        : Collections.emptyList();
  }

  public HarnessStore cloneInternal() {
    return HarnessStore.builder().files(files).build();
  }

  @Override
  public VisitableChildren getChildrenToWalk() {
    return VisitableChildren.builder().build();
  }

  @Override
  public StoreConfig applyOverrides(StoreConfig overrideConfig) {
    HarnessStore harnessStore = (HarnessStore) overrideConfig;
    HarnessStore resultantHarnessStore = this;

    if (harnessStore.getFiles() != null) {
      resultantHarnessStore = resultantHarnessStore.withFiles(harnessStore.getFiles());
    }

    return resultantHarnessStore;
  }

  @Override
  public Map<String, ParameterField<String>> extractFileRefs() {
    Map<String, ParameterField<String>> fileRefMap = new HashMap<>();
    if (files != null && isNotEmpty(files.getValue())) {
      for (HarnessStoreFile file : files.getValue()) {
        fileRefMap.put(YAMLFieldNameConstants.FILE_REF, file.getRef());
      }
    }

    return fileRefMap;
  }

  public HarnessStoreDTO toHarnessStoreDTO() {
    return HarnessStoreDTO.builder().files(ParameterFieldHelper.getParameterFieldValue(files)).build();
  }
}
