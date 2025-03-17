package spring.studytest.testcontainers;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import spring.studytest.domain.Member;
import spring.studytest.domain.Study;
import spring.studytest.domain.StudyStatus;
import spring.studytest.member.MemberService;
import spring.studytest.study.MemberRepository;
import spring.studytest.study.StudyRepository;
import spring.studytest.study.StudyService;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

/**
 * 테스트 컨테이너 기능 살펴보기
 * 컨테이너 host port 지정 불가능 충돌하지 않게 설정된다.
 */
@DataJpaTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
@Slf4j
class TestContainersPractice {

    @Mock MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired StudyRepository studyRepository;

    @Container
    static GenericContainer<?> mysqlContainer =
            new GenericContainer<>(DockerImageName.parse("mysql:latest"))
                    .withExposedPorts(5432)                // 컨테이너 port
                    .withEnv("MYSQL_DB", "study_test");    // 컨테이너 사용 환경변수
                 // .waitingFor(Wait.forListeningPort()    // 사용할 준비가 됐는지

    @BeforeAll
    static void setUp() {
        System.out.println(mysqlContainer.getMappedPort(5432)); // host port
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        mysqlContainer.withLogConsumer(logConsumer); // 로그 스트리밍
    }

    @BeforeEach
    void beforeEach() {
        mysqlContainer.getLogs(); // 로그 살펴보기
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
