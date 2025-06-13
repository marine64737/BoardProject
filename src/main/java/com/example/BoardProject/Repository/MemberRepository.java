package com.example.BoardProject.Repository;

import com.example.BoardProject.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query(value = "select count(*) from member where userid = :userid", nativeQuery = true)
    int isMember(String userid);
}
