package com.example.redditclone.dtos;

public class CodeRequestDto {
        private String code;
        private String email;
        public CodeRequestDto() {
        }

        public CodeRequestDto(String code, String email) {
            this.code = code;
            this.email = email;
        }

        public String getCode() {
            return code;
        }

        public String getEmail() {
            return  email;
        }
}
