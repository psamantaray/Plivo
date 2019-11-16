package com.dto.SearchAllUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SearchAllUserRequestDTO {
	private String jwtToken;
	private String testCase;
	
	public String toString() {
		return testCase;
	}
}
