package com.example.thejavatest.study;

import com.example.thejavatest.domain.Member;
import com.example.thejavatest.domain.Study;
import com.example.thejavatest.member.MemberService;
import java.util.Optional;

public class StudyService {
    private final MemberService memberService;
    private final StudyRepository repository;
    public StudyService(MemberService memberService, StudyRepository repository) {
        this.memberService = memberService;
        this.repository = repository;
    }
    public Study createNewStudy(Long memberId, Study study){
        Optional<Member> member = memberService.findById(memberId);
        study.setOwnerId(member.orElseThrow(() ->
                new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'")).getId());

        Study newStudy = repository.save(study);
        memberService.notify(newStudy);
        // memberService.notify(member.get());
        return newStudy;
    }
    public Study openStudy(Study study) {
        study.open();
        Study openedStudy = repository.save(study);
        memberService.notify(openedStudy);
        return openedStudy;
    }
}
