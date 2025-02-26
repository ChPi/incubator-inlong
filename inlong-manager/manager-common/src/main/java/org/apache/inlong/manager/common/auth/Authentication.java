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

package org.apache.inlong.manager.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Locale;
import java.util.Map;

public interface Authentication {

    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    AuthType getAuthType();

    void configure(Map<String, String> properties);

    enum AuthType {
        UNAME_PASSWD,
        TOKEN,
        SECRET,
        SECRET_AND_TOKEN;

        public static AuthType forType(String type) {
            for (AuthType authType : values()) {
                if (authType.name().equals(type.toUpperCase())) {
                    return authType;
                }
            }
            throw new IllegalArgumentException(String.format("Unsupported authType=%s for Inlong", type));
        }

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
