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

package org.apache.inlong.manager.common.pojo.commonserver;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The VO of common file server list.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Response of file source list")
public class CommonFileServerListVo {

    private int id;

    @ApiModelProperty("access type, support: Agent, DataProxy Client, LoadProxy")
    private String accessType;

    @ApiModelProperty("source IP")
    private String ip;

    private int port;

    @ApiModelProperty("issue type, support: SSH, TCS")
    private String issueType;

    private String username;

    @ApiModelProperty("status, 0: Normal, 1: Invalid")
    private Integer status;

    private String modifier;
    private Date modifyTime;
}
