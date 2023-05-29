package com.example.finalproject12be.domain.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.finalproject12be.domain.bookmark.entity.Bookmark;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickname;

	@ManyToOne
	@JoinColumn(name = "BOOKMARK_ID")
	private Bookmark bookmark;

	public Member(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}

	public static Member of(String email, String password, String nickname) {
		return new Member(email, password, nickname);
	}

	public void setBookmark(Bookmark bookmark){
		this.bookmark = bookmark;
	}
}
