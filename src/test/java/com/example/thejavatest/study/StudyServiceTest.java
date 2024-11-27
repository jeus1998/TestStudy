package com.example.thejavatest.study;

import com.example.thejavatest.domain.Member;
import com.example.thejavatest.domain.Study;
import com.example.thejavatest.domain.StudyStatus;
import com.example.thejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {
    @Test
    void createNewStudy(@Mock MemberService memberService, @Mock StudyRepository studyRepository){
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("baejeu@naver.com");

        when(memberService.findById(anyLong()))
                .thenReturn(Optional.of(member))
                .thenThrow(RuntimeException.class)
                .thenReturn(Optional.empty());

        assertEquals(member , memberService.findById(1L).get());
        assertThrows(RuntimeException.class, () -> memberService.findById(2L));
        assertEquals(Optional.empty(), memberService.findById(3L));
        assertEquals(Optional.empty(), memberService.findById(4L));
    }
    @Test
    void test(@Mock MemberService memberService, @Mock StudyRepository studyRepository){
        // TODO memberService 객체에 findById 메소드를 1L 값으로 호출하면 Optional.of(member) 객체를 리턴하도록 Stubbing
        Member member = new Member();
        member.setId(1L);
        member.setEmail("baejeu@naver.com");
        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        // TODO studyRepository 객체에 save 메소드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 Stubbing
        Study study = new Study();
        study.setId(1L);
        study.setStatus(StudyStatus.STARTED);

        when(studyRepository.save(study)).thenReturn(study);

        StudyService studyService = new StudyService(memberService, studyRepository);
        Study createStudy = studyService.createNewStudy(1L, study);

        assertEquals(member.getId(), createStudy.getOwnerId());
    }
    @Test
    void notifyTest(@Mock MemberService memberService, @Mock StudyRepository studyRepository){
        Member member = new Member();
        member.setId(1L);
        member.setEmail("baejeu@naver.com");
        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        Study study = new Study(100, "자바 스터디");
        when(studyRepository.save(study)).thenReturn(study);

        StudyService studyService = new StudyService(memberService, studyRepository);
        studyService.createNewStudy(1L, study);

        // notify 호출 검증
        verify(memberService, times(1)).notify(study);

        verifyNoMoreInteractions(memberService);

        /*
        // validate 호출 검증
        verify(memberService, never()).validate(any());

        // 순서 검증
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
        */
    }
    @Test
    void bddTest(@Mock MemberService memberService, @Mock StudyRepository studyRepository){
        // given
        Member member = new Member();
        member.setId(1L);
        member.setEmail("baejeu@naver.com");

        given(memberService.findById(1L)).willReturn(Optional.of(member));

        Study study = new Study(100, "자바 스터디");
        given(studyRepository.save(study)).willReturn(study);

        StudyService studyService = new StudyService(memberService, studyRepository);
        // when
        studyService.createNewStudy(1L, study);

        // then
        then(memberService).should(times(1)).notify(study);
        then(memberService).should(never()).validate(any());
        then(memberService).shouldHaveNoMoreInteractions();
    }
}