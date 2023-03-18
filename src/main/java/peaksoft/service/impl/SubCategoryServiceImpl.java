package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.SubCategoryRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.subCategory.SubCategoryResponse;
import peaksoft.dto.responses.subCategory.SubCategoryResponsesByCategory;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.SubCategoryService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.impl
 * 17.03.2023
 **/
@Service
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;

    }

    @Override
    public List<SubCategoryResponse> findAll(Long id) {
        if (id == null) {
            return subCategoryRepository.findAllSubCategoryResponse();
        }else {
            return subCategoryRepository.findAllByCategoryIdOrderByName(id);
        }
    }


    @Override
    public SimpleResponse save(SubCategoryRequest request) {

        if (subCategoryRepository.existsByName(request.name())) {
            return SimpleResponse.builder()
                    .status(HttpStatus.CONFLICT)
                    .message(String.format("SubCategory with name: %s already exists!", request.name()))
                    .build();
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Category with id: %s not found", request.categoryId())));

        SubCategory subCategory = SubCategory.builder()
                .name(request.name())
                .category(category)
                .build();
        subCategoryRepository.save(subCategory);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("SubCategory with name %s successfully saved", subCategory.getName()))
                .build();
    }

    @Override
    public SubCategoryResponse findById(Long id) {
        return subCategoryRepository.findSubCategoryResponseById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("SubCategory with id: %s not found!", id)));
    }

    @Override
    public SimpleResponse update(Long id, SubCategoryRequest request) {

        if (subCategoryRepository.existsByNameAndIdNot(request.name(), id)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.CONFLICT)
                    .message(String.format("SubCategory with name: %s already exists", request.name()))
                    .build();
        }

        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("SubCategory with id: %s not found!", id)));

        subCategory.setName(request.name());
        subCategoryRepository.save(subCategory);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("SubCategory with name: %s successfully saved!", request.name()))
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        if (!subCategoryRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(String.format("SubCategory with id: %s not found!", id))
                    .build();
        }
        subCategoryRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("SubCategory with id: %s successfully deleted", id))
                .build();
    }

    @Override
    public Map<String,List<SubCategoryResponsesByCategory>> groupingByCategory() {
        List<SubCategoryResponsesByCategory> grouping = subCategoryRepository.findAllGrouping();
        return grouping.stream().collect(Collectors.groupingBy(SubCategoryResponsesByCategory::categoryName));
    }
}
