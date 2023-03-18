package peaksoft.service;

import peaksoft.dto.requests.CategoryRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.category.CategoryResponse;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 17.03.2023
 **/
public interface CategoryService {
    List<CategoryResponse> findAll();

    CategoryResponse findById(Long id);

    SimpleResponse save(CategoryRequest request);

    SimpleResponse update(Long id,CategoryRequest request);

    SimpleResponse delete(Long id);

}
