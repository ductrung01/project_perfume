package com.dan.shoe.perfume.services;

import com.dan.shoe.perfume.models.enums.RoleName;
import com.dan.shoe.perfume.dtos.requests.ChangePasswordForm;
import com.dan.shoe.perfume.dtos.requests.UpdateProfile;
import com.dan.shoe.perfume.dtos.responses.ResponseMessage;
import com.dan.shoe.perfume.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User save(User user);
    User saveByAdmin(User user);
    Page<User> getAllUser(Pageable pageable);
    Page<User> searchUserByKeyword(String keyword, Pageable pageable);
    ResponseMessage changePassword(String username, ChangePasswordForm changePasswordForm);
    User updateUser(User user, String username);
    boolean isEnableUser(String username);
    boolean verify(String verificationCode);
    ResponseMessage updateProfile(UpdateProfile updateProfile, String username);
    void updateVerificationCode(String username, String verificationCode);
    Page<User> getUserByRoleName(RoleName roleName, String keyword, String enabled, Pageable pageable);
    ResponseMessage updateStatusUser(Long id);
    Boolean existByUsernameAndUsernameNot(String username);
    Boolean existByEmailAndEmailNot(String email);
    ResponseMessage deleteUser(Long id);
    User getUserById(Long id);
    User forgotPassword(String email);
}
