package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.AuthUserRequest;
import peaksoft.dto.requests.UserRequest;
import peaksoft.dto.responses.*;
import peaksoft.dto.responses.user.*;
import peaksoft.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Zholdoshov Nuradil
 * peaksoft.api
 * 16.03.2023
 **/
@RestController
@RequestMapping("/api/users")
public class UserApi {
    private final UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handlerException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("An error occurred: " + e.getMessage());
    }

    /**
     * LOGIN
     **/
    @PostMapping("/login")
    public UserTokenResponse login(@RequestBody @Valid AuthUserRequest userRequest) {
        return userService.authenticate(userRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public List<UserAllResponse> finaAllUsers(@RequestParam(required = false) String role,
                                              @RequestParam(required = false) String lastName) {
        return userService.findAll(role, lastName);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveUser(@RequestBody @Valid UserRequest request) {

        return userService.save(request);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        return userService.update(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return userService.deleteById(id);
    }


    @PostMapping("/application")
    public SimpleResponse application(@RequestBody @Valid UserRequest request) {
        return userService.application(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/applications")
    public AbstractApplicationClass applications(@RequestParam(required = false) Long id,
                                                 @RequestParam(required = false) Boolean accepted) {
        return userService.applications(id, accepted);
    }


}
