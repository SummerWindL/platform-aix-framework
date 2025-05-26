package com.platform.aix.common.datacommon.db.service.pikai.impl;

import com.algolia.api.SearchClient;
import com.algolia.model.search.UpdatedAtResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.aix.common.datacommon.cache.impl.AsyncServiceImpl;
import com.platform.aix.common.datacommon.db.dao.IMybatisMapper;
import com.platform.aix.common.datacommon.db.dao.PikaiTimelineContentMapper;
import com.platform.aix.common.datacommon.db.domain.PikaiTimelineContent;
import com.platform.aix.common.datacommon.db.domain.PikaiTimelineContentExample;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiTimelineContentService;
import com.platform.aix.controller.pikai.common.request.PikaiTimelineContentReq;
import com.platform.common.util.BeanUtil;
import com.platform.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月17日 11:02
 */
@Service
public class PikaiTimelineContentServiceImpl extends AsyncServiceImpl<Integer, PikaiTimelineContent> implements PikaiTimelineContentService {

    @Autowired
    private PikaiTimelineContentMapper mapper;
    @Override
    public IMybatisMapper<PikaiTimelineContent, Integer, ?> getMapper() {
        return mapper;
    }

    @Override
    public void clearCache() {

    }

    @Override
    protected void update(PikaiTimelineContent pikaiTimelineContent) {

    }

    @Override
    protected void save(PikaiTimelineContent pikaiTimelineContent) {

    }

    @Override
    protected void delete(Integer PK) {

    }

    @Override
    public int savePikaiTimelineContent(PikaiTimelineContentReq content) {
        PikaiTimelineContent pikaiTimelineContent = new PikaiTimelineContent();
        BeanUtil.copyPropertiesIgnoreNull(pikaiTimelineContent,content);
        pikaiTimelineContent.setUpdateTime(DateUtil.now());
        return mapper.updateOrInsertSelective(pikaiTimelineContent);
    }

    @Override
    public int updatePikaiTimelineContent(PikaiTimelineContent content) {
        return mapper.updateByPrimaryKey(content);
    }

    @Override
    public int deletPikaiTimelineContent(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public PikaiTimelineContent queryPikaiTimelineContentOne(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PikaiTimelineContent> queryAllContent(PikaiTimelineContentReq contentReq) {
        PikaiTimelineContentExample pikaiTimelineContentExample = new PikaiTimelineContentExample();
        BeanUtil.copyPropertiesIgnoreNull(pikaiTimelineContentExample,contentReq);
        List<PikaiTimelineContent> pikaiTimelineContents = mapper.selectByExample(pikaiTimelineContentExample);
        logger.info("====数据初始化Algolia开始====");
        // 将内容放到Algolia中
        // Connect and authenticate with your Algolia app
        SearchClient client = new SearchClient("E9C63HCURS", "5d47ca31cd4b1c7365e661c1b97c552b");
        // 每次进来前先清除index，在保存
        // TODO 需要调整数据结构 参考 https://weijunext.com/article/nextjs-auth-postgres-prisma-login 网络请求
        /**
         * "hierarchy": {
         *                         "lvl0": "Documentation",
         *                         "lvl1": "5分钟搞定！给Next.js项目集成Algolia DocSearch搜索功能",
         *                         "lvl2": "前言",
         *                         "lvl3": null,
         *                         "lvl4": null,
         *                         "lvl5": null,
         *                         "lvl6": null
         *                     }
         */
        UpdatedAtResponse content_index = client.clearObjects("content_index");
        logger.info("====clear content_index successed：{}====",content_index);
        // Save records in Algolia index
        client.saveObjects("content_index", pikaiTimelineContents);
        logger.info("===={} 条数据初始化Algolia完成====",pikaiTimelineContents.size());
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pikaiTimelineContents;
    }

    @Override
    public List<PikaiTimelineContent> queryContentByKeyword(String keyword) {
        return mapper.selectContentByKeyword(keyword);
    }

    @Override
    public List<Map<String, Object>> countUserContentNum() {
        return mapper.countUserContentNum();
    }

//    public static void main(String[] args) throws Exception {
//        // Fetch sample dataset
//        URL url = new URI("https://dashboard.algolia.com/api/1/sample_datasets?type=movie").toURL();
//        InputStream stream = url.openStream();
//        ObjectMapper mapper = new ObjectMapper();
//        List<JsonNode> movies = mapper.readValue(stream, new TypeReference<List<JsonNode>>() {});
//        stream.close();
//
//        // Connect and authenticate with your Algolia app
//        SearchClient client = new SearchClient("E9C63HCURS", "5d47ca31cd4b1c7365e661c1b97c552b");
//
//        // Save records in Algolia index
//        client.saveObjects("movies_index", movies);
//        client.close();
//    }

}
