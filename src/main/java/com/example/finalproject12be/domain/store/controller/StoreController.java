package com.example.finalproject12be.domain.store.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.Flow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject12be.domain.store.OpenApiManager;
import com.example.finalproject12be.domain.store.dto.StoreResponseDto;
import com.example.finalproject12be.domain.store.service.StoreService;

import lombok.RequiredArgsConstructor;

@RestController
// @Controller
@RequiredArgsConstructor
// @RequestMapping("api/store")
public class StoreController {

	private final StoreService storeService;

	@GetMapping("/api/store")
	public List<StoreResponseDto> getAllStores(){
		return storeService.getAllStores();
	}




	private final OpenApiManager openApiManager;

	@GetMapping("api/store/open-api")
	public void fetch() {
		openApiManager.fetch();
	}

}
