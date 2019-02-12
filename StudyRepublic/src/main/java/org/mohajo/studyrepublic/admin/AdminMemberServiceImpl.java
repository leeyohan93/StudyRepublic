package org.mohajo.studyrepublic.admin;

import java.lang.management.GarbageCollectorMXBean;
import java.util.Arrays;
import java.util.List;

import org.mohajo.studyrepublic.domain.GradeCD;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberStatusCD;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

/**
 * @author 이요한
 * @since 2019. 2. 7.
 * @version 0.0 -기능 설명1
 */
@Service("AdminMemberService")
public class AdminMemberServiceImpl implements AdminMemberService {

	@Autowired
	MemberRepository memberRepository;
	@Autowired
	JavaMailSender emailSender;

	@Override
	public List<Member> getMemberList() {
		return memberRepository.findAll();
	}
	
	// https://www.google.com/settings/security/lesssecureapps 에서 허용 설정
	@Override
	public void sendEmailMessage(String[] to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}
	
	@Override
	public void changePassword(String memberId,String memberPhonenumber) {

		Member member = memberRepository.findById(memberId).get();
		member.setPassword(memberPhonenumber);
		memberRepository.save(member);

	}
	
	@Override
	public List<Member> unpauseMember(String[] selectedId) {

		List<Member> selectedMember = memberRepository.getSelectedMember(selectedId);
		for (int i = 0; i < selectedMember.size(); i++) {
			selectedMember.get(i).setMemberStatusCD(new MemberStatusCD("N","일반"));
			memberRepository.saveAndFlush(selectedMember.get(i));
			
		}
		memberRepository.flush();
		return selectedMember;
	}
	
	@Override
	public List<Member> changeGrade(String[] selectedId, String changeGrade) {

		List<Member> selectedMember = memberRepository.getSelectedMember(selectedId);
		String gradeValueKorean = "";
		switch(changeGrade) {
		case "N":
			gradeValueKorean="일반";
			break;
		case "W":
			gradeValueKorean="강사승인대기";
			break;
		case "T":
			gradeValueKorean="강사";
			break;
		case "A":
			gradeValueKorean="관리자";
		}
		
		for (int i = 0; i < selectedMember.size(); i++) {
			selectedMember.get(i).setGradeCD(new GradeCD(changeGrade,gradeValueKorean));
			memberRepository.save(selectedMember.get(i));
		}
		return selectedMember;
	}

	

	@Override
	public List<Member> pauseMember(String[] selectedId){
		
		List<Member> selectedMember = memberRepository.getSelectedMember(selectedId);
		
		for(int i=0; i<selectedMember.size(); i++) {
			selectedMember.get(i).setMemberStatusCD(new MemberStatusCD("P","정지"));
			memberRepository.save(selectedMember.get(i));
		}
		return selectedMember;
	}
	
	@Override
	public List<Member> exiteMember(String[] selectedId){
		List<Member> selectedMember = memberRepository.getSelectedMember(selectedId);
		
		for(int i=0; i<selectedMember.size(); i++) {
			selectedMember.get(i).setMemberStatusCD(new MemberStatusCD("E","탈퇴"));
			memberRepository.save(selectedMember.get(i));
		}
		return selectedMember;
		
	}
	
/*	public List<Member> getSelectedMember(String[] selectedId){
		Iterable<Member> member= memberRepository.findAll(AdminPredicate.selectedMember(selectedId));
		List<Member> list = Lists.newArrayList(member);
		System.out.println(list.get(0).getMemberStatusCD().getCodeValueKorean());
		return list;
	}*/
	@Override
	public List<Member> getSearchMember(String[] grade,String[] status,String searchKey,String searchValue){
		Iterable<Member> searchmember = memberRepository.findAll(AdminPredicate.searchMember(grade, status, searchKey, searchValue));
		return Lists.newArrayList(searchmember);
	}
	

	
}