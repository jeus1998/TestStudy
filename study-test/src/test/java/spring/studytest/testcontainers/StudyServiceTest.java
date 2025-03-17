package spring.studytest.testcontainers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
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
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.given;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
public class StudyServiceTest {
    @Mock MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired  StudyRepository studyRepository;

    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer =
            new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
                    .withDatabaseName("study_test");

    @BeforeAll
    static void beforeAll() {
        System.out.println(mySQLContainer.getJdbcUrl());
    }

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
