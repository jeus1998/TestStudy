package spring.studytest.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.studytest.domain.Member;
import spring.studytest.domain.Study;
import spring.studytest.member.MemberService;
import spring.studytest.study.StudyRepository;
import spring.studytest.study.StudyService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))    // 1
                .thenThrow(RuntimeException.class)  // 2
                .thenReturn(Optional.empty());   // 3

        assertAll(
                () -> assertThat(memberService.findById(1L)).isPresent(),
                () -> assertThatThrownBy(() -> memberService.findById(1L))
                        .isInstanceOf(RuntimeException.class),
                () -> assertThat(memberService.findById(1L)).isEmpty(),
                () -> assertThat(memberService.findById(1L)).isEmpty()
        );
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
