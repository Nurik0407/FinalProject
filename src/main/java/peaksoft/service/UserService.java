package peaksoft.service;

import peaksoft.dto.requests.AuthUserRequest;
import peaksoft.dto.requests.UserRequest;
import peaksoft.dto.responses.*;
import peaksoft.dto.responses.user.*;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 16.03.2023
 **/
public interface UserService {


    UserTokenResponse authenticate(AuthUserRequest userRequest);

    List<UserAllResponse> findAll(String role, String lastName);

    SimpleResponse save(UserRequest request);

    UserResponse findById(Long id);

    SimpleResponse update(Long id, UserRequest request);

    SimpleResponse deleteById(Long id);

    SimpleResponse application(UserRequest request);

    AbstractApplicationClass applications(Long id, Boolean accepted);

}
