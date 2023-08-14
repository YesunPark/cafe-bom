package com.zerobase.CafeBom.user.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.zerobase.CafeBom.user.domain.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    Member member = Member.builder()
        .password("password")
        .nickname("tester")
        .phone("01012345678")
        .email("test@test.com")
        .build();

    // yesun-23.08.14
    @Test
    @DisplayName("회원 정보 등록")
    void save() {
        // when
        Member actual = memberRepository.save(member);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getPassword()).isEqualTo(member.getPassword());
        assertThat(actual.getNickname()).isEqualTo(member.getNickname());
        assertThat(actual.getPhone()).isEqualTo(member.getPhone());
        assertThat(actual.getEmail()).isEqualTo(member.getEmail());
    }

    // yesun-23.08.14
    @Test
    @DisplayName("단건 조회 - 회원 id")
    void findById() {
        // given
        Member expect = memberRepository.save(member);

        // when
        Member actual = memberRepository.findById(member.getId())
            .orElseThrow(() -> new RuntimeException("error"));

        // then
        assertThat(actual.getId()).isEqualTo(expect.getId());
        assertThat(actual.getPassword()).isEqualTo(expect.getPassword());
        assertThat(actual.getNickname()).isEqualTo(expect.getNickname());
        assertThat(actual.getPhone()).isEqualTo(expect.getPhone());
        assertThat(actual.getEmail()).isEqualTo(expect.getEmail());
    }

    // yesun-23.08.14
    @Test
    @DisplayName("회원 정보 업데이트 - 닉네임")
    void update() {
        // given
        Member expect = memberRepository.save(member);

        // when
        Member actual = memberRepository.findById(expect.getId())
            .orElseThrow(() -> new RuntimeException("error"));
        actual.setNickname("nickname2");

        // then
        assertThat(actual.getId()).isEqualTo(expect.getId());
        assertThat(actual.getPassword()).isEqualTo(expect.getPassword());
        assertThat(actual.getNickname()).isEqualTo(expect.getNickname());
        assertThat(actual.getPhone()).isEqualTo(expect.getPhone());
        assertThat(actual.getNickname()).isEqualTo(expect.getNickname());
        assertThat(actual.getEmail()).isEqualTo(expect.getEmail());
    }
}