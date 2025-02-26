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

package org.apache.inlong.manager.client;

import lombok.Data;
import org.apache.inlong.manager.common.auth.DefaultAuthentication;
import org.apache.inlong.manager.common.pojo.group.InlongGroupInfo;
import org.apache.inlong.manager.common.pojo.group.pulsar.InlongPulsarInfo;
import org.apache.inlong.manager.common.pojo.sort.FlinkSortConf;

import java.util.HashMap;
import java.util.Map;

/**
 * Base example class for client.
 */
@Data
public class BaseExample {

    // Manager web url
    private String serviceUrl = "127.0.0.1:8083";
    // Inlong user && passwd
    private DefaultAuthentication inlongAuth = new DefaultAuthentication("admin", "inlong");
    // Inlong group ID
    private String groupId = "{group.id}";
    // Inlong stream ID
    private String streamId = "{stream.id}";
    // Flink cluster url
    private String flinkUrl = "{flink.cluster.url}";
    // Pulsar cluster admin url
    private String pulsarAdminUrl = "{pulsar.admin.url}";
    // Pulsar cluster service url
    private String pulsarServiceUrl = "{pulsar.service.url}";
    // Pulsar tenant
    private String tenant = "{pulsar.tenant}";
    // Pulsar tenant
    private String namespace = "{pulsar.namespace}";
    // Pulsar topic
    private String topic = "{pulsar.topic}";

    public InlongGroupInfo createGroupInfo() {
        InlongPulsarInfo pulsarInfo = new InlongPulsarInfo();
        pulsarInfo.setInlongGroupId(groupId);

        // pulsar conf
        pulsarInfo.setServiceUrl(pulsarServiceUrl);
        pulsarInfo.setAdminUrl(pulsarAdminUrl);
        pulsarInfo.setTenant(tenant);
        pulsarInfo.setMqResource(namespace);

        // set enable zk, create resource, lightweight mode, and cluster tag
        pulsarInfo.setEnableZookeeper(0);
        pulsarInfo.setEnableCreateResource(1);
        pulsarInfo.setLightweight(1);
        pulsarInfo.setInlongClusterTag("default_cluster");

        pulsarInfo.setDailyRecords(10000000);
        pulsarInfo.setDailyStorage(10000);
        pulsarInfo.setPeakRecords(100000);
        pulsarInfo.setMaxLength(10000);

        // flink conf
        FlinkSortConf sortConf = new FlinkSortConf();
        sortConf.setServiceUrl(flinkUrl);
        Map<String, String> map = new HashMap<>(16);
        sortConf.setProperties(map);
        pulsarInfo.setSortConf(sortConf);

        return pulsarInfo;
    }

}