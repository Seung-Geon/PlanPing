package io.github.seunggeon.planping.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * <pre>
 *  Class Name: ResponseCode
 *  Description: API 응답 상태 코드 및 메시지 정의
 *
 *  History
 *  2026.11.08: Seung-Geon: 클래스 생성 및 기본 성공/실패 정의
 * </pre>
 */
@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    // =============================================
    // 성공 응답
    // =============================================
    OK(HttpStatus.OK, "S001", "요청이 성공적으로 처리되었습니다."),
    CREATED(HttpStatus.CREATED, "S002", "리소스가 성공적으로 생성되었습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "S003", "요청은 성공했으나, 반환할 데이터가 없습니다."),
    ACCEPTED(HttpStatus.ACCEPTED, "S004", "요청이 접수되었으며, 처리가 진행 중입니다."),
    // =============================================
    // 실패 응답(common)
    // =============================================
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C002", "서버 내부 오류가 발생했습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "C003", "접근 권한이 없습니다.");



    private final HttpStatus status;
    private final String code;
    private final String message;
}
