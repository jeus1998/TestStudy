package spring.studytest.study;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.studytest.domain.Member;
public interface MemberRepository extends JpaRepository<Member, Long> {

}
