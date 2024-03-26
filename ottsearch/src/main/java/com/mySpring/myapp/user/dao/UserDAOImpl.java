package com.mySpring.myapp.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mySpring.myapp.user.dto.UserDTO;

@Repository
//public class UserDAOImpl {
	public class UserDAOImpl implements UserDAO {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    
	@Autowired
	private SqlSession sqlSession;	
	
	public void insertUser(UserDTO userDTO) throws DataAccessException {
		try {
	        sqlSession.insert("mapper.user.insertNewUser", userDTO);
	    } catch (DataAccessException e) {
	        // 예외 처리 로직
	        logger.error("Failed to insert user: " + e.getMessage());
	        throw e;
	    }
	}
	
	//emailCheck 받아오기
		@Override
		public  String emailCheck(String email) {
			int check = 0;
			check = this.sqlSession.selectOne("mapper.user.emailCheck", email);
			if (check == 0) {
				return "이메일 중복아님";
			} else {
				return "중복";
			}
		}
		
	    @Override
	    public List<UserDTO> getAllusers() throws DataAccessException {
	        return sqlSession.selectList("mapper.user.getAllusers");
	    }
		
		//nickCheck 받아오기
		@Override
		public  String nickCheck(String nickname) {
			int checkN = 0;
			checkN = this.sqlSession.selectOne("mapper.user.nickCheck", nickname);
			if (checkN == 0) {
				return "닉네임 중복아님";
			} else {
				return "닉네임 중복";
			}
		}
		
		//numbCheck 받아오기
		@Override
		public  String numbCheck(String phoneNumber) {
			int checkNumb = 0;
			checkNumb = this.sqlSession.selectOne("mapper.user.numbCheck", phoneNumber);
			if (checkNumb == 0) {
				return "전화번호 중복아님";
			} else {
				return "전화번호 중복";
			}
		}
		
		@Override
		public UserDTO loginById(UserDTO userDTO ) throws DataAccessException {
		    UserDTO user = sqlSession.selectOne("mapper.user.loginById", userDTO);
		    if (user != null && user.getIsDeleted() == 'N') {
		        return user;
		    }
		    return null;
		}
		
		@Override
		public UserDTO getUserByUserId(int userId) {
			UserDTO vo = sqlSession.selectOne("mapper.user.selectUserInfoByUserId", userId);
			System.out.println("Retrieved user: " + vo); // 로그 출력
			return vo;
		}
		
	    @Override
	    public void updateUser(UserDTO userDTO) throws DataAccessException {
	        sqlSession.update("mapper.user.updateUser", userDTO);
	    }
		
	    @Override
	    public void deleteUser(int userId) throws DataAccessException {
	        try {
	            sqlSession.update("mapper.user.deleteUser", userId);
	        } catch (DataAccessException e) {
	            logger.error("회원 탈퇴 처리 실패: " + e.getMessage());
	            throw e;
	        }
	    }
	    
	    @Override
	    public List<UserDTO> getAllUserList(int startRow, int endRow) throws DataAccessException {
	        Map<String, Object> paramMap = new HashMap<>();
	        paramMap.put("startRow", startRow);
	        paramMap.put("endRow", endRow);
	    	return sqlSession.selectList("mapper.user.getAllUserList", paramMap);
	    }

	    @Override
	    public int countUsers() throws Exception {
	    	return sqlSession.selectOne("mapper.user.countUsers"); 
	    }
	    
		@Override
		public void updateUserRole(int userId, String role) throws Exception {
	        Map<String, Object> paramMap = new HashMap<>();
	        paramMap.put("userId", userId);
	        paramMap.put("role", role);
			sqlSession.update("mapper.user.updateUserRole", paramMap);
		}
}
