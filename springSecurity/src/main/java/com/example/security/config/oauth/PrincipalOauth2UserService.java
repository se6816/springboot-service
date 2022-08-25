package com.example.security.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.example.security.config.auth.PrincipalDetails;
import com.example.security.domain.User;

@Component
public class PrincipalOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	@Autowired
	private com.example.security.repository.userRepository userRepository;
	/**
	 * 구글로 받은 userRequest Data에 대한 후처리를 시행함
	 * 함수 종료시 @AuthenticationPricipal의 대상 객체가 생성됨
	 */
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {  // 인증 후처리할 떄 작용하는 메소드
		System.out.println(userRequest.getClientRegistration());
		OAuth2UserService delegate=new DefaultOAuth2UserService();
		// google login -> 로그인 확인 -> code를 리턴(OAuth-client 라이브러리가 받음) -> AccessToken 요청
		// userRequest 정보 -> loadUser 함수 -> 구글로부터 회원 프로필 조회
		OAuth2User user=delegate.loadUser(userRequest);
		OAuthUserInfo userInfo = null;
		String userNameAttribute=userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // unique한 필드 이름
        System.out.println(userRequest.getClientRegistration());
        System.out.println(user.getAttributes());
        System.out.println(userNameAttribute);
		//		String providerId=user.getAttribute(userNameAttribute);            // 계정마다의 고유값
		String provider=userRequest.getClientRegistration().getRegistrationId(); // google
		
		if(provider.equals("google")) {
			userInfo=new GoogleUserInfo(user.getAttributes());
		}
		else if(provider.equals("naver")) {
			userInfo=new NaverUserInfo(user.getAttribute("response"));
		}
		else {
			System.out.println("구글과 네이버 외에 기능을 제공하지 않습니다.");
		}
		
		String providerId=userInfo.getProviderId();
		
		String username=provider+"_"+providerId;
		String password="111";
		String email=userInfo.getEmail();
		String role="ROLE_USER";
		User userEntity=userRepository.findByUsername(username);
		
		if(userEntity==null) {
			userEntity=User.builder()
					.email(email)
					.role(role)
					.username(username)
					.password(password)
					.provider(provider)
					.providerId(providerId)
					.build();
					
			userRepository.save(userEntity);
		}
		else {
			System.out.println("당신은 회원가입을 한 적이 있습니다.");
		}
		
		return new PrincipalDetails(userEntity,user);
	}

}
