package peaksoft.service;

import peaksoft.dto.requests.MenuItemRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.menuItem.MenuItemAllResponse;
import peaksoft.dto.responses.menuItem.MenuItemResponse;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 17.03.2023
 **/
public interface MenuItemService {

    SimpleResponse save(MenuItemRequest request);

    List<MenuItemAllResponse> findAll();

    MenuItemResponse findById(Long id);

    SimpleResponse update(Long id,MenuItemRequest request);
    SimpleResponse delete(Long id);


    List<MenuItemAllResponse> findAll(String global, String sort, Boolean isVegetarian);
}
