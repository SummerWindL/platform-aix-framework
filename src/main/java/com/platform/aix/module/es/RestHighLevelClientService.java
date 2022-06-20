package com.platform.aix.module.es;

import com.fasterxml.jackson.databind.JsonNode;
import com.platform.common.util.JsonAdaptor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Advance
 * @date 2022年06月20日 10:43
 * @since V1.0.0
 */
@Slf4j
@Service
public class RestHighLevelClientService {
    @Resource
    private RestHighLevelClient client;

    @Resource
    private JsonAdaptor mapper;

    /**
     * 删除索引
     * @param indexName
     * @return
     */
    public void deleteIndex(String indexName) throws IOException {
        //第一种，按索引id删除某个文档。
/*      DeleteRequest deleteRequest = new DeleteRequest("post", "m56EDXIBK5FuWnazQHHa");
       client.delete(deleteRequest,RequestOptions.DEFAULT);*/

        //第二种，一次性删除整个索引
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        client.indices().delete(deleteIndexRequest,RequestOptions.DEFAULT);

        //第三种，先按条件查询出某范围内所有Id，再一个一个删除
        /*BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.size(100);
        SearchRequest searchRequest = new SearchRequest().indices("post");
        searchRequest.source(sourceBuilder);
        SearchResponse response1 = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = response1.getHits().getHits();
        for(SearchHit hit:hits){
            String id = hit.getId();
            DeleteRequest deleteRequest = new DeleteRequest("post", id);
            client.delete(deleteRequest,RequestOptions.DEFAULT);
        }*/
    }
    /**
     * 创建索引
     * @param indexName
     * @param settings
     * @param mapping
     * @return
     * @throws IOException
     */
    public CreateIndexResponse createIndex(String indexName, String settings, String mapping) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        if (null != settings && !"".equals(settings)) {
            request.settings(settings, XContentType.JSON);
        }
        if (null != mapping && !"".equals(mapping)) {
            request.mapping(mapping, XContentType.JSON);
        }
        return client.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * 判断 index 是否存在
     */
    public boolean indexExists(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 搜索
     */
    public SearchResponse search(String field, String key, String rangeField, String
            from, String to, String termField, String termVal,
                                 String ... indexNames) throws IOException{
        SearchRequest request = new SearchRequest(indexNames);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(new MatchQueryBuilder(field, key)).must(new RangeQueryBuilder(rangeField).from(from).to(to)).must(new TermQueryBuilder(termField, termVal));
        builder.query(boolQueryBuilder);
        request.source(builder);
        log.info("[搜索语句为:{}]",request.source().toString());
        return client.search(request, RequestOptions.DEFAULT);
    }

    /**
     * 批量导入
     * @param indexName
     * @param isAutoId 使用自动id 还是使用传入对象的id
     * @param source
     * @return
     * @throws IOException
     */
    public BulkResponse importAll(String indexName, boolean isAutoId, String  source) throws IOException{
        if (0 == source.length()){
            //todo 抛出异常 导入数据为空
        }
        BulkRequest request = new BulkRequest();
        JsonNode jsonNode = mapper.readTree(source);

        if (jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
                if (isAutoId) {
                    request.add(new IndexRequest(indexName).source(node.toPrettyString(), XContentType.JSON));
                } else {
                    request.add(new IndexRequest(indexName)
                            .id(node.get("id").asText())
                            .source(node.asText(), XContentType.JSON));
                }
            }
        }
        return client.bulk(request, RequestOptions.DEFAULT);
    }
}
