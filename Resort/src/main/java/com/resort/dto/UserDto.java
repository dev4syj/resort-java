package com.resort.dto;

import java.io.Serializable;

import com.resort.domain.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDto {

	// 회원정보 요청 Dto
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	@Getter
	@Setter
	public static class Request {
		
		private Long userId;

		@Pattern(regexp = "^[a-z0-9]{3,10}$", message = "아이디는 3~10자로 영어 소문자와 숫자만 허용됩니다.")
		@NotBlank(message = "아이디는 필수 입력 값입니다.")
		private String id;

		@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[A-Za-z\\d@#$%^&+=!]{8,20}$", message = "비밀번호는 8~20자로 영어 대소문자, 숫자, @#$%^&+=!만 허용됩니다.")
		@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
		private String password;

		@Pattern(regexp = "^[가-힣a-zA-Z]{2,30}$", message = "한글 또는 영어 대소문자로 이루어진 이름을 입력하세요.")
		@NotBlank(message = "이름은 필수 입력 값입니다.")
		private String name;

		@Pattern(regexp = "^\\d{11,11}$", message = "전화번호는 숫자 11자리만 허용됩니다.")
		@NotBlank(message = "전화번호는 필수 입력 값입니다.")
		private String phone;

		@NotBlank(message = "주소는 필수 입력 값입니다.")
		private String address;

		@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
		@NotBlank(message = "이메일은 필수 입력 값입니다.")
		private String email;
		/*
		 * 로컬파트@도메인파트로 구성 아래와 같은 형식은 통과하지 않는다
		 * 
		 * 1. @mail.com: 로컬 파트가 없는 경우
		 * 2. user@.com: 도메인 파트가 없는 경우
		 * 3. user.com: @ 기호가 없는 경우
		 * 4. user@mail: 최상위 수준 도메인(TLD)이 누락된 경우
		 */

		// Dto -> Entity
		public User toEntity() {
			User user = User.builder()
					.userId(userId)
					.id(id)
					.password(password)
					.name(name)
					.phone(phone)
					.address(address)
					.email(email)
					.build();

			return user;
		}
	}
	
	// 세션정보 저장용 Dto
	@Getter
    public static class Response implements Serializable {
		
		private final Long userId;
        private final String id;
        private final String password;
        private final String name;
        private final String phone;
        private final String address;
        private final String email;
        
        // Entity -> Dto
        public Response(User user) {
            this.userId = user.getUserId();
            this.id = user.getId();
            this.password = user.getPassword();
            this.name = user.getName();
            this.phone = user.getPhone();
            this.address = user.getAddress();
            this.email = user.getEmail();
        }
	}
}