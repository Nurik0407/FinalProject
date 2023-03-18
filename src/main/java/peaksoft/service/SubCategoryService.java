package peaksoft.service;

import peaksoft.dto.requests.SubCategoryRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.subCategory.SubCategoryResponse;
import peaksoft.dto.responses.subCategory.SubCategoryResponsesByCategory;

import java.util.List;
import java.util.Map;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 17.03.2023
 **/
public interface SubCategoryService {

    List<SubCategoryResponse> findAll(Long id);

    SimpleResponse save(SubCategoryRequest request);

    SubCategoryResponse findById(Long id);

    SimpleResponse update(Long id,SubCategoryRequest request);

    SimpleResponse delete(Long id);

    Map<String,List<SubCategoryResponsesByCategory>> groupingByCategory();
}
