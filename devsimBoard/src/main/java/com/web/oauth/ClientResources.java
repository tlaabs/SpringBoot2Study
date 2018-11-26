package com.web.oauth;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

//소셜 미디어 리소스 프로퍼티를 객체로 매핑해주는 객체
public class ClientResources {
    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client =
            new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    private ResourceServerProperties resource = new ResourceServerProperties();

    public AuthorizationCodeResourceDetails getClient(){
        return client;
    }

    public ResourceServerProperties getResource(){
        return resource;
    }
}
