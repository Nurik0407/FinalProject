package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.StopListRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.stopList.StopListResponse;
import peaksoft.service.StopListService;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.api
 * 18.03.2023
 **/
@RestController
@RequestMapping("/api/stopLists")
public class StopListAPI {
    private final StopListService stopListService;

    public StopListAPI(StopListService stopListService) {
        this.stopListService = stopListService;
    }


    @PreAuthorize("permitAll()")
    @GetMapping
    public List<StopListResponse> findAll() {
        return stopListService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid StopListRequest request) {
        return stopListService.save(request);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public StopListResponse findById(@PathVariable Long id) {
        return stopListService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid StopListRequest request) {
        return stopListService.update(id, request);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return stopListService.delete(id);
    }
}
