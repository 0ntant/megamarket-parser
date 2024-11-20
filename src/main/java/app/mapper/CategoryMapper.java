package app.mapper;

import app.entity.Category;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper
{


    public static List<Category> mapList(JsonNode json)
    {
        List<Category> categories = new ArrayList<>();

        for (JsonNode node : json.path("nodes"))
        {
            categories.add(Category.builder()
                            .collectionId(collectionId(node))
                            .parentId(parentId(node))
                            .url(url(node))
                    .build()
            );
        }

        return categories;
    }

    public static Category map(JsonNode json,String url)
    {
        return Category.builder()
                .url(url)
                .collectionId(collectionId(json
                        .path("params")
                        .path("menuNode"))
                )
                .parentId(parentId(json
                        .path("params")
                        .path("menuNode"))
                )
                .build();
    }

    private static String collectionId(JsonNode json)
    {
        return json
                .path("collection")
                .path("collectionId")
                .asText();
    }

    private static String parentId(JsonNode json)
    {
        return json
                .path("id")
                .asText();
    }


    private static String url(JsonNode json)
    {
        return json
                .path("collection")
                .path("url")
                .asText();
    }
}
