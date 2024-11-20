package app.mapper;

import app.entity.Category;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class MegamarketMapper
{
    public static int errorCode(JsonNode json)
    {
        return json.path("code").asInt();
    }

    public static List<String> parentIds(JsonNode json)
    {
        List<String> parentIds = new ArrayList<>();
        for(JsonNode node :  json.path("nodes"))
        {
            parentIds.add(node.path("id").asText());
        }
        return parentIds;
    }

    public static String goodsId(String url)
    {
        return url.replaceAll(".*-(\\d+_?\\d*)(?:/)?$", "$1");
    }

    public static String collectionId(String url)
    {
        return  url.replaceAll(".*(/catalog/[^/]+/).*", "$1");
    }
}
