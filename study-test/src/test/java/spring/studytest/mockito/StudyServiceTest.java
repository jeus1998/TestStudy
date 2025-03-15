package spring.studytest.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.studytest.domain.Member;
import spring.studytest.domain.Study;
import spring.studytest.member.MemberService;
import spring.studytest.study.StudyRepository;
import spring.studytest.study.StudyService;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock MemberService memberService;
    @Mock StudyRepository studyRepository;
    @InjectMocks StudyService studyService;

    @Test
    @DisplayName("main test")
    void main(){
        // given
        var member = new Member();
        member.setId(1L);
        member.setEmail("test@test.com");

        var study = new Study(10, "테스트");
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        // when
        studyService.createNewStudy(1L, study);

        // then
        then(memberService).should(times(1)).notify(member);
        then(memberService).should(times(1)).notify(study);
        then(memberService).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("Mockito.mock")
    void createStudyService(){
        var memberService = mock(MemberService.class);
        var studyRepository = mock(StudyRepository.class);
        var studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    @Test
    @DisplayName("@Mock")
    void createStudyService2(){
        var studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    @Test
    @DisplayName("@Mock parameter")
    void createStudyService3(@Mock MemberService memberService, @Mock StudyRepository studyRepository){
        var studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    @Test
    @DisplayName("@InjectMocks")
    void createStudyService4(){
        StudyService studyService = this.studyService;
        assertNotNull(studyService);
    }
}
