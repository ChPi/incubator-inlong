/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.inlong.manager.common.pojo.sink;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Response of the stream sink
 */
@Data
@ApiModel("Response of the stream sink")
@NoArgsConstructor
public class SinkResponse {

    @ApiModelProperty("Sink type, including: HIVE, ICEBERG, etc.")
    protected String sinkType;

    @ApiModelProperty("Sink id")
    private Integer id;

    @ApiModelProperty("Inlong group id")
    private String inlongGroupId;

    @ApiModelProperty("Inlong stream id")
    private String inlongStreamId;

    @ApiModelProperty("Sink name, unique in one stream.")
    private String sinkName;

    @ApiModelProperty("Sink description")
    private String description;

    @ApiModelProperty("Inlong cluster name")
    private String inlongClusterName;

    @ApiModelProperty("Data node name")
    private String dataNodeName;

    @ApiModelProperty("Sort task name")
    private String sortTaskName;

    @ApiModelProperty("Sort consumer group")
    private String sortConsumerGroup;

    @ApiModelProperty(value = "Whether to enable create sink resource? 0: disable, 1: enable. default is 1",
            notes = "Such as create Hive table")
    private Integer enableCreateResource = 1;

    @ApiModelProperty("Backend operation log")
    private String operateLog;

    @ApiModelProperty("Status")
    private Integer status;

    @ApiModelProperty("Previous State")
    private Integer previousStatus;

    @ApiModelProperty("Creator")
    private String creator;

    @ApiModelProperty("Modifier")
    private String modifier;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    @ApiModelProperty("Sink field list")
    private List<SinkFieldResponse> fieldList;

    @ApiModelProperty("Properties for sink")
    private Map<String, Object> properties;

}
