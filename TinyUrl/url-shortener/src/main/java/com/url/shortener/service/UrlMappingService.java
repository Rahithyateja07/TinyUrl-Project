package com.url.shortener.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.url.shortener.dtos.ClickEventDTO;
import com.url.shortener.dtos.UrlMappingDTO;
import com.url.shortener.models.ClickEvent;
import com.url.shortener.models.UrlMapping;
import com.url.shortener.models.User;
import com.url.shortener.repository.ClickEventRepository;
import com.url.shortener.repository.UrlMappingRepository;

@Service
public class UrlMappingService {
	
	@Autowired
	private UrlMappingRepository urlMappingRepository;
	
	@Autowired
	private ClickEventRepository clickEventRepository;

	public UrlMappingDTO createShortUrl(String originalUrl, User user) {
		String shortUrl = generateShortUrl();
		
		UrlMapping urlMapping = new UrlMapping();
		urlMapping.setOriginalUrl(originalUrl);
		urlMapping.setShortlUrl(shortUrl);
		urlMapping.setUser(user);
		urlMapping.setCreatedTime(LocalDateTime.now());
		UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
		return convertToDto(savedUrlMapping);
	}
	
	private UrlMappingDTO convertToDto(UrlMapping urlMapping) {
		
		UrlMappingDTO urlMappingDTO = new UrlMappingDTO();
		urlMappingDTO.setId(urlMapping.getId());
		urlMappingDTO.setOriginalUrl(urlMapping.getOriginalUrl());
		urlMappingDTO.setShortUrl(urlMapping.getShortUrl());
		urlMappingDTO.setUsername(urlMapping.getUser().getUsername());
		urlMappingDTO.setClickCount(urlMapping.getClickCount());
		urlMappingDTO.setCreatedTime(urlMapping.getCreatedTime());
		
		return urlMappingDTO;
		
	}
	

	private String generateShortUrl() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder shortUrl = new StringBuilder();
		
		for(int i=0;i<8;i++) {
			shortUrl.append(characters.charAt(random.nextInt(characters.length())));
		}
		
		return shortUrl.toString();
	}

	public List<UrlMappingDTO> getUrlsByUser(User user) {
		return urlMappingRepository.findByUser(user).stream().map(url -> convertToDto(url)) // Map each UrlMapping to UrlMappingDto
	    .toList(); 
		
	}

	public List<ClickEventDTO> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
		UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
		if(urlMapping != null) {
			
			return clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping, start, end).stream()
				    .collect(Collectors.groupingBy(
				        click -> click.getClickDate().toLocalDate(),
				        Collectors.counting() 
				    ))
				    .entrySet().stream()
				    .map(entity -> {
				        ClickEventDTO clickEventDTO = new ClickEventDTO();
				        clickEventDTO.setClickDate(entity.getKey()); 
				        clickEventDTO.setCount(entity.getValue());
				        return clickEventDTO;
				    })
				    .collect(Collectors.toList()); 

			
		}
		return null;
	}

	public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end) {
		List<UrlMapping> urlMappings = urlMappingRepository.findByUser(user);
		List<ClickEvent> clickEvents = clickEventRepository.findByUrlMappingInAndClickDateBetween(urlMappings, start.atStartOfDay(), end.plusDays(1).atStartOfDay());
		return clickEvents.stream().collect(Collectors.groupingBy(click->click.getClickDate().toLocalDate(),Collectors.counting()));
	}

	public UrlMapping getOriginalUrl(String shortUrl) {
		
		UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
		if(urlMapping!=null) {
			//Saving the analytics
			//1.updating the count in the urlMapping table
			urlMapping.setClickCount(urlMapping.getClickCount()+1);
			urlMappingRepository.save(urlMapping);
			
			//updating the clickEvent table with respect to the urlMapping.
			ClickEvent clickEvent = new ClickEvent();
			clickEvent.setClickDate(LocalDateTime.now());
			clickEvent.setUrlMapping(urlMapping);
			clickEventRepository.save(clickEvent);
		}
		return urlMapping;
	}

}
