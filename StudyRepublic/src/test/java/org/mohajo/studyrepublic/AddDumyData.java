package org.mohajo.studyrepublic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberPoint;
import org.mohajo.studyrepublic.domain.MemberRoles;
import org.mohajo.studyrepublic.repository.MemberPointRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Commit
public class AddDumyData {
	
	BCryptPasswordEncoder bcryptpasswordencoder = new BCryptPasswordEncoder();
	
	@Autowired
	MemberRepository memberrepository;
	
	@Autowired
	private MemberPointRepository memberpointrepository;
	
	private static final String ROLE_PREFIX = "ROLE_";
	
	public static String randomHangulName() {
	    List<String> 성 = Arrays.asList("김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신", "권", "황", "안",
	        "송", "류", "전", "홍", "고", "문", "양", "손", "배", "조", "백", "허", "유", "남", "심", "노", "정", "하", "곽", "성", "차", "주",
	        "우", "구", "신", "임", "나", "전", "민", "유", "진", "지", "엄", "채", "원", "천", "방", "공", "강", "현", "함", "변", "염", "양",
	        "변", "여", "추", "노", "도", "소", "신", "석", "선", "설", "마", "길", "주", "연", "방", "위", "표", "명", "기", "반", "왕", "금",
	        "옥", "육", "인", "맹", "제", "모", "장", "남", "탁", "국", "여", "진", "어", "은", "편", "구", "용");
	    List<String> 이름 = Arrays.asList("가", "강", "건", "경", "고", "관", "광", "구", "규", "근", "기", "길", "나", "남", "노", "누", "다",
	        "단", "달", "담", "대", "덕", "도", "동", "두", "라", "래", "로", "루", "리", "마", "만", "명", "무", "문", "미", "민", "바", "박",
	        "백", "범", "별", "병", "보", "빛", "사", "산", "상", "새", "서", "석", "선", "설", "섭", "성", "세", "소", "솔", "수", "숙", "순",
	        "숭", "슬", "승", "시", "신", "아", "안", "애", "엄", "여", "연", "영", "예", "오", "옥", "완", "요", "용", "우", "원", "월", "위",
	        "유", "윤", "율", "으", "은", "의", "이", "익", "인", "일", "잎", "자", "잔", "장", "재", "전", "정", "제", "조", "종", "주", "준",
	        "중", "지", "진", "찬", "창", "채", "천", "철", "초", "춘", "충", "치", "탐", "태", "택", "판", "하", "한", "해", "혁", "현", "형",
	        "혜", "호", "홍", "화", "환", "회", "효", "훈", "휘", "희", "운", "모", "배", "부", "림", "봉", "혼", "황", "량", "린", "을", "비",
	        "솜", "공", "면", "탁", "온", "디", "항", "후", "려", "균", "묵", "송", "욱", "휴", "언", "령", "섬", "들", "견", "추", "걸", "삼",
	        "열", "웅", "분", "변", "양", "출", "타", "흥", "겸", "곤", "번", "식", "란", "더", "손", "술", "훔", "반", "빈", "실", "직", "흠",
	        "흔", "악", "람", "뜸", "권", "복", "심", "헌", "엽", "학", "개", "롱", "평", "늘", "늬", "랑", "얀", "향", "울", "련");
	    Collections.shuffle(성);
	    Collections.shuffle(이름);
	    return 성.get(0) + 이름.get(0) + 이름.get(1);
	  }
	
	
	
    public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static LocalDate createRandomDate(int startYear, int endYear) {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(startYear, endYear);
        return LocalDate.of(year, month, day);
    }

	
	@Test
	public void addInsertMember() throws ParseException {
		
		for(int i = 501; i <= 520; i++) {	// 501번부터 이용가능
			Member member = new Member();
			member.setId("mohajo" + i);
			member.setPassword(bcryptpasswordencoder.encode("123"));
			
			MemberRoles memberroles = new MemberRoles();
			memberroles.setRoleName("N");
			member.setRoles(Arrays.asList(memberroles));
					
			String name = randomHangulName();
			member.setName(name);
						
			if(i%2 == 0) {
				member.setGender("남");
				}
			else {
				member.setGender("여");
				}
			
			LocalDate randomDate = createRandomDate(1900, 2000);
			member.setBirth(randomDate+"");
			

			
			member.setNickname(name.charAt(0)+"아무개" + i);
			
			member.setEmail("mohajo"+i+"@naver.com");
			
			int randomKey = (int)((Math.random()*90000000))+10000000;
			member.setPhonenumber("010" + randomKey);
			
			member.setVisibility(0);
			
			member.setProfileOriginName("default_img.png");
			member.setProfileSaveName("default_img.png");
						
			String sdate = createRandomDate(2014, 2018).toString();
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyyy-mm-dd");
			Date date = dt1.parse(sdate);
			member.setRegistrationDate(date);
			
		/*	member.setGradeCD(new GradeCD("N"));
			member.setMemberStatusCD(new MemberStatusCD("N"));*/
			
/*			InterestLocation intloc = new InterestLocation();
			intloc.setInterestLocation("서울강남구역삼동");
			member.setInterestlocation(Arrays.asList((intloc)));
			
			MemberInterest memint = new MemberInterest();
			Interest2CD int2 = new Interest2CD();
			int2.setCodeValueKorean("오라클");
			int2.setInterest2Code("D02");
			Interest1CD int1 = new Interest1CD();
			int1.setCodeValueKorean("데이터베이스");
			int1.setInterest1Code("D");
			int2.setInterest1cd(int1);
			memint.setInterest2cd(int2);
			member.setMemberInterest(Arrays.asList((memint)));*/
			

			memberrepository.save(member);
			System.out.println(member);
			
			MemberPoint memberpoint = new MemberPoint();
			memberpoint.setMember(member);
			memberpoint.setPoint(0);
			
			memberpointrepository.save(memberpoint);
			
						
			
		} 
		
		
		
		
		
	}
	
}
