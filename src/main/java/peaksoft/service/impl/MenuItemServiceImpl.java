package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.MenuItemRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.menuItem.MenuItemAllResponse;
import peaksoft.dto.responses.menuItem.MenuItemResponse;
import peaksoft.dto.responses.menuItem.PaginationResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.SubCategory;
import peaksoft.exceptions.AlreadyExistException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.MenuItemService;

import java.util.List;

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
                .orElseThrow(() -> new NotFoundException(
                        String.format("Restaurant with id: %s not found", request.restaurantId())));

        SubCategory subCategory = subCategoryRepository.findById(request.subCategoryId())
                .orElseThrow(() -> new NotFoundException(
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
                .orElseThrow(() -> new NotFoundException(
                        String.format("MenuItem with id: %s not found!", id)));
    }

    @Override
    public SimpleResponse update(Long id, MenuItemRequest request) {

        if (menuItemRepository.existsByNameAndIdNot(request.name(), id)) {
            throw new AlreadyExistException(String.format("MenuItem with name: %s already exists", request.name()));
        }

        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
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
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("MenuItem with id: %s not found!", id)));

        List<Cheque> cheques = menuItem.getCheques();
        if (cheques != null) {
            for (int i = 0; i < cheques.size(); i++) {
                menuItem.deleteCheque(cheques.get(i));
            }
        }

        menuItemRepository.deleteById(id);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("MenuItem with id: %s successfully deleted", id))
                .build();
    }


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

    @Override
    public PaginationResponse getAllPagination(int page, int size) {

        Pageable pageable
                = PageRequest.of(page - 1, size, Sort.by("price"));

        Page<MenuItemAllResponse> userPage = menuItemRepository.findAllBy(pageable);

        return PaginationResponse.builder()
                .users(userPage.getContent())
                .currentPage(userPage.getTotalPages())
                .pageSize(userPage.getNumber() + 1)
                .build();
    }
}
