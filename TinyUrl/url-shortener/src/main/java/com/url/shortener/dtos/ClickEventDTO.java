package com.url.shortener.dtos;

import java.time.LocalDate;

public class ClickEventDTO {
	private LocalDate clickDate;
	private Long count;
	public LocalDate getClickDate() {
		return clickDate;
	}
	public void setClickDate(LocalDate clickDäte) {
		this.clickDate = clickDäte;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	
}
