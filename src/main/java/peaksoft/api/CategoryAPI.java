package peaksoft.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.CategoryRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.category.CategoryResponse;
import peaksoft.service.CategoryService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Zholdoshov Nuradil
 * peaksoft.api
 * 17.03.2023
 **/
@RestController
@RequestMapping("/api/categories")
public class CategoryAPI {
    private final CategoryService categoryService;

    public CategoryAPI(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<String> handlerExceptions(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("An error occurred: "+e.getMessage());
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<CategoryResponse> findAl(){
        return categoryService.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse save(@RequestBody CategoryRequest request){
        return categoryService.save(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Long id){
        return categoryService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id,@RequestBody CategoryRequest request){
        return categoryService.update(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return categoryService.delete(id);
    }
}
