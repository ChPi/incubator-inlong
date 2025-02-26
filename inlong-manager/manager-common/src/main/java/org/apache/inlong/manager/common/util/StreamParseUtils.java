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

package org.apache.inlong.manager.common.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.inlong.manager.common.enums.DataFormat;
import org.apache.inlong.manager.common.enums.SinkType;
import org.apache.inlong.manager.common.enums.SourceType;
import org.apache.inlong.manager.common.enums.TransformType;
import org.apache.inlong.manager.common.pojo.stream.SinkField;
import org.apache.inlong.manager.common.pojo.stream.StreamNode;
import org.apache.inlong.manager.common.pojo.stream.StreamPipeline;
import org.apache.inlong.manager.common.pojo.stream.StreamSink;
import org.apache.inlong.manager.common.pojo.stream.StreamSource;
import org.apache.inlong.manager.common.pojo.stream.StreamTransform;
import org.apache.inlong.manager.common.pojo.transform.TransformDefinition;
import org.apache.inlong.manager.common.pojo.transform.deduplication.DeDuplicationDefinition;
import org.apache.inlong.manager.common.pojo.transform.filter.FilterDefinition;
import org.apache.inlong.manager.common.pojo.transform.joiner.JoinerDefinition;
import org.apache.inlong.manager.common.pojo.transform.replacer.StringReplacerDefinition;
import org.apache.inlong.manager.common.pojo.transform.splitter.SplitterDefinition;

import java.util.List;

/**
 * Utils of stream parse.
 */
public class StreamParseUtils {

    public static final String LEFT_NODE = "leftNode";
    public static final String RIGHT_NODE = "rightNode";
    public static final String SOURCE_TYPE = "sourceType";
    public static final String SOURCE_NAME = "sourceName";
    public static final String SINK_TYPE = "sinkType";
    public static final String SINK_NAME = "sinkName";
    public static final String TRANSFORM_TYPE = "transformType";
    public static final String TRANSFORM_NAME = "transformName";

    private static Gson gson = new Gson();

    public static TransformDefinition parseTransformDefinition(String transformDefinition,
            TransformType transformType) {
        switch (transformType) {
            case FILTER:
                return gson.fromJson(transformDefinition, FilterDefinition.class);
            case JOINER:
                return parseJoinerDefinition(transformDefinition);
            case SPLITTER:
                return gson.fromJson(transformDefinition, SplitterDefinition.class);
            case DE_DUPLICATION:
                return gson.fromJson(transformDefinition, DeDuplicationDefinition.class);
            case STRING_REPLACER:
                return gson.fromJson(transformDefinition, StringReplacerDefinition.class);
            default:
                throw new IllegalArgumentException(String.format("Unsupported transformType for %s", transformType));
        }
    }

    public static JoinerDefinition parseJoinerDefinition(String transformDefinition) {
        JoinerDefinition joinerDefinition = gson.fromJson(transformDefinition, JoinerDefinition.class);
        JsonObject joinerJson = gson.fromJson(transformDefinition, JsonObject.class);
        JsonObject leftNode = joinerJson.getAsJsonObject(LEFT_NODE);
        StreamNode leftStreamNode = parseNode(leftNode);
        joinerDefinition.setLeftNode(leftStreamNode);
        JsonObject rightNode = joinerJson.getAsJsonObject("rightNode");
        StreamNode rightStreamNode = parseNode(rightNode);
        joinerDefinition.setRightNode(rightStreamNode);
        return joinerDefinition;
    }

    private static StreamNode parseNode(JsonObject jsonObject) {
        if (jsonObject.has(SOURCE_TYPE)) {
            String sourceName = jsonObject.get(SOURCE_NAME).getAsString();
            StreamSource source = new StreamSource() {
                @Override
                public SourceType getSourceType() {
                    return null;
                }

                @Override
                public SyncType getSyncType() {
                    return null;
                }

                @Override
                public DataFormat getDataFormat() {
                    return null;
                }
            };
            source.setSourceName(sourceName);
            return source;
        } else if (jsonObject.has(SINK_TYPE)) {
            String sinkName = jsonObject.get(SINK_NAME).getAsString();
            StreamSink sink = new StreamSink() {
                @Override
                public SinkType getSinkType() {
                    return null;
                }

                @Override
                public List<SinkField> getSinkFields() {
                    return null;
                }

                @Override
                public DataFormat getDataFormat() {
                    return null;
                }
            };
            sink.setSinkName(sinkName);
            return sink;
        } else {
            String transformName = jsonObject.get(TRANSFORM_NAME).getAsString();
            StreamTransform transform = new StreamTransform() {
                @Override
                public String getTransformName() {
                    return super.getTransformName();
                }
            };
            transform.setTransformName(transformName);
            return transform;
        }
    }

    public static StreamPipeline parseStreamPipeline(String tempView, String inlongStreamId) {
        Preconditions.checkNotEmpty(tempView,
                String.format(" should not be null for streamId=%s", inlongStreamId));
        return gson.fromJson(tempView, StreamPipeline.class);
    }

}
