package com.mySpring.myapp.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.mySpring.myapp.user.dto.UserDTO;

@Service
public interface UserService {
    boolean create(UserDTO userDTO);
    boolean checkEmailAvailability(String email);
    boolean checkNickAvailability(String nickname);
    boolean checkNumAvailability(String phoneNumber);

    UserDTO login(UserDTO user) throws Exception;
    boolean loginCheck(String email, String password);
    
    boolean updateUser(UserDTO userDTO);
    
    UserDTO getUserByUserId(int userId) throws Exception;
    boolean deleteUser(int userId);
    
    List<UserDTO> getAllUserList(int startRow, int endRow) throws Exception;
    int countUsers() throws Exception;
    boolean updateUserRole(int userId, String role) throws Exception;
}
