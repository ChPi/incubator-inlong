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

package org.apache.inlong.manager.client.api.inner;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.inlong.manager.client.api.InlongStream;
import org.apache.inlong.manager.common.util.AssertUtils;
import org.apache.inlong.manager.common.pojo.group.InlongGroupApproveRequest;
import org.apache.inlong.manager.common.pojo.group.InlongGroupInfo;
import org.apache.inlong.manager.common.pojo.stream.InlongStreamApproveRequest;

import java.util.List;
import java.util.Map;

/**
 * InnerGroupContext.
 */
@Data
@NoArgsConstructor
public class InnerGroupContext {

    private InlongGroupInfo groupInfo;

    private Map<String, InnerStreamContext> streamContextMap = Maps.newHashMap();

    private Map<String, InlongStream> streamMap = Maps.newHashMap();

    private Pair<InlongGroupApproveRequest, List<InlongStreamApproveRequest>> initMsg;

    public String getGroupId() {
        AssertUtils.notNull(groupInfo, "InlongGroupRequest is not init");
        return groupInfo.getInlongGroupId();
    }

    public void setStreamContext(InnerStreamContext streamContext) {
        AssertUtils.isTrue(streamContext != null && streamContext.getStreamInfo() != null,
                "StreamContext should not be null");
        if (MapUtils.isEmpty(streamContextMap)) {
            streamContextMap = Maps.newHashMap();
        }
        streamContextMap.put(streamContext.getStreamInfo().getName(), streamContext);
    }

    public void setStream(InlongStream stream) {
        AssertUtils.isTrue(stream != null, "Stream should not be null");
        if (MapUtils.isEmpty(streamMap)) {
            streamMap = Maps.newHashMap();
        }
        streamMap.put(stream.getStreamId(), stream);
    }

    public InlongStream getStream(String streamId) {
        AssertUtils.isTrue(StringUtils.isNotEmpty(streamId), "Stream streamId should not be null");
        if (MapUtils.isNotEmpty(streamMap)) {
            return streamMap.get(streamId);
        }
        return null;
    }
}
