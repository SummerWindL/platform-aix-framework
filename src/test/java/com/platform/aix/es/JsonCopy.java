package com.platform.aix.es;

/**
 * @author Advance
 * @date 2022年06月20日 12:08
 * @since V1.0.0
 */
public class JsonCopy {
    public static void main(String[] args) {
        String s = "" +
                "{" +
                "    \"properties\": {" +
                "      \"itemId\" : {" +
                "        \"type\": \"keyword\"," +
                "        \"ignore_above\": 64" +
                "      }," +
                "      \"urlId\" : {" +
                "        \"type\": \"keyword\"," +
                "        \"ignore_above\": 64" +
                "      }," +
                "      \"sellAddress\" : {" +
                "        \"type\": \"text\"," +
                "        \"analyzer\": \"ik_max_word\", " +
                "        \"search_analyzer\": \"ik_smart\"," +
                "        \"fields\": {" +
                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}" +
                "        }" +
                "      }," +
                "      \"courierFee\" : {" +
                "        \"type\": \"text\" "+
                "      }," +
                "      \"promotions\" : {" +
                "        \"type\": \"text\"," +
                "        \"analyzer\": \"ik_max_word\", " +
                "        \"search_analyzer\": \"ik_smart\"," +
                "        \"fields\": {" +
                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}" +
                "        }" +
                "      }," +
                "      \"originalPrice\" : {" +
                "        \"type\": \"keyword\"," +
                "        \"ignore_above\": 64" +
                "      }," +
                "      \"startTime\" : {" +
                "        \"type\": \"date\"," +
                "        \"format\": \"yyyy-MM-dd HH:mm:ss\"" +
                "      }," +
                "      \"endTime\" : {" +
                "        \"type\": \"date\"," +
                "        \"format\": \"yyyy-MM-dd HH:mm:ss\"" +
                "      }," +
                "      \"title\" : {" +
                "        \"type\": \"text\"," +
                "        \"analyzer\": \"ik_max_word\", " +
                "        \"search_analyzer\": \"ik_smart\"," +
                "        \"fields\": {" +
                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}" +
                "        }" +
                "      }," +
                "      \"serviceGuarantee\" : {" +
                "        \"type\": \"text\"," +
                "        \"analyzer\": \"ik_max_word\", " +
                "        \"search_analyzer\": \"ik_smart\"," +
                "        \"fields\": {" +
                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}" +
                "        }" +
                "      }," +
                "      \"venue\" : {" +
                "        \"type\": \"text\"," +
                "        \"analyzer\": \"ik_max_word\", " +
                "        \"search_analyzer\": \"ik_smart\"," +
                "        \"fields\": {" +
                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}" +
                "        }" +
                "      }," +
                "      \"currentPrice\" : {" +
                "        \"type\": \"keyword\"," +
                "        \"ignore_above\": 64" +
                "      }" +
                "   }" +
                "}";
    }
}
