package com.example.thejavatest.study;

import com.example.thejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {
    @Mock
    MemberService memberService;
    @Mock
    StudyRepository studyRepository;
    @Test
    void createStudyService(){
        StudyService studyService = new StudyService(memberService, studyRepository);
        System.out.println(studyService);
        assertNotNull(studyService);
    }
}