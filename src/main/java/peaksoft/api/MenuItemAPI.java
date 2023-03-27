package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.MenuItemRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.menuItem.MenuItemAllResponse;
import peaksoft.dto.responses.menuItem.MenuItemResponse;
import peaksoft.dto.responses.menuItem.PaginationResponse;
import peaksoft.service.MenuItemService;

import java.util.List;


/**
 * Zholdoshov Nuradil
 * peaksoft.api
 * 17.03.2023
 **/
@RestController
@RequestMapping("/api/menuItems")
public class MenuItemAPI {

    private final MenuItemService menuItemService;

    public MenuItemAPI(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }


    @PreAuthorize("permitAll()")
    @GetMapping
    public List<MenuItemAllResponse> findAll(@RequestParam(required = false) String global,
                                             @RequestParam(required = false,defaultValue = "asc") String sort,
                                             @RequestParam(required = false) Boolean isVegan) {

            return menuItemService.findAll(global, sort, isVegan);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid MenuItemRequest request) {
        return menuItemService.save(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @GetMapping("/{id}")
    public MenuItemResponse findById(@PathVariable Long id) {
        return menuItemService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid MenuItemRequest request) {
        return menuItemService.update(id, request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return menuItemService.delete(id);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/pagination")
    public PaginationResponse pagination(@RequestParam int page,
                                         @RequestParam int size) {
        return menuItemService.getAllPagination(page,size);
    }
}
