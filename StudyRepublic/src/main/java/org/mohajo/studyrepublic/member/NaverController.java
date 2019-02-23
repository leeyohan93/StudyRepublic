/*package org.mohajo.studyrepublic.member;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.mohajo.studyrepbulic.naver.NaverLoginBO;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.security.oauth2.JsonStringParse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;



@Controller
public class NaverController {

	@Autowired
	MemberRepository mr;

     NaverLoginBO 
    private NaverLoginBO naverLoginBO;
    private String apiResult = null;
    
    @Autowired
    private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
        this.naverLoginBO = naverLoginBO;
    }

    //로그인 첫 화면 요청 메소드
    @RequestMapping(value = "/NaverLogin", method = { RequestMethod.GET, RequestMethod.POST })
    public String login(Model model, HttpSession session) {
        
         네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 
        String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
        
        //https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************&
        //redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
        System.out.println("네이버:" + naverAuthUrl);
        
        //네이버 
        model.addAttribute("url", naverAuthUrl);

         생성한 인증 URL을 View로 전달 
        return "user/naverlogin";
    }

  
    
    
    //네이버 로그인 성공시 callback호출 메소드
    @RequestMapping(value = "/loginPostNaver", method = { RequestMethod.GET, RequestMethod.POST })
    public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session)
            throws IOException {
    	
    	
        
    	System.out.println("여기는 callback");
        OAuth2AccessToken oauthToken;
        oauthToken = naverLoginBO.getAccessToken(session, code, state);
        //로그인 사용자 정보를 읽어온다.
        apiResult = naverLoginBO.getUserProfile(oauthToken);
        System.out.println("결과: \n" + naverLoginBO.getUserProfile(oauthToken).toString());
        model.addAttribute("result", apiResult);
        System.out.println("result : \n"+apiResult);
        
        JsonStringParse jsonparse = new JsonStringParse();
        
        JSONObject jsonobj = jsonparse.stringToJson(apiResult, "response");
        String gender = jsonparse.JsonToString(jsonobj, "gender");
        String snsId = jsonparse.JsonToString(jsonobj, "id");
        String name = jsonparse.JsonToString(jsonobj, "name");
        String email = jsonparse.JsonToString(jsonobj, "email");
        
        Member member = new Member();
        
        member.setId(email);
        member.setEmail(email);
        member.setName(name);
        
        mr.save(member);
        member.setGender(gender);
        
        System.out.println("member : " + member);
        
         네이버 로그인 성공 페이지 View 호출 
//      JSONObject jsonobj = jsonparse.stringToJson(apiResult, "response");
//      String snsId = jsonparse.JsonToString(jsonobj, "id");
//      String name = jsonparse.JsonToString(jsonobj, "name");
//
//      UserVO vo = new UserVO();
//      vo.setUser_snsId(snsId);
//      vo.setUser_name(name);
//
//      System.out.println(name);
//      try {
//          vo = service.naverLogin(vo);
//      } catch (Exception e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//      }


//      session.setAttribute("login",vo);
//      return new ModelAndView("user/loginPost", "result", vo);
        
        return "user/loginPostNaver";
	
}
}*/