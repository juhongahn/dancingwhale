package com.k1ng.doinggajigaji.service;


import com.k1ng.doinggajigaji.dto.PasswordChangeDto;
import com.k1ng.doinggajigaji.dto.ProfileEditDto;
import com.k1ng.doinggajigaji.entity.CustomUserDetails;
import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements UserDetailsService, MemberService {

    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;

    @Override
    public Member join(Member member, String siteURL) throws MessagingException, UnsupportedEncodingException {

        String randomCode = RandomString.make(64);
        member.setVerificationCode(randomCode);
        member.setEnabled(false);
        Member savedMember = memberRepository.save(member);
        sendVerificationEmail(savedMember, siteURL);
        return savedMember;
    }

    private void sendVerificationEmail(Member member, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = member.getEmail();
        String fromAddress = "dancingwhaile@gmail.com";
        String senderName = "춤추는 고래";
        String subject = "회원가입 인증 메일입니다.";
        String content = "안녕하세요! [[name]],<br>"
                + "아래 링크를 클릭해 인증을 마무리 해주세요:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">인증하기</a></h3>"
                + "감사합니다.<br>"
                + "춤추는 고래";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", member.getName());
        String verifyURL = siteURL + "/verify?code=" + member.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public boolean verify(String verificationCode) {
        Member member = memberRepository.findByVerificationCode(verificationCode);

        if (member == null || member.isEnabled()) {
            return false;
        } else {
            member.setVerificationCode(null);
            member.setEnabled(true);
            memberRepository.save(member);
            return true;
        }

    }

    @Override
    public boolean findDuplication(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public Member findMemberByEmail(String email) {
        
        // 찾고 없을 땐 null을 반환.
        return memberRepository.findMemberByEmail(email).orElse(null);
    }

    @Override
    public Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Long updateProfile(Long memberId, ProfileEditDto profileEditDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);
        member.updateProfile(profileEditDto);
        return member.getId();
    }

    @Override
    public Long updatePassword(Long memberId, PasswordChangeDto passwordChangeDto, PasswordEncoder passwordEncoder) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);
        member.updatePassword(passwordChangeDto, passwordEncoder);
        return member.getId();
    }

    @Override
    public Long deleteMember(Member member) {
        memberRepository.delete(member);
        return member.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }
        return customUserDetails;
    }
}
