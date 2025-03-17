package spring.studytest.testcontainers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import spring.studytest.domain.Member;
import spring.studytest.domain.Study;
import spring.studytest.domain.StudyStatus;
import spring.studytest.member.MemberService;
import spring.studytest.study.MemberRepository;
import spring.studytest.study.StudyRepository;
import spring.studytest.study.StudyService;

import java.io.File;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
public class DockerComposePractice {

    @Mock MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired StudyRepository studyRepository;

    @Container
    static DockerComposeContainer composeContainer =
            new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

    @BeforeEach
    void setUp() {
        var member = new Member();
        member.setEmail("me@email.com");
        memberRepository.save(member);
    }

    @Test
    void createStudy() {
        // given
        var studyService = new StudyService(memberService, studyRepository);
        var study = new Study(10, "container");

        var member = memberRepository.findById(1L).get();

        given(memberService.findById(1L)).willReturn(Optional.of(member));

        // when
        studyService.createNewStudy(member.getId(), study);

        // then
        then(memberService).should(times(1)).findById(1L);
        then(memberService).should(times(1)).notify(any(Member.class));
        then(memberService).should(times(1)).notify(any(Study.class));
        then(memberService).shouldHaveNoMoreInteractions();

        Optional<Study> findStudy = studyRepository.findById(study.getId());
        assertThat(findStudy.isPresent()).isTrue();
        assertThat(findStudy.get().getId()).isEqualTo(study.getId());
        assertThat(findStudy.get().getName()).isEqualTo(study.getName());
        assertThat(findStudy.get().getStatus()).isEqualTo(StudyStatus.DRAFT);
    }


}
