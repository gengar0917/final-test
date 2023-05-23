package com.example.finalproject12be.domain.member.dto.request;

import com.example.finalproject12be.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class MemberSignupRequest {


	private String email;

	private String password;

	private String nickname;


	public static Member toEntity(MemberSignupRequest memberSignupRequest, String password) {
		return Member.of(
			memberSignupRequest.getEmail(),
			password,
			memberSignupRequest.getNickname()
		);
	}

}
