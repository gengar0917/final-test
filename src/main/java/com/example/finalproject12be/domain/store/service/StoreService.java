package com.example.finalproject12be.domain.store.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.finalproject12be.domain.store.dto.StoreResponseDto;
import com.example.finalproject12be.domain.store.entity.Store;
import com.example.finalproject12be.domain.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;

	public List<StoreResponseDto> getAllStores() {

		List<Store> stores = storeRepository.findAll();
		List<StoreResponseDto> storeResponseDtos = new ArrayList<>();

		for(Store store : stores){
			storeResponseDtos.add(new StoreResponseDto(store));
		}

		return storeResponseDtos;
	}
}
