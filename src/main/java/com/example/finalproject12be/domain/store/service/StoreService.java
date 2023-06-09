package com.example.finalproject12be.domain.store.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.finalproject12be.domain.store.dto.OneStoreResponseDto;
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

	public List<StoreResponseDto> searchStore(String storeName, String gu, boolean open, boolean holidayBusiness, boolean nightBusiness) {

		int progress = 0; //stores 리스트가 null일 때 0, 반대는 1
		List<StoreResponseDto> storeResponseDtos = new ArrayList<>();
		List<Store> stores = new ArrayList<>();


		//storeName
		if(storeName != null){
			//findByStoreName
			progress = 1;
			stores = storeRepository.findAllByName(storeName);
		}

		//gu
		if(gu != null){ //구가 요청되었을 때

			if(progress == 0){ //저장된 stores가 없을 때

				progress = 1;
				stores = storeRepository.findAllByAddressContaining(gu);

			}else{ //저장된 stores가 있을 때

				for (Store store : stores) {
					if (!store.getAddress().contains(gu)) {
						stores.remove(store);
					}
				}
			}
		} //구가 요청되지 않았을 때는 progress가 0이고 저장될 사항이 없기 때문에 else 생략




		//filter
		if(open == true){ // open 필터



			if(progress == 1){ //저장된 stores가 있을 때만 실행 가능함

				stores = openCheck(stores);

			}

		}else if(holidayBusiness == true){

			if(progress == 0){
				progress = 1;

				stores = storeRepository.findAllByHolidayTimeIsNotNull();
			}else{

				//test
				List<Store> testStores = new ArrayList<>();
				for(Store store: stores){
					testStores.add(store);
				}

				for(Store testStore : testStores){
					if(testStore.getHolidayTime() == null){
						stores.remove(testStore);
					}
				}

			}

		}else if (nightBusiness == true){

			// nightBusinessCheck(stores);

			if(progress == 0){

				//TODO: 현재 요일 정해서 if 추가

				progress = 1;

				List<Store> newStores = new ArrayList<>();

				LocalDate now = LocalDate.now();
				int dayOfWeek = now.getDayOfWeek().getValue();

				//평일 검사
				if(dayOfWeek < 6){
					stores = storeRepository.findAllByWeekdaysTimeContaining("24");
					newStores = storeRepository.findAllByWeekdaysTimeContaining("25");
					for(Store store : newStores){
						stores.add(store);
					}

					newStores = storeRepository.findAllByWeekdaysTimeContaining("26");
					for(Store store : newStores){
						stores.add(store);
					}

					newStores = storeRepository.findAllByWeekdaysTimeContaining("27");
					for(Store store : newStores){
						stores.add(store);
					}
				}else if(dayOfWeek == 6){

					//토요일 검사

					newStores = storeRepository.findAllBysaturdayTimeContaining("24");
					for(Store store : newStores){
						stores.add(store);
					}

					newStores = storeRepository.findAllBysaturdayTimeContaining("25");
					for(Store store : newStores){
						stores.add(store);
					}

					newStores = storeRepository.findAllBysaturdayTimeContaining("26");
					for(Store store : newStores){
						stores.add(store);
					}

					newStores = storeRepository.findAllBysaturdayTimeContaining("27");
					for(Store store : newStores){
						stores.add(store);
					}

				}else if(dayOfWeek == 7){

					//일요일 검사

					newStores = storeRepository.findAllBysundayTimeContaining("24");
					for(Store store : newStores){
						stores.add(store);
					}

					newStores = storeRepository.findAllBysundayTimeContaining("25");
					for(Store store : newStores){
						stores.add(store);
					}

					newStores = storeRepository.findAllBysundayTimeContaining("26");
					for(Store store : newStores){
						stores.add(store);
					}

					newStores = storeRepository.findAllBysundayTimeContaining("27");
					for(Store store : newStores){
						stores.add(store);
					}

					newStores = storeRepository.findAllBysundayTimeContaining("28");
					for(Store store : newStores){
						stores.add(store);
					}

					newStores = storeRepository.findAllBysundayTimeContaining("29");
					for(Store store : newStores){
						stores.add(store);
					}

					// newStores = storeRepository.findAllBysundayTimeContaining("30");
					// for(Store store : newStores){
					// 	stores.add(store);
					// }

					//TODO: 얘 살리려면 영업중 필터와 같은 조건 필요

				}


			}else if(progress == 1){

				//test
				List<Store> testStores = new ArrayList<>();
				for(Store store: stores){
					testStores.add(store);
				}

				LocalDate now = LocalDate.now();
				int dayOfWeek = now.getDayOfWeek().getValue();

				if(dayOfWeek < 6){

					for(Store testStore : testStores){

						String storeTime = testStore.getWeekdaysTime();

						String[] storeTimes = storeTime.split("~");
						String closeHour = storeTimes[1].substring(0, 2);

						if(closeHour.equals("24")) {
							continue;
						}else if(closeHour.equals("25")){
							continue;
						}else if(closeHour.equals("26")){
							continue;
						}else if(closeHour.equals("27")){
							continue;
						}else if(closeHour.equals("28")){
							continue;
						}else if(closeHour.equals("29")){
							continue;
						}else if(closeHour.equals("30")){
							continue;
						}else{
							stores.remove(testStore);
						}

					}

				}else if(dayOfWeek == 6){


					for(Store testStore : testStores){

						String storeTime = testStore.getSaturdayTime();

						String[] storeTimes = storeTime.split("~");
						String closeHour = storeTimes[1].substring(0, 2);

						if(closeHour.equals("24")) {
							continue;
						}else if(closeHour.equals("25")){
							continue;
						}else if(closeHour.equals("26")){
							continue;
						}else if(closeHour.equals("27")){
							continue;
						}else if(closeHour.equals("28")){
							continue;
						}else if(closeHour.equals("29")){
							continue;
						}else if(closeHour.equals("30")){
							continue;
						}else{
							stores.remove(testStore);
						}

					}

				}else if (dayOfWeek == 7){

					for(Store testStore : testStores){

						String storeTime = testStore.getSundayTime();

						String[] storeTimes = storeTime.split("~");
						String closeHour = storeTimes[1].substring(0, 2);

						if(closeHour.equals("24")) {
							continue;
						}else if(closeHour.equals("25")){
							continue;
						}else if(closeHour.equals("26")){
							continue;
						}else if(closeHour.equals("27")){
							continue;
						}else if(closeHour.equals("28")){
							continue;
						}else if(closeHour.equals("29")){
							continue;
						}else if(closeHour.equals("30")){
							continue;
						}else{
							stores.remove(testStore);
						}

					}

				}

			}

		}


		for(Store store : stores){
			storeResponseDtos.add(new StoreResponseDto(store));
		}

		return storeResponseDtos;
	}

	public OneStoreResponseDto getStore(Long storeId) {
		Store store = storeRepository.findById(storeId)
			.orElseThrow(() -> new IllegalArgumentException("해당 약국은 존재하지 않습니다."));

		return new OneStoreResponseDto(store);
	}

	private List<Store> openCheck(List<Store> stores){

		//test
		List<Store> testStores = new ArrayList<>();
		for(Store store: stores){
			testStores.add(store);
		}


		LocalDate now = LocalDate.now();
		int dayOfWeek = now.getDayOfWeek().getValue();

		LocalTime nowTime = LocalTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		// 포맷 적용하기
		String formatedNow = nowTime.format(formatter);
		int nowHour = Integer.parseInt(formatedNow.substring(0, 2));
		int nowMin = Integer.parseInt(formatedNow.substring(3, 5));



		int openHour = 0;
		int openMin = 0;
		int closeHour = 0;
		int closeMin = 0;


		for (Store testStore : testStores) {
			if (dayOfWeek > 0 && dayOfWeek < 6) { //평일

				String storeTime = testStore.getWeekdaysTime();

				String[] storeTimes = storeTime.split("~");

				openHour = Integer.parseInt(storeTimes[0].substring(3, 5));
				openMin = Integer.parseInt(storeTimes[0].substring(6));

				closeHour = Integer.parseInt(storeTimes[1].substring(0, 2));
				closeMin = Integer.parseInt(storeTimes[1].substring(3));

			}else if (dayOfWeek == 6){ // 토요일 TODO: 일요일이랑 합치기

				String storeTime = testStore.getWeekdaysTime();

				String[] storeTimes = storeTime.split("~");

				openHour = Integer.parseInt(storeTimes[0].substring(2, 4));
				openMin = Integer.parseInt(storeTimes[0].substring(5));

				closeHour = Integer.parseInt(storeTimes[1].substring(0, 2));
				closeMin = Integer.parseInt(storeTimes[1].substring(3));

			}else if( dayOfWeek == 7){ // 일요일

				String storeTime = testStore.getWeekdaysTime();

				String[] storeTimes = storeTime.split("~");

				openHour = Integer.parseInt(storeTimes[0].substring(2, 4));
				openMin = Integer.parseInt(storeTimes[0].substring(5));

				closeHour = Integer.parseInt(storeTimes[1].substring(0, 2));
				closeMin = Integer.parseInt(storeTimes[1].substring(3));

			}

			if((openHour > nowHour) || (closeHour < nowHour)){
				stores.remove(testStore);
			}else if(openHour == nowHour && openMin > nowMin){
				stores.remove(testStore);
			} else if(closeHour == nowHour && closeMin < nowMin){ //현재 시간이 영업 시간에 포함되지 않을 때
				stores.remove(testStore);
			}
		}

		return stores;

	}


}
