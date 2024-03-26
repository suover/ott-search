package com.mySpring.myapp.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mySpring.myapp.user.dto.UserDTO;
import com.mySpring.myapp.user.service.UserService;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	 
	// 회원가입 페이지 이동
	@RequestMapping(value = "/signupForm", method = RequestMethod.GET)
	public String signup() throws Exception {
		return "signupForm";
	}

	//회원가입 폼 제출
	@RequestMapping(value = "/user/signup", method = RequestMethod.POST) 
	public ModelAndView createPost(UserDTO userDTO) {
	    ModelAndView mav = new ModelAndView();
	    userDTO.setSignupDate(new Date()); // 현재 날짜 설정

	    boolean result = userService.create(userDTO); // 회원가입 시도

	    if (result) {
	        mav.addObject("message", "success");
	        mav.setViewName("redirect:/loginForm"); // 성공시 로그인 페이지로 리다이렉트
	    } else {
	        mav.addObject("message", "fault");
	        mav.setViewName("signupForm"); // 실패시 동일한 회원가입 페이지로
	    }
	    return mav;
	}
	
	
	@RequestMapping(value = "/loginForm", method = RequestMethod.GET)
	public String loginForm() {
		return "loginForm";
	}
	
	//로그인 폼
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("user") UserDTO user, RedirectAttributes rAttr,
	        HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView mav = new ModelAndView();

	    try {
	        UserDTO userDTO = userService.login(user);
	        if (userDTO != null) {
	            // 로그인 성공
	            HttpSession session = request.getSession();
	            session.setAttribute("user", userDTO);
	            session.setAttribute("isLogOn", true);
	            session.setAttribute("userRole", userDTO.getUserRole());
	            session.setAttribute("userId", userDTO.getUserId());
	            
	            // 다른 페이지에서 로그인 페이지로 리디렉션된 경우를 처리
	            String action = (String) session.getAttribute("action");
	            if (action != null) {
	                session.removeAttribute("action");
	                mav.setViewName("redirect:" + action);
	            } else {
	                mav.setViewName("redirect:/ottSearch"); // 로그인 후 이동할 페이지
	            }
	        } else {
	            // 로그인 실패
	            rAttr.addFlashAttribute("result", "loginFailed");
	            mav.setViewName("redirect:/loginForm");
	        }
	    } catch (Exception e) {
	        // 예외 처리
	        rAttr.addFlashAttribute("result", "loginError");
	        mav.setViewName("redirect:/loginForm");
	    }

	    return mav;
	}
	
    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "redirect:/ottSearch"; // 로그아웃 후 이동할 페이지
    }
	
		//이메일 중복확인
		@ResponseBody
		@RequestMapping(value = "/user/check-email", method = RequestMethod.POST)
		public HashMap<String, Boolean> checkEmail(@RequestParam("email") String email) {

			HashMap<String, Boolean> response = new HashMap<String, Boolean>();
			try {
				boolean isEmailAvailable = userService.checkEmailAvailability(email);
				System.out.println(email);

				response.put("isEmailAvailable", isEmailAvailable);
				return response;
			} catch (Exception e) {

				System.out.println(e);
				return response;
			}
		}
		
		//닉네임 중복확인
		@ResponseBody
		@RequestMapping(value = "/user/check-nick", method = RequestMethod.POST)
		public HashMap<String, Boolean> checkNick(@RequestParam("nickname") String nickname) {

			HashMap<String, Boolean> response = new HashMap<String, Boolean>();
			try {

				boolean isNickAvailable = userService.checkNickAvailability(nickname);
				System.out.println(nickname + "닉네임확인");

				response.put("isNickAvailable", isNickAvailable);

				return response;
			} catch (Exception e) {

				System.out.println(e);
				return response;
			}
		}
		
		//휴대폰 번호 중복확인
		@ResponseBody
		@RequestMapping(value = "/user/check-mobile", method = RequestMethod.POST)
		public HashMap<String, Boolean> checkPhone(@RequestParam("phoneNumber") String phoneNumber) {

			HashMap<String, Boolean> response = new HashMap<String, Boolean>();
			try {

				boolean isNumAvailable = userService.checkNumAvailability(phoneNumber);

				response.put("isNumAvailable", isNumAvailable);

				return response;
			} catch (Exception e) {

				System.out.println(e);
				return response;
			}
		}
		
		// 마이페이지 요청 처리
		@RequestMapping(value = "/user/myPage", method = RequestMethod.GET)
		public ModelAndView myPage(HttpServletRequest request) throws Exception {
		    HttpSession session = request.getSession();
		    Integer userId = (Integer) session.getAttribute("userId"); // 세션에서 userId 가져오기
		    if (userId == null) {
		        // 사용자가 로그인하지 않은 경우 처리
		        return new ModelAndView("redirect:/loginForm");
		    }

		    UserDTO userDTO = userService.getUserByUserId(userId); // userId를 사용하여 사용자 정보 조회
		    ModelAndView mav = new ModelAndView("user/myPage");
		    mav.addObject("user", userDTO); // 사용자 정보를 뷰에 추가
		    return mav;
		}
		
		// 회원정보 수정
		@PostMapping("/user/update")
		@ResponseBody
		public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {
		    HttpSession session = request.getSession();
		    Integer sessionUserId = (Integer) session.getAttribute("userId");

		    if (sessionUserId == null || !sessionUserId.equals(userDTO.getUserId())) {
		        return new ResponseEntity<>("권한이 없습니다", HttpStatus.UNAUTHORIZED);
		    }

		    try {
		        UserDTO currentUser = userService.getUserByUserId(sessionUserId);

		        if (!currentUser.getNickname().equals(userDTO.getNickname()) &&
		            !userService.checkNickAvailability(userDTO.getNickname())) {
		            return new ResponseEntity<>("닉네임이 이미 사용 중입니다", HttpStatus.BAD_REQUEST);
		        }

		        if (!currentUser.getPhoneNumber().equals(userDTO.getPhoneNumber()) &&
		            !userService.checkNumAvailability(userDTO.getPhoneNumber())) {
		            return new ResponseEntity<>("전화번호가 이미 사용 중입니다", HttpStatus.BAD_REQUEST);
		        }

		        if (userService.updateUser(userDTO)) {
		            return new ResponseEntity<>("사용자 정보가 성공적으로 업데이트되었습니다", HttpStatus.OK);
		        } else {
		            return new ResponseEntity<>("사용자 정보 업데이트에 실패했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    } catch (Exception e) {
		        logger.error("사용자 정보 조회 실패: " + e.getMessage());
		        return new ResponseEntity<>("사용자 정보 조회 중 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}
		
		// 회원탈퇴
		@PostMapping("/user/delete")
		@ResponseBody
		public ResponseEntity<String> deleteUser(@RequestParam("userId") Integer userId, HttpServletRequest request) {
		    HttpSession session = request.getSession();
		    Integer sessionUserId = (Integer) session.getAttribute("userId");

		    if (sessionUserId == null || !sessionUserId.equals(userId)) {
		        return new ResponseEntity<>("권한이 없습니다", HttpStatus.UNAUTHORIZED);
		    }

		    try {
		        boolean result = userService.deleteUser(userId); // 회원탈퇴 처리 서비스 호출

		        if (result) {
		            session.invalidate(); // 세션 무효화
		            return new ResponseEntity<>("회원 탈퇴 처리가 완료되었습니다", HttpStatus.OK);
		        } else {
		            return new ResponseEntity<>("회원 탈퇴 처리에 실패했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    } catch (Exception e) {
		        logger.error("회원 탈퇴 처리 실패: " + e.getMessage());
		        return new ResponseEntity<>("회원 탈퇴 처리 중 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}
		
		//관리자 페이지
		@RequestMapping(value = "/user/adminPage", method = RequestMethod.GET)
		public ModelAndView adminPage(
				@RequestParam(defaultValue = "1") int page, 
				@RequestParam(defaultValue = "20") int pageSize) throws Exception {
			
			ModelAndView mav = new ModelAndView();
			
			int totalCountUser = userService.countUsers();
			int totalPageNum = (int)Math.ceil((double)totalCountUser/pageSize);
			
	    	int startRow = (page - 1) * pageSize + 1;
	    	int endRow = startRow + pageSize - 1; 
			
			List<UserDTO> userList = userService.getAllUserList(startRow, endRow);
			mav.addObject("userList", userList);
			
	    	mav.addObject("contentList", userList);
			mav.addObject("currentPage", page);
			mav.addObject("totalPages", totalPageNum);
			mav.addObject("pageSize", pageSize);
			mav.addObject("totalContents", totalCountUser);

			return mav;
		}
		
		@PostMapping("/updateRole/{userId}")
		@ResponseBody
		public HashMap<String, Boolean> changeUserRole(@PathVariable("userId") int userId, 
		                                                @RequestParam("role") String role) {
		    HashMap<String, Boolean> response = new HashMap<>();
		    try {
		        boolean isUpdate = userService.updateUserRole(userId, role);
		        response.put("isUpdate", isUpdate);
		        return response;
		    } catch (Exception e) {
		        response.put("isUpdate", false);
		        return response;
		    }
	    }
}