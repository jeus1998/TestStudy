package spring.studytest.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.studytest.member.MemberService;
import spring.studytest.study.StudyRepository;
import spring.studytest.study.StudyService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock MemberService memberService;
    @Mock StudyRepository studyRepository;
    @InjectMocks StudyService studyService;

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
