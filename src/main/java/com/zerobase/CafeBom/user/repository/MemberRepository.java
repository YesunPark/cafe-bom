package com.zerobase.CafeBom.user.repository;

import com.zerobase.CafeBom.user.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByEmail(String email);
}
