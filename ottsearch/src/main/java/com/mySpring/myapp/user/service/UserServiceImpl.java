package com.mySpring.myapp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.mySpring.myapp.user.dao.UserDAO;
import com.mySpring.myapp.user.dto.UserDTO;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private final UserDAO userDAO;

	@Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    @Override
    public boolean checkEmailAvailability(String email) {
    	String result = userDAO.emailCheck(email);
        return "이메일 중복아님".equals(result);
    }

    @Override
    public boolean checkNickAvailability(String nickname) {
    	String result = userDAO.nickCheck(nickname);
        return "닉네임 중복아님".equals(result);
    }

    @Override
    public boolean checkNumAvailability(String phoneNumber) {
    	String result = userDAO.numbCheck(phoneNumber);
        return "전화번호 중복아님".equals(result);
    }

    @Override
    public boolean create(UserDTO userDTO) {
        try {
            userDAO.insertUser(userDTO);
            return true;
        } catch (DataAccessException e) {
            logger.error("Failed to create user due to DataAccessException: " + e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Failed to create user due to an unexpected error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public UserDTO login(UserDTO userDTO) throws Exception {
        UserDTO storedUser = userDAO.loginById(userDTO);
        if (storedUser != null && storedUser.getPassword().equals(userDTO.getPassword())) {
            return storedUser;
        }
        return null;
    }

	
    @Override
    public boolean loginCheck(String email, String password) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        UserDTO storedUser = userDAO.loginById(userDTO);
        return storedUser != null && storedUser.getPassword().equals(userDTO.getPassword());
    }
	
    @Override
    public UserDTO getUserByUserId(int userId) throws Exception {
        return userDAO.getUserByUserId(userId);
    }
    
    @Override
    public boolean updateUser(UserDTO userDTO) {
        try {
            userDAO.updateUser(userDTO);
            return true;
        } catch (DataAccessException e) {
            logger.error("사용자 업데이트 실패: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean deleteUser(int userId) {
        try {
            userDAO.deleteUser(userId); // UserDAO의 deleteUser 메소드 호출
            return true;
        } catch (DataAccessException e) {
            logger.error("회원 탈퇴 처리 실패: " + e.getMessage());
            return false;
        }
    }
    
	@Override
	public List<UserDTO> getAllUserList(int startRow, int endRow) throws Exception {
		return userDAO.getAllUserList(startRow, endRow);
	}
	
	@Override
	public int countUsers() throws Exception {
		return userDAO.countUsers();
	}
	
	@Override
	public boolean updateUserRole(int userId, String role) throws Exception {
	    try {
	        	userDAO.updateUserRole(userId, role);
	            return true;
        } catch (Exception e) {
	          return false;
        }
	}

}