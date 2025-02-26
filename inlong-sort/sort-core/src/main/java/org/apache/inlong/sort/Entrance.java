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

package org.apache.inlong.sort;

import com.google.common.base.Preconditions;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.inlong.sort.configuration.Configuration;
import org.apache.inlong.sort.configuration.Constants;
import org.apache.inlong.sort.parser.impl.FlinkSqlParser;
import org.apache.inlong.sort.parser.result.ParseResult;
import org.apache.inlong.sort.protocol.GroupInfo;
import org.apache.inlong.sort.util.ParameterTool;

import java.io.File;
import java.io.IOException;

public class Entrance {
    public static void main(String[] args) throws Exception {
        final ParameterTool parameterTool = ParameterTool.fromArgs(args);
        final Configuration config = parameterTool.getConfiguration();
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // Checkpoint related
        env.enableCheckpointing(config.getInteger(Constants.CHECKPOINT_INTERVAL_MS));
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(
                config.getInteger(Constants.MIN_PAUSE_BETWEEN_CHECKPOINTS_MS));
        env.getCheckpointConfig().setCheckpointTimeout(config.getInteger(Constants.CHECKPOINT_TIMEOUT_MS));
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner()
                .inStreamingMode().build();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, settings);
        final GroupInfo groupInfo = getGroupInfoFromFile(config.getString(Constants.GROUP_INFO_FILE));
        final FlinkSqlParser parser = FlinkSqlParser.getInstance(tableEnv, groupInfo);
        final ParseResult parseResult = Preconditions.checkNotNull(parser.parse(),
                "parse result is null");
        parseResult.execute();
    }

    private static GroupInfo getGroupInfoFromFile(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(fileName), GroupInfo.class);
    }
}
