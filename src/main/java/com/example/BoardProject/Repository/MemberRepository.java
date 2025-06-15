package com.example.BoardProject.Repository;

import com.example.BoardProject.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{
    @Query(value = "select count(*) from member where username = :username", nativeQuery = true)
    int isMember(String username);

    @Query(value = "select * from member where username = :username", nativeQuery = true)
    Optional<Member> findByUserId(String username);
}
