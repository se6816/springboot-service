package com.example.security.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.security.config.auth.PrincipalDetails;
import com.example.security.domain.User;
import com.example.security.repository.userRepository;

@Controller
public class indexController {
	
	@Autowired
	userRepository userRepository;
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@PostConstruct
	public void init() {
		User user=new User();
		user.setUsername("user");
		user.setPassword("1234");
		user.setRole("ROLE_USER");
		String pw=user.getPassword();
		String newPw=encoder.encode(pw);
		user.setPassword(newPw);
		userRepository.save(user);
	}
	@GetMapping({"/","/index"})
	public String index() {
		return "index";
	}
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	@GetMapping("/manager")
	public  @ResponseBody String manager() {
		return "manager";
	}
	@GetMapping("/admin")
	public  @ResponseBody String admin() {
		return "admin";
	}
	@GetMapping("/loginForm")
	public  String login() {
		return "loginForm";
	}
	@PostMapping("/join")
	public String joinTry(User user) {
	
		user.setRole("ROLE_USER");
		String pw=user.getPassword();
		String newPw=encoder.encode(pw);
		user.setPassword(newPw);
		userRepository.save(user);
		return "redirect:/loginForm";
	}
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	@GetMapping("/joinProc")
	public  @ResponseBody String joinProc() {
		return "joinProc";
	}
	@GetMapping("/test/login")
	public @ResponseBody String testLogin(@AuthenticationPrincipal PrincipalDetails principal,Authentication auth) {
		PrincipalDetails principals=(PrincipalDetails)auth.getPrincipal();
		System.out.println("auth : "+principals.getUser());
		System.out.println("auth : "+principal.getUser());    // 즉 다운캐스팅을 통하거나 @AuthenticationPrincipal을 통해서 얻은 객체는 같은 결과를 도출한다.
		return "세션 정보 조회";
	}
	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOauthLogin(@AuthenticationPrincipal OAuth2User principal,Authentication auth) {
		OAuth2User principals=(OAuth2User)auth.getPrincipal();
		System.out.println("auth : "+principals.getAttributes());
		System.out.println("auth : "+principal.getAttributes());   
		return "세션 정보 조회";
	}
	
}
