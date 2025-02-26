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

package org.apache.inlong.manager.client.cli.pojo;

import lombok.Data;
import org.apache.inlong.manager.client.api.InlongGroupContext.InlongGroupStatus;

import java.util.Date;

/**
 * Group info, including inlong group id, inlong group name, etc.
 */
@Data
public class GroupInfo {

    private Integer id;
    private String inlongGroupId;
    private String name;
    private String status;
    private Date modifyTime;

    public void setStatus(String status) {
        InlongGroupStatus groupStatus = InlongGroupStatus.parseStatusByCode(Integer.parseInt(status));
        this.status = groupStatus.name() + " (" + status + ")";
    }
}
