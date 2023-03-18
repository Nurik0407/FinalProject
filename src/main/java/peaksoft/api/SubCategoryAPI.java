package peaksoft.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.SubCategoryRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.subCategory.SubCategoryResponse;
import peaksoft.dto.responses.subCategory.SubCategoryResponsesByCategory;
import peaksoft.service.SubCategoryService;

import java.util.List;
import java.util.Map;

/**
 * Zholdoshov Nuradil
 * peaksoft.api
 * 17.03.2023
 **/
@RestController
@RequestMapping("/api/subCategories")
public class SubCategoryAPI {
    private final SubCategoryService subcategoryService;

    public SubCategoryAPI(SubCategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<SubCategoryResponse> findAll(@RequestParam(required = false) Long id) {
        return subcategoryService.findAll(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse save(@RequestBody SubCategoryRequest request) {
        return subcategoryService.save(request);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public SubCategoryResponse findById(@PathVariable Long id) {
        return subcategoryService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody SubCategoryRequest request) {
        return subcategoryService.update(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return subcategoryService.delete(id);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/grouping")
    public Map<String, List<SubCategoryResponsesByCategory>> grouping() {
        return subcategoryService.groupingByCategory();
    }


}
