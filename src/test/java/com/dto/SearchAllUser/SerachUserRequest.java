package com.dto.SearchAllUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SerachUserRequest {
	private String token;
	private String phone;
	private String testCase;
	
	public String toString() {
		return testCase;
	}
}
