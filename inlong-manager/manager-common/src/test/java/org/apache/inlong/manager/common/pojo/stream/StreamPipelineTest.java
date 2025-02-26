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

package org.apache.inlong.manager.common.pojo.stream;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

/**
 * Stream pipeline service test for check has circle.
 */
public class StreamPipelineTest {

    @Test
    public void testCheckHasCircle() {
        StreamPipeline streamPipeline = new StreamPipeline();
        streamPipeline.addRelationShip(new StreamNodeRelationship(Sets.newHashSet("A", "B"), Sets.newHashSet("C")));
        streamPipeline.addRelationShip(new StreamNodeRelationship(Sets.newHashSet("C"), Sets.newHashSet("D")));
        streamPipeline.addRelationShip(new StreamNodeRelationship(Sets.newHashSet("D"), Sets.newHashSet("E", "F")));
        streamPipeline.addRelationShip(new StreamNodeRelationship(Sets.newHashSet("F"), Sets.newHashSet("G")));
        streamPipeline.addRelationShip(new StreamNodeRelationship(Sets.newHashSet("E"), Sets.newHashSet("H", "C")));
        Pair<Boolean, Pair<String, String>> circleState = streamPipeline.hasCircle();
        Assert.assertTrue(circleState.getLeft());
        Assert.assertTrue(Sets.newHashSet("E","C").contains(circleState.getRight().getLeft()));
        Assert.assertTrue(Sets.newHashSet("E","C").contains(circleState.getRight().getRight()));
    }

}
