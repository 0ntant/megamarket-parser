package app.service;

import app.entity.Category;
import app.mapper.CategoryMapper;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImp implements CategoryService {
    @Override
    public Category getCategory(JsonNode json, String url)
    {
        return CategoryMapper.map(json, url);
    }

    @Override
    public List<Category> getSubCategories(JsonNode json)
    {
        return CategoryMapper.mapList(json);
    }
}
