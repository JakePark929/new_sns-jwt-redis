package com.jake.sns.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {
    private String resultCode;
    private T result;

    public static CommonResponse<Void> error(String errorCode) {
        return new CommonResponse<>(errorCode, null);
    }

    public static <T> CommonResponse<T> success(T result) {
        return new CommonResponse<>("SUCCESS", result);
    }
}
