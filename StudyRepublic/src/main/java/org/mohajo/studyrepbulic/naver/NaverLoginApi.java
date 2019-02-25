/**
 * 
 */
package org.mohajo.studyrepbulic.naver;

import com.github.scribejava.core.builder.api.DefaultApi20;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * -기능1 추가
 * -기능2 추가
 * -기능3 추가
 */

public class NaverLoginApi extends DefaultApi20 {


	 protected NaverLoginApi(){
	    }

	    private static class InstanceHolder{
	        private static final NaverLoginApi INSTANCE = new NaverLoginApi();
	    }


	    public static NaverLoginApi instance(){
	        return InstanceHolder.INSTANCE;
	    }

	    
	    @Override
	    public String getAccessTokenEndpoint() {
	        
	        return "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";

	    }

	    @Override
	    protected String getAuthorizationBaseUrl() {
	        // TODO Auto-generated method stub
	        return"https://nid.naver.com/oauth2.0/authorize";
	    }

}
