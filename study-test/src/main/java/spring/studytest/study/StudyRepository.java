package spring.studytest.study;


import org.springframework.data.jpa.repository.JpaRepository;
import spring.studytest.domain.Study;

public interface StudyRepository extends JpaRepository<Study, Long> {

}
