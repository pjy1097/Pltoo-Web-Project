package com.pltoo.membership.Service;

import com.pltoo.membership.dto.MemberDTO;
import com.pltoo.membership.entity.MemberEntity;
import com.pltoo.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    //ModelMapper
    private final ModelMapper modelMapper = new ModelMapper();  // ModelMapper 인스턴스 생성

    public void save(MemberDTO memberDTO) {
        // 1. dto -> entity 변환
        //2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
        //repository의 save메서드 호출(조건. entity객체를 넘겨줘야 함)
    }

    public Long findIdByEmail(String MemberEmail) {
        Long memberNum = memberRepository.findIdByEmail(MemberEmail);
        if (memberNum != null) {
            return memberNum;
        }
        return null;
    }

    public void updateMemberNickname(String memberEmail, String memberNickname) {
        MemberEntity member = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + memberEmail));
        member.setNickname(memberNickname);
        memberRepository.save(member);
    }

    private MemberDTO convertToDTO(Long memberEntity) {
        return modelMapper.map(memberEntity, MemberDTO.class);
    }

}
