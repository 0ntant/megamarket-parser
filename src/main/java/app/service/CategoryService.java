package app.service;

import app.entity.Category;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface CategoryService {
    Category getCategory(JsonNode json, String url);

    List<Category> getSubCategories(JsonNode json);
}
