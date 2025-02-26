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

package org.apache.inlong.manager.common.pojo.tubemq;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The response info of tube cluster.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TubeClusterResponse extends TubeManagerResponse {

    private List<DataBean> data;

    public static class DataBean {

        private int clusterId;
        private String clusterName;
        private String createTime;
        private Object modifyTime;
        private String createUser;

        public int getClusterId() {
            return clusterId;
        }

        public void setClusterId(int clusterId) {
            this.clusterId = clusterId;
        }

        public String getClusterName() {
            return clusterName;
        }

        public void setClusterName(String clusterName) {
            this.clusterName = clusterName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(Object modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }
    }
}
