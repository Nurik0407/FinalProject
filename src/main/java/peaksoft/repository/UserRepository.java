package peaksoft.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.responses.user.UserAllResponse;
import peaksoft.dto.responses.user.UserResponse;
import peaksoft.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByPhoneNumber(String phoneNumber);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("SELECT NEW peaksoft.dto.responses.user.UserAllResponse(u.id,concat(u.lastName,' ',u.firstName),u.email,u.role) FROM User u")
    List<UserAllResponse> findAllUserResponses();

    @Query("SELECT NEW peaksoft.dto.responses.user.UserAllResponse(u.id,concat(u.lastName,' ',u.firstName),u.email,u.role) FROM User u WHERE LOWER(u.role) ILIKE  LOWER(:role) ")
    List<UserAllResponse> findAllUserResponsesByRole(String role);

    @Query("SELECT NEW peaksoft.dto.responses.user.UserAllResponse(u.id,concat(u.lastName,' ',u.firstName),u.email,u.role) " +
            "FROM User u WHERE u.lastName ILIKE (:lastName)")
    List<UserAllResponse> findAllUserResponsesByLastName(String lastName);
    @Query("SELECT NEW peaksoft.dto.responses.user.UserResponse(u.id,concat(u.lastName,' ',u.firstName),u.dateOfBirth,u.email,u.phoneNumber,u.role,u.experience) " +
            "FROM User u WHERE u.id=:id")
    Optional<UserResponse> findUserResponseById(Long id);
    List<User> findAllByAcceptedFalse();
    @Query("SELECT NEW peaksoft.dto.responses.user.UserAllResponse(u.id,concat(u.lastName,' ',u.firstName),u.email,u.role)" +
            " FROM User u WHERE u.accepted=false")
    List<UserAllResponse> findAllUserResponsesByAcceptedFalse();

}