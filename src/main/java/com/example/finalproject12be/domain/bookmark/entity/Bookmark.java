package com.example.finalproject12be.domain.bookmark.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.example.finalproject12be.domain.member.entity.Member;
import com.example.finalproject12be.domain.store.entity.Store;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Bookmark {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BOOKMARK_ID")
	private Long id;

	@OneToMany(mappedBy = "bookmark")
	@JsonBackReference
	private List<Member> members = new ArrayList<Member>();

	@OneToOne(mappedBy = "bookmark")
	@JsonBackReference
	private Store store;

	@Column
	private long totalCount = 0;

	public void bookmarkStore(int isPresent){
		this.totalCount += isPresent;
	}

	public void deleteBookmark(Member member){
		this.members.remove(member);
	}

	public Bookmark(Store store, Member member){
		this.store = store;
		this.members.add(member);
	}

}
