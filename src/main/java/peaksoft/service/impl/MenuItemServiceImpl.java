package peaksoft.service.impl;

import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.MenuItemRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.menuItem.MenuItemAllResponse;
import peaksoft.dto.responses.menuItem.MenuItemResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.SubCategory;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.MenuItemService;

import java.util.HashMap;
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
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubCategoryRepository subCategoryRepository;


    public MenuItemServiceImpl(MenuItemRepository menuItemRepository,
                               RestaurantRepository restaurantRepository, SubCategoryRepository subCategoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.subCategoryRepository = subCategoryRepository;
    }


    @Transactional
    @Override
    public SimpleResponse save(MenuItemRequest request) {

        if (menuItemRepository.existsByName(request.name())) {
            return SimpleResponse.builder()
                    .status(HttpStatus.CONFLICT)
                    .message(String.format("MenuItem with name: %s already exists!", request.name()))
                    .build();
        }

        if (request.price() <= 0) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Price not cannot be negative!")
                    .build();
        }

        Restaurant restaurant = restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Restaurant with id: %s not found", request.restaurantId())));

        SubCategory subCategory = subCategoryRepository.findById(request.subCategoryId())
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("SubCategory with id: %s not found", request.subCategoryId())));

        MenuItem menuItem = MenuItem.builder()
                .name(request.name())
                .image(request.image())
                .price(request.price())
                .description(request.description())
                .isVegetarian(request.isVegetarian())
                .restaurant(restaurant)
                .subCategory(subCategory)
                .build();
        menuItemRepository.save(menuItem);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("MenuItem with name: %s successfully saved", menuItem.getName()))
                .build();
    }

    @Override
    public List<MenuItemAllResponse> findAll() {
        return menuItemRepository.findAllMenuItem();
    }

    @Override
    public MenuItemResponse findById(Long id) {

        return menuItemRepository.findMenuItemResponseById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("MenuItem with id: %s not found!", id)));
    }

    @Override
    public SimpleResponse update(Long id, MenuItemRequest request) {

        if (menuItemRepository.existsByNameAndIdNot(request.name(), id)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.CONFLICT)
                    .message(String.format("MenuItem with name: %s already exists", request.name()))
                    .build();
        }

        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("MenuItem with id: %s not found!", id)));
        menuItem.setName(request.name());
        menuItem.setImage(request.image());
        menuItem.setDescription(request.description());
        menuItem.setPrice(request.price());
        menuItem.setIsVegetarian(request.isVegetarian());
        menuItemRepository.save(menuItem);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("MenuItem with id: %s successfully updated", id))
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        if (!menuItemRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("MenuItem with id: " + id + " not found!")
                    .build();
        }
        menuItemRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("MenuItem with id: %s successfully deleted", id))
                .build();
    }


    /**
     * Dodelat
     **/
    @Override
    public List<MenuItemAllResponse> findAll(String global, String sort, Boolean isVegan) {

        if (sort.equalsIgnoreCase("asc")) {
            return menuItemRepository.findAllByGlobalAndSortAndIsVeganAsc(global, isVegan);
        } else if (sort.equalsIgnoreCase("desc")) {
            return menuItemRepository.findAllByGlobalAndSortAndIsVeganDesc(global, isVegan);
        } else {
            return findAll();
        }
    }
}
