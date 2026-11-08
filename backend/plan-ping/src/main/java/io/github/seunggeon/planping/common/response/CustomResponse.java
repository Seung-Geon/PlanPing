package io.github.seunggeon.planping.common.response;

import lombok.*;

/**
 * <pre>
 *  Class Name: CustomResponse
 *  Description: API 공통 응답 포멧
 *
 *  Example:
 *      1. 성공 응답
 *          {
 *              "code": "S001"
 *              "message": "요청이 성공적으로 처리되었습니다."
 *              "data":{...}
 *          }
 *      2. 실패 응답
 *          {
 *              "code": "[domain]001"
 *              "message": "[실패 이유]."
 *              "data":
 *          }
 *
 *  History
 *  2026.11.08: 클래스 생성
 * </pre>
 *
 * Todo:
 *  현재 @data 어노테이션을 이용하여 한번에 Getter, Setter 등을 불러옴
 *  추후 필요한 어노테이션을 구분하여 처리 필요
 *
 * @param <T>
 */
@Data
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL) // yml에서 직접 설정
public class CustomResponse<T> {
    private final String code;
    private final String message;
    private final T data;

    private CustomResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    /**
     * 반환하는 데이터가 없는 성공 응답
     *
     * @param code
     * @return CustomResponse
     * @param <T>
     */
    public static <T> CustomResponse<T> success(ResponseCode code) {
        return new CustomResponse<>(code.getCode(), code.getMessage(), null);
    }

    /**
     * 반환하는 데이터가 있는 성공 응답
     *
     * @param code
     * @param data
     * @return CustomResponse
     * @param <T>
     */
    public static <T> CustomResponse<T> success(ResponseCode code, T data) {
        return new CustomResponse<>(code.getCode(), code.getMessage(), data);
    }

    /**
     * 요청 처리 실패 응답
     *
     * @param code
     * @return
     * @param <T>
     */
    public static <T> CustomResponse<T> error(ResponseCode code) {
        return new CustomResponse<>(code.getCode(), code.getMessage(), null);
    }

}

/*
    ## JsonInclude
        자바 객체를 JSON데이터로 직렬화하는 과정에서, 특정 조건에 따라 JSON에 포함할 필드를 제어하는 Jackson라이브러리
        불필요한 `null` 값이나 기본값을 JSON 응답에서 제외하여 네트워크 전송 데이터 크기를 줄이고 API 응답을 깔끔하게 만들 때 사용

    ## 주요 옵션
        @JsonInclude(JsonInclude.include.[옵션]) 형태로 사용

        * ALWAYS: 값의 유무와 상관없이 항상 포함(기본값)
        * NON_NULL: 값이 `null` 인 경우 제외
        * NON_ABSENT: `null` 뿐만 아니라 `Optional`, `AtomicReference` 등 부재(Absent) 상태인 경우도 제외
        * NON_EMPTY: `null` 은 물론 비어있는 컬렉션(List, Map 등), 빈 배열, 길이가 0인 문자열도 제외
        * NON_DEFAULT: 자바 기본값(int: 0, boolean: false 등)과 일치하는 경우 제외
        * CUSTOM: 커스텀 로직을 통해 포함 여부를 결정

    ## 참고
        * 직렬화 전용
            `자바 객체 -> JSON`으로 변환하는 과정에만 동작
            반대로 `JSON->자바 객체` 하는 역직렬화에는 동작 X
        * 글로벌 설정
            특정 DTO나 필드마다 붙이기 번거롭다면, 스프링 부트 설정(yml 설정)이나 ObjectMapper 설정을 통해
            프로젝트 전역에서 `NON_NULL` 등을 기본값으로 적용할 수 있음
 */