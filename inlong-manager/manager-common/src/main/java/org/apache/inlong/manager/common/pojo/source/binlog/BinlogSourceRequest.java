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

package org.apache.inlong.manager.common.pojo.source.binlog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.inlong.manager.common.enums.SourceType;
import org.apache.inlong.manager.common.pojo.source.SourceRequest;
import org.apache.inlong.manager.common.util.JsonTypeDefine;

/**
 * Request info of the binlog source info
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Request of the binlog source info")
@JsonTypeDefine(value = SourceType.SOURCE_BINLOG)
public class BinlogSourceRequest extends SourceRequest {

    @ApiModelProperty("Username of the DB server")
    private String user;

    @ApiModelProperty("Password of the DB server")
    private String password;

    @ApiModelProperty("Hostname of the DB server")
    private String hostname;

    @ApiModelProperty("Exposed port of the DB server")
    private int port = 3306;

    @ApiModelProperty("Id of physical node of MySQL Cluster, 0 if single node")
    private Integer serverId = 0;

    @ApiModelProperty("Whether include schema, default is 'false'")
    private String includeSchema;

    @ApiModelProperty(value = "List of DBs to be collected, supporting regular expressions, "
            + "separate them with ',', for example: db1,test_db*",
            notes = "DBs not in this list are excluded. If not set, all DBs are monitored")
    private String databaseWhiteList;

    @ApiModelProperty(value = "List of tables to be collected, supporting regular expressions, "
            + "separate them with ',', for example: tb1,user*",
            notes = "Tables not in this list are excluded. By default, all tables are monitored")
    private String tableWhiteList;

    @ApiModelProperty("Database time zone, Default is UTC")
    private String serverTimezone;

    @ApiModelProperty("The interval for recording an offset")
    private String intervalMs;

    /**
     * <code>initial</code>: Default mode, do a snapshot when no offset is found.
     * <p/>
     * <code>when_needed</code>: Similar to initial, do a snapshot when the binlog position
     * has been purged on the DB server.
     * <p/>
     * <code>never</code>: Do not snapshot.
     * <p/>
     * <code>schema_only</code>: All tables' column name will be taken, but the table data will not be exported,
     * and it will only be consumed from the end of the binlog at the task is started.
     * So it is very suitable for not caring about historical data, but only about recent changes. the
     * <p/>
     * <code>schema_only_recovery</code>: When <code>schema_only</code> mode fails, use this mode to recover, which is
     * generally not used.
     */
    @ApiModelProperty("Snapshot mode, supports: initial, when_needed, never, schema_only, schema_only_recovery")
    private String snapshotMode = "initial";

    @ApiModelProperty("The file path to store offset info")
    private String offsetFilename;

    @ApiModelProperty("The file path to store history info")
    private String historyFilename;

    @ApiModelProperty("Whether to monitor the DDL, default is 'false'")
    private String monitoredDdl;

    @ApiModelProperty("Timestamp standard for binlog: SQL, ISO_8601")
    private String timestampFormatStandard = "SQL";

    @ApiModelProperty("Need transfer total database")
    private boolean allMigration = false;

    @ApiModelProperty(value = "Primary key must be shared by all tables", required = false)
    private String primaryKey;

    public BinlogSourceRequest() {
        this.setSourceType(SourceType.BINLOG.toString());
    }

}
