package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import peaksoft.dto.responses.menuItem.MenuItemAllResponse;
import peaksoft.dto.responses.menuItem.MenuItemResponse;
import peaksoft.entity.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    boolean existsByName(String name);

    @Query("select new peaksoft.dto.responses.menuItem.MenuItemAllResponse(" +
            "m.id,m.subCategory.category.name,m.subCategory.name,m.name,m.price) " +
            "from MenuItem m left join m.stopList s where s.id is null or s.date <> current_date ")
    List<MenuItemAllResponse> findAllMenuItem();

    @Query("select new peaksoft.dto.responses.menuItem.MenuItemResponse(" +
            "m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where m.id=:id")
    Optional<MenuItemResponse> findMenuItemResponseById(Long id);

    boolean existsByNameAndIdNot(String name, Long id);


    @Query("select new peaksoft.dto.responses.menuItem.MenuItemAllResponse(m.id,m.subCategory.category.name,m.subCategory.name,m.name,m.price) from MenuItem m " +
            "left join m.stopList s where (m.name ilike %:global% or m.subCategory.name ilike %:global% or m.subCategory.category.name ilike %:global%) " +
            "and (:isVegan is null or m.isVegetarian = :isVegan) and s.id is null or s.date <> current_date order by m.price asc ")
    List<MenuItemAllResponse> findAllByGlobalAndSortAndIsVeganAsc(@Param("global") String global,
                                                                  @Param("isVegan") Boolean isVegan);

    @Query("select new peaksoft.dto.responses.menuItem.MenuItemAllResponse(m.id,m.subCategory.category.name,m.subCategory.name,m.name,m.price) from MenuItem m " +
            "left join m.stopList s where (m.name ilike %:global% or m.subCategory.name ilike %:global% or m.subCategory.category.name ilike %:global%) " +
            "and (:isVegan is null or m.isVegetarian = :isVegan) and s.id is null or s.date <> current_date order by  m.price desc ")
    List<MenuItemAllResponse> findAllByGlobalAndSortAndIsVeganDesc(@Param("global") String global,
                                                                   @Param("isVegan") Boolean isVegan);

    @Query("select new peaksoft.dto.responses.menuItem.MenuItemAllResponse(m.id,m.subCategory.category.name,m.subCategory.name,m.name,m.price) from MenuItem m " +
            "left join m.stopList s where s.id is null or s.date <> current_date")
    Page<MenuItemAllResponse> findAllBy(Pageable pageable);


    @Query("select new peaksoft.dto.responses.menuItem.MenuItemAllResponse(" +
            "m.id,m.subCategory.category.name,m.subCategory.name,m.name,m.price) " +
            "from MenuItem m join m.cheques c where c.id=:id")
    List<MenuItemAllResponse> getAllMenuItemsByChequeId(Long id);
}