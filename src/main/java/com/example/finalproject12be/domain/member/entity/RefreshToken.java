package com.example.finalproject12be.domain.member.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(force = true)
public class RefreshToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String refreshToken;

	@NotNull
	private String email;

	public RefreshToken(String refreshToken, String email) {
		this.refreshToken = refreshToken;
		this.email = email;
	}

	public RefreshToken updateToken(String refreshToken) {
		this.refreshToken = refreshToken;
		return this;
	}
}
