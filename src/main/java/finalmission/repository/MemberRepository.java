package finalmission.repository;

import finalmission.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(@Param("email") String email);
}
