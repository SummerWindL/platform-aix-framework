//package com.platform.aix.es;
//
//import cn.hutool.json.JSON;
//import com.platform.aix.PlatformAixApplicationTests;
//import com.platform.aix.module.es.RestHighLevelClientService;
//import com.platform.common.util.JSONUtil;
//import com.platform.common.util.JsonAdaptor;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.IOException;
//
///**
// * @author Advance
// * @date 2022年06月20日 10:47
// * @since V1.0.0
// */
//@Slf4j
//public class EsServiceTest extends PlatformAixApplicationTests {
//    @Autowired
//    private RestHighLevelClientService clientService;
//    @Autowired
//    private JsonAdaptor jsonAdaptor;
//
//    @Test
//    public void deleteIdx() throws IOException {
//        clientService.deleteIndex("idx_item");
//    }
//
//    @Test
//    public void createIdx1() throws IOException {
//        String settings = "" +
//                "  {\n" +
//                "      \"number_of_shards\" : \"2\",\n" +
//                "      \"number_of_replicas\" : \"0\"\n" +
//                "   }";
//        String mappings = "" +
//                "{\n" +
//                "    \"properties\": {\n" +
//                "      \"itemId\" : {\n" +
//                "        \"type\": \"keyword\",\n" +
//                "        \"ignore_above\": 64\n" +
//                "      },\n" +
//                "      \"urlId\" : {\n" +
//                "        \"type\": \"keyword\",\n" +
//                "        \"ignore_above\": 64\n" +
//                "      },\n" +
//                "      \"sellAddress\" : {\n" +
//                "        \"type\": \"text\",\n" +
//                "        \"analyzer\": \"ik_max_word\", \n" +
//                "        \"search_analyzer\": \"ik_smart\",\n" +
//                "        \"fields\": {\n" +
//                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}\n" +
//                "        }\n" +
//                "      },\n" +
//                "      \"courierFee\" : {\n" +
//                "        \"type\": \"text\n" +
//                "      },\n" +
//                "      \"promotions\" : {\n" +
//                "        \"type\": \"text\",\n" +
//                "        \"analyzer\": \"ik_max_word\", \n" +
//                "        \"search_analyzer\": \"ik_smart\",\n" +
//                "        \"fields\": {\n" +
//                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}\n" +
//                "        }\n" +
//                "      },\n" +
//                "      \"originalPrice\" : {\n" +
//                "        \"type\": \"keyword\",\n" +
//                "        \"ignore_above\": 64\n" +
//                "      },\n" +
//                "      \"startTime\" : {\n" +
//                "        \"type\": \"date\",\n" +
//                "        \"format\": \"yyyy-MM-dd HH:mm:ss\"\n" +
//                "      },\n" +
//                "      \"endTime\" : {\n" +
//                "        \"type\": \"date\",\n" +
//                "        \"format\": \"yyyy-MM-dd HH:mm:ss\"\n" +
//                "      },\n" +
//                "      \"title\" : {\n" +
//                "        \"type\": \"text\",\n" +
//                "        \"analyzer\": \"ik_max_word\", \n" +
//                "        \"search_analyzer\": \"ik_smart\",\n" +
//                "        \"fields\": {\n" +
//                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}\n" +
//                "        }\n" +
//                "      },\n" +
//                "      \"serviceGuarantee\" : {\n" +
//                "        \"type\": \"text\",\n" +
//                "        \"analyzer\": \"ik_max_word\", \n" +
//                "        \"search_analyzer\": \"ik_smart\",\n" +
//                "        \"fields\": {\n" +
//                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}\n" +
//                "        }\n" +
//                "      },\n" +
//                "      \"venue\" : {\n" +
//                "        \"type\": \"text\",\n" +
//                "        \"analyzer\": \"ik_max_word\", \n" +
//                "        \"search_analyzer\": \"ik_smart\",\n" +
//                "        \"fields\": {\n" +
//                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}\n" +
//                "        }\n" +
//                "      },\n" +
//                "      \"currentPrice\" : {\n" +
//                "        \"type\": \"keyword\",\n" +
//                "        \"ignore_above\": 64\n" +
//                "      }\n" +
//                "   }\n" +
//                "}";
//        clientService.createIndex("idx_item", settings, mappings);
//    }
//
//    @Test
//    public void createIdx() throws IOException {
//        String settings = cn.hutool.json.JSONUtil.toJsonStr(  "{      \"number_of_shards\" : \"2\",      \"number_of_replicas\" : \"0\"   }");
//        String mappings = cn.hutool.json.JSONUtil.toJsonStr("" +
//                "{" +
//                "    \"properties\": {" +
//                "      \"itemId\" : {" +
//                "        \"type\": \"keyword\"," +
//                "        \"ignore_above\": 64" +
//                "      }," +
//                "      \"urlId\" : {" +
//                "        \"type\": \"keyword\"," +
//                "        \"ignore_above\": 64" +
//                "      }," +
//                "      \"sellAddress\" : {" +
//                "        \"type\": \"text\"," +
//                "        \"analyzer\": \"ik_max_word\", " +
//                "        \"search_analyzer\": \"ik_smart\"," +
//                "        \"fields\": {" +
//                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}" +
//                "        }" +
//                "      }," +
//                "      \"courierFee\" : {" +
//                "        \"type\": \"text\" "+
//                "      }," +
//                "      \"promotions\" : {" +
//                "        \"type\": \"text\"," +
//                "        \"analyzer\": \"ik_max_word\", " +
//                "        \"search_analyzer\": \"ik_smart\"," +
//                "        \"fields\": {" +
//                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}" +
//                "        }" +
//                "      }," +
//                "      \"originalPrice\" : {" +
//                "        \"type\": \"keyword\"," +
//                "        \"ignore_above\": 64" +
//                "      }," +
//                "      \"startTime\" : {" +
//                "        \"type\": \"date\"," +
//                "        \"format\": \"yyyy-MM-dd HH:mm:ss\"" +
//                "      }," +
//                "      \"endTime\" : {" +
//                "        \"type\": \"date\"," +
//                "        \"format\": \"yyyy-MM-dd HH:mm:ss\"" +
//                "      }," +
//                "      \"title\" : {" +
//                "        \"type\": \"text\"," +
//                "        \"analyzer\": \"ik_max_word\", " +
//                "        \"search_analyzer\": \"ik_smart\"," +
//                "        \"fields\": {" +
//                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}" +
//                "        }" +
//                "      }," +
//                "      \"serviceGuarantee\" : {" +
//                "        \"type\": \"text\"," +
//                "        \"analyzer\": \"ik_max_word\", " +
//                "        \"search_analyzer\": \"ik_smart\"," +
//                "        \"fields\": {" +
//                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}" +
//                "        }" +
//                "      }," +
//                "      \"venue\" : {" +
//                "        \"type\": \"text\"," +
//                "        \"analyzer\": \"ik_max_word\", " +
//                "        \"search_analyzer\": \"ik_smart\"," +
//                "        \"fields\": {" +
//                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}" +
//                "        }" +
//                "      }," +
//                "      \"currentPrice\" : {" +
//                "        \"type\": \"keyword\"," +
//                "        \"ignore_above\": 64" +
//                "      }" +
//                "   }" +
//                "}");
//        clientService.createIndex("idx_item_0220", settings, mappings);
//    }
//
//    @Test
//    public void batchImport() throws IOException {
//        String s = cn.hutool.json.JSONUtil.toJsonStr("[{ \"_id\": \"https://detail.tmall.com/item.htm?id=538528948719\\u0026skuId=3216546934499\",\"卖家地址\": \"上海\",\"快递费\": \"运费: 0.00元\",\"优惠活动\": \"满199减10,满299减30,满499减60,可跨店\",\"商品ID\": \"538528948719\", \"原价\": \"2290.00\", \"活动开始时间\": \"2016-11-11 00:00:00\",\"活动结束时间\": \"2016-11-11 23:59:59\",\"标题\": \"【天猫海外直营】 ReFa CARAT RAY 黎珐 双球滚轮波光美容仪\",\"服务保障\": \"正品保证;赠运费险;极速退款;七天退换\",\"会场\": \"进口尖货\",\"现价\": \"1950.00\" }," +
//                "{ \"_id\": \"https://detail.tmall.com/item.htm?id=538528948719\\u0026skuId=3216546934499\",\"卖家地址\": \"上海\",\"快递费\": \"运费: 0.00元\",\"优惠活动\": \"满199减10,满299减30,满499减60,可跨店\",\"商品ID\": \"538528948719\", \"原价\": \"2290.00\", \"活动开始时间\": \"2016-11-11 00:00:00\",\"活动结束时间\": \"2016-11-11 23:59:59\",\"标题\": \"【天猫海外直营】 ReFa CARAT RAY 黎珐 双球滚轮波光美容仪\",\"服务保障\": \"正品保证;赠运费险;极速退款;七天退换\",\"会场\": \"进口尖货\",\"现价\": \"1950.00\" },{" +
//                "    \"itemId\": \"525033055044\"," +
//                "    \"urlId\": \"https://detail.tmall.com/item.htm?id=525033055044&skuId=def\"," +
//                "    \"sellAddress\": \"湖北武汉\"," +
//                "    \"courierFee\": \"快递: 0.00\"," +
//                "    \"promotions\": \"满199减10,满299减30,满499减60,可跨店\"," +
//                "    \"originalPrice\": \"3768.00\"," +
//                "    \"startTime\": \"2016-11-01 00:00:00\"," +
//                "    \"endTime\": \"2016-11-11 23:59:59\"," +
//                "    \"title\": \"酒嗨酒 西班牙原瓶原装进口红酒蒙德干红葡萄酒6只装整箱送酒具\"," +
//                "    \"serviceGuarantee\": \"破损包退;正品保证;公益宝贝;不支持7天退换;极速退款\"," +
//                "    \"venue\": \"食品主会场\"," +
//                "    \"currentPrice\": \"151.00\"" +
//                "  }," +
//                "{ \"_id\": \"https://detail.tmall.com/item.htm?id=538528948719\\u0026skuId=3216546934499\",\"卖家地址\": \"上海\",\"快递费\": \"运费: 0.00元\",\"优惠活动\": \"满199减10,满299减30,满499减60,可跨店\",\"商品ID\": \"538528948719\", \"原价\": \"2290.00\", \"活动开始时间\": \"2016-11-11 00:00:00\",\"活动结束时间\": \"2016-11-11 23:59:59\",\"标题\": \"【天猫海外直营】 ReFa CARAT RAY 黎珐 双球滚轮波光美容仪\",\"服务保障\": \"正品保证;赠运费险;极速退款;七天退换\",\"会场\": \"进口尖货\",\"现价\": \"1950.00\" },{" +
//                "    \"itemId\": \"525033055044\"," +
//                "    \"urlId\": \"https://detail.tmall.com/item.htm?id=525033055044&skuId=def\"," +
//                "    \"sellAddress\": \"江西南昌\"," +
//                "    \"courierFee\": \"快递: 0.00\"," +
//                "    \"promotions\": \"满199减10,满299减30,满499减60,可跨店\"," +
//                "    \"originalPrice\": \"3768.00\"," +
//                "    \"startTime\": \"2016-11-01 00:00:00\"," +
//                "    \"endTime\": \"2016-11-11 23:59:59\"," +
//                "    \"title\": \"酒嗨酒 西班牙原瓶原装进口红酒蒙德干红葡萄酒6只装整箱送酒具\"," +
//                "    \"serviceGuarantee\": \"破损包退;正品保证;公益宝贝;不支持7天退换;极速退款\"," +
//                "    \"venue\": \"食品主会场\"," +
//                "    \"currentPrice\": \"151.00\"" +
//                "  }," +
//                "{ \"_id\": \"https://detail.tmall.com/item.htm?id=538528948719\\u0026skuId=3216546934499\",\"卖家地址\": \"上海\",\"快递费\": \"运费: 0.00元\",\"优惠活动\": \"满199减10,满299减30,满499减60,可跨店\",\"商品ID\": \"538528948719\", \"原价\": \"2290.00\", \"活动开始时间\": \"2016-11-11 00:00:00\",\"活动结束时间\": \"2016-11-11 23:59:59\",\"标题\": \"【天猫海外直营】 ReFa CARAT RAY 黎珐 双球滚轮波光美容仪\",\"服务保障\": \"正品保证;赠运费险;极速退款;七天退换\",\"会场\": \"进口尖货\",\"现价\": \"1950.00\" },{" +
//                "    \"itemId\": \"525033055044\"," +
//                "    \"urlId\": \"https://detail.tmall.com/item.htm?id=525033055044&skuId=def\"," +
//                "    \"sellAddress\": \"广东深圳\"," +
//                "    \"courierFee\": \"快递: 0.00\"," +
//                "    \"promotions\": \"满199减10,满299减30,满499减60,可跨店\"," +
//                "    \"originalPrice\": \"3768.00\"," +
//                "    \"startTime\": \"2016-11-01 00:00:00\"," +
//                "    \"endTime\": \"2016-11-11 23:59:59\"," +
//                "    \"title\": \"酒嗨酒 西班牙原瓶原装进口红酒蒙德干红葡萄酒6只装整箱送酒具\"," +
//                "    \"serviceGuarantee\": \"破损包退;正品保证;公益宝贝;不支持7天退换;极速退款\"," +
//                "    \"venue\": \"食品主会场\"," +
//                "    \"currentPrice\": \"151.00\"" +
//                "  }]");
//        clientService.importAll("idx_item_0220",true,s);
//    }
//
//    @Test
//    public void search() throws IOException {
//        SearchResponse search = clientService.search("title", "酒", "currentPrice",
//                "11", "159", "sellAddress", "武汉");
//        SearchHits hits = search.getHits();
//        SearchHit[] hits1 = hits.getHits();
//        for (SearchHit documentFields : hits1) {
//            log.info("搜索结果：{}",documentFields.getSourceAsString());
//        }
//    }
//
//}
