package spring.studytest.study;

import spring.studytest.domain.Member;
import spring.studytest.domain.Study;
import spring.studytest.member.MemberService;

import java.util.Optional;

public class StudyService {

    private final MemberService memberService;
    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        assert memberService != null;
        assert repository != null;
        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study){
        Optional<Member> optionalMember = memberService.findById(memberId);
        var member = optionalMember.orElseThrow(
                () -> new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'")
        );
        study.setOwner(member);
        var newStudy = repository.save(study);
        memberService.notify(study);
        memberService.notify(member);
        return newStudy;
    }

    public Study openStudy(Study study) {
        study.open();
        Study openedStudy = repository.save(study);
        memberService.notify(openedStudy);
        return openedStudy;
    }
}
