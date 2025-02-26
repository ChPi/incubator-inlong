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

package org.apache.inlong.manager.service.resource.es;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.inlong.manager.common.enums.GlobalConstants;
import org.apache.inlong.manager.common.enums.SinkStatus;
import org.apache.inlong.manager.common.enums.SinkType;
import org.apache.inlong.manager.common.exceptions.WorkflowException;
import org.apache.inlong.manager.common.pojo.sink.SinkInfo;
import org.apache.inlong.manager.common.pojo.sink.es.ElasticsearchFieldInfo;
import org.apache.inlong.manager.common.pojo.sink.es.ElasticsearchSinkDTO;
import org.apache.inlong.manager.dao.entity.StreamSinkFieldEntity;
import org.apache.inlong.manager.dao.mapper.StreamSinkFieldEntityMapper;
import org.apache.inlong.manager.service.resource.SinkResourceOperator;
import org.apache.inlong.manager.service.sink.StreamSinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Elasticsearch resource operator
 */
@Service
public class ElasticsearchResourceOperator implements SinkResourceOperator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchResourceOperator.class);
    @Autowired
    private StreamSinkService sinkService;
    @Autowired
    private StreamSinkFieldEntityMapper sinkFieldMapper;

    @Override
    public Boolean accept(SinkType sinkType) {
        return SinkType.ELASTICSEARCH == sinkType;
    }

    /**
     * Create ES index according to the groupId and ES config
     */
    @Override
    public void createSinkResource(SinkInfo sinkInfo) {
        if (sinkInfo == null) {
            LOGGER.warn("sink info was null, skip to create resource");
            return;
        }

        if (SinkStatus.CONFIG_SUCCESSFUL.getCode().equals(sinkInfo.getStatus())) {
            LOGGER.warn("sink resource [" + sinkInfo.getId() + "] already success, skip to create");
            return;
        } else if (GlobalConstants.DISABLE_CREATE_RESOURCE.equals(sinkInfo.getEnableCreateResource())) {
            LOGGER.warn("create resource was disabled, skip to create for [" + sinkInfo.getId() + "]");
            return;
        }

        this.createIndex(sinkInfo);
    }

    private void createIndex(SinkInfo sinkInfo) {
        LOGGER.info("begin to create ES Index for sinkId={}", sinkInfo.getId());

        List<StreamSinkFieldEntity> sinkList = sinkFieldMapper.selectBySinkId(sinkInfo.getId());
        if (CollectionUtils.isEmpty(sinkList)) {
            LOGGER.warn("no es fields found, skip to create index for sinkId={}", sinkInfo.getId());
        }

        // set fields
        List<ElasticsearchFieldInfo> fieldList = getElasticsearchFieldFromSink(sinkList);

        try {
            ElasticsearchConfig config = new ElasticsearchConfig();
            ElasticsearchSinkDTO esInfo = ElasticsearchSinkDTO.getFromJson(sinkInfo.getExtParams());
            if (StringUtils.isNotEmpty(esInfo.getUsername())) {
                config.setAuthEnable(true);
                config.setUsername(esInfo.getUsername());
                config.setPassword(esInfo.getPassword());
            }
            config.setHost(esInfo.getHost());
            config.setPort(esInfo.getPort());

            ElasticsearchApi client = new ElasticsearchApi();
            client.setEsConfig(config);

            String indexName = ElasticsearchSinkDTO.getElasticSearchIndexName(esInfo, fieldList);
            boolean indexExists = client.indexExists(indexName);

            // 3. index not exists, create it
            if (!indexExists) {
                client.createIndexAndMapping(indexName, fieldList);
            } else {
                // 4. index exists, add fields - skip the exists fields
                client.addNotExistFields(indexName, fieldList);
            }

            // 5. update the sink status to success
            String info = "success to create Elasticsearch resource";
            sinkService.updateStatus(sinkInfo.getId(), SinkStatus.CONFIG_SUCCESSFUL.getCode(), info);
            LOGGER.info(info + " for sinkInfo={}", sinkInfo);
        } catch (Throwable e) {
            String errMsg = "create Elasticsearch index failed: " + e.getMessage();
            LOGGER.error(errMsg, e);
            sinkService.updateStatus(sinkInfo.getId(), SinkStatus.CONFIG_FAILED.getCode(), errMsg);
            throw new WorkflowException(errMsg);
        }
    }

    public List<ElasticsearchFieldInfo> getElasticsearchFieldFromSink(List<StreamSinkFieldEntity> sinkList) {
        List<ElasticsearchFieldInfo> fieldList = new ArrayList<>();
        for (StreamSinkFieldEntity entry : sinkList) {
            ElasticsearchFieldInfo fieldInfo = new ElasticsearchFieldInfo();
            fieldInfo.setName(entry.getFieldName());
            fieldInfo.setType(entry.getFieldType());
            fieldInfo.setFormat(entry.getFieldFormat());
            ElasticsearchFieldInfo filedExtrParam =
                    ElasticsearchFieldInfo.getFromJson(entry.getExtrParam());
            fieldInfo.setScalingFactor(filedExtrParam.getScalingFactor());
            fieldInfo.setAnalyzer(filedExtrParam.getAnalyzer());
            fieldInfo.setSearchAnalyzer(filedExtrParam.getSearchAnalyzer());
            fieldList.add(fieldInfo);
        }
        return fieldList;
    }

}
