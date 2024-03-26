package com.mySpring.myapp.user.dao;


import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.mySpring.myapp.user.dto.UserDTO;

@Repository
public interface UserDAO {
	String emailCheck(String email);
	String nickCheck(String nickname);
	String numbCheck(String phoneNumber);
	
	public void insertUser(UserDTO userDTO) throws Exception;
	List<UserDTO> getAllusers() throws Exception;
	
	public UserDTO loginById(UserDTO userDTO);
	 
	UserDTO getUserByUserId(int userId);
	void updateUser(UserDTO userDTO) throws DataAccessException;
	void deleteUser(int userId) throws DataAccessException;

	List<UserDTO> getAllUserList(int startRow, int endRow) throws Exception;
	int countUsers() throws Exception;
    void updateUserRole(int userId, String role) throws Exception;
}