package com.example.security.config.oauth;

public interface OAuthUserInfo {

	public String getProvider();
	public String getProviderId();
	public String getName();
	public String getEmail();
}
