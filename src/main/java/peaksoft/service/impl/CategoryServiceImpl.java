package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.CategoryRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.category.CategoryResponse;
import peaksoft.entity.Category;
import peaksoft.exceptions.AlreadyExistException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.service.CategoryService;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.impl
 * 17.03.2023
 **/
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAllCategoryResponses();
    }

    @Override
    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with ID: %s not found", id)));

        return CategoryResponse.builder()
                .name(category.getName())
                .id(category.getId())
                .build();
    }

    @Override
    public SimpleResponse save(CategoryRequest request) {

        if (categoryRepository.findAll().stream().anyMatch(s -> s.getName().equals(request.name()))) {
            throw new AlreadyExistException(String.format("Category with name: %s already exist!", request.name()));
        }

        Category category = Category.builder().name(request.name()).build();

        categoryRepository.save(category);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Category with name: %s successfully saved", request.name()))
                .build();
    }

    @Override
    public SimpleResponse update(Long id, CategoryRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with id: %s not found!", id)));

        for (Category category1 : categoryRepository.findAll()) {
            if (category1.getName().equals(request.name()) && !category1.getId().equals(category.getId())) {
                throw new AlreadyExistException(String.format("Category with name: %s already exist!", request.name()));
            }
        }

        category.setName(request.name());
        categoryRepository.save(category);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Category with id: %s successfully updated", id))
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException(String.format("Category with ID: %s not found", id));
        }
        categoryRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Category with id: %s successfully deleted", id))
                .build();
    }
}
