package spring.studytest.member;


import spring.studytest.domain.Member;
import spring.studytest.domain.Study;
import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);
    void validate(Long memberId);
    void notify(Study newStudy);
    void notify(Member member);
}
