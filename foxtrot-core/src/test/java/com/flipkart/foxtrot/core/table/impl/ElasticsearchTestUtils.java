package com.flipkart.foxtrot.core.table.impl;
/*
 * Copyright 2014 Flipkart Internet Pvt. Ltd.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.flipkart.foxtrot.core.querystore.impl.ElasticsearchConfig;
import com.flipkart.foxtrot.core.querystore.impl.ElasticsearchConnection;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;

import java.util.Collections;

/***
 Created by nitish.goyal on 02/08/18
 ***/
@Slf4j
public class ElasticsearchTestUtils {

    public static ElasticsearchConnection getConnection() throws Exception {
        ElasticsearchConfig config = new ElasticsearchConfig();
        config.setCluster("elasticsearch");
        config.setHosts(Collections.singletonList("localhost"));
        config.setTableNamePrefix("foxtrot");

        ElasticsearchConnection elasticsearchConnection = new ElasticsearchConnection(config);
        elasticsearchConnection.start();
        return elasticsearchConnection;
    }

    public static void cleanupIndices(final ElasticsearchConnection elasticsearchConnection) {
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("_all");
            final DeleteIndexResponse deleteIndexResponse = elasticsearchConnection.getClient()
                    .admin()
                    .indices()
                    .delete(deleteIndexRequest)
                    .get();
            log.info("Delete index response: {}", deleteIndexResponse);
        } catch (Exception e) {
            log.error("Index Cleanup failed", e);
        }
    }
}