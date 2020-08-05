package playground;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long>{
    List<Schedule> findByBookId(Long bookId);
    List<Schedule> findByGroundId(Long groundId);
}