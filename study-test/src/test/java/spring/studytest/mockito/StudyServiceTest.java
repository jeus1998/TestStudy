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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock MemberService memberService;
    @Mock StudyRepository studyRepository;
    @InjectMocks StudyService studyService;

    @Test
    @DisplayName("main test")
    void main(){
        var member = new Member();
        member.setId(1L);
        member.setEmail("test@test.com");

        var study = new Study(10, "테스트");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(any(Study.class)))
                .thenReturn(study);

        studyService.createNewStudy(1L, study);

        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).findById(any());
        inOrder.verify(memberService).notify(any(Study.class));
        inOrder.verify(memberService).notify(any(Member.class));
        verifyNoMoreInteractions(memberService);
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
