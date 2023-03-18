package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.responses.subCategory.SubCategoryResponse;
import peaksoft.dto.responses.subCategory.SubCategoryResponsesByCategory;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @Query("select new peaksoft.dto.responses.subCategory.SubCategoryResponse(s.name) from SubCategory s")
    List<SubCategoryResponse> findAllSubCategoryResponse();

    boolean existsByName(String name);

    @Query("select new peaksoft.dto.responses.subCategory.SubCategoryResponse(s.name) from SubCategory s where s.id=:id")
    Optional<SubCategoryResponse> findSubCategoryResponseById(Long id);

    boolean existsByNameAndIdNot(String name, Long id);

    List<SubCategoryResponse> findAllByCategoryIdOrderByName(Long id);

    @Query("select new peaksoft.dto.responses.subCategory.SubCategoryResponsesByCategory(c.name,s.name) from Category c join c.subCategories s")
    List<SubCategoryResponsesByCategory> findAllGrouping();
}