package com.example.finalproject12be.domain.store.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;

import com.example.finalproject12be.domain.bookmark.entity.Bookmark;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STORE_ID")
	private Long id;

	@Column
	private String address;

	@Column
	private String name;

	@Column
	private String callNumber;

	//평일 운영 시간
	@Column
	private String weekdaysTime;

	@Column
	private String saturdayTime;

	@Column
	private String sundayTime;

	@Column
	private String holidayTime;

	@Column
	private String longitude;

	@Column
	private String latitude;

	@OneToOne
	@JoinColumn(name = "BOOKMARK_ID")
	private Bookmark bookmark;


	public Store(String address, String name, String callNumber, String weekdaysTime, String saturdayTime, String sundayTime, String holidayTime, String longitude, String latitude){
		this.address = address;
		this.name = name;
		this.callNumber = callNumber;
		this.weekdaysTime = weekdaysTime;
		this.saturdayTime = saturdayTime;
		this.sundayTime = sundayTime;
		this.holidayTime = holidayTime;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public void setBookmark(Bookmark bookmark){
		this.bookmark = bookmark;
	}
}
