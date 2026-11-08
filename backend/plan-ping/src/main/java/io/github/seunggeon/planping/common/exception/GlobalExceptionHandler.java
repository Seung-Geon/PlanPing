package io.github.seunggeon.planping.common.exception;

import io.github.seunggeon.planping.common.response.CustomResponse;
import io.github.seunggeon.planping.common.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * <pre>
 *  Class Name: GlobalExceptionHandler
 *  Description: 전역 예외 처리를 위한 @RestControllerAdvice
 *
 *  History
 *  2026.11.08: Seung-Geon: 클래스 생성 및 기본 처리
 * </pre>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * <pre>
     * 비즈니스 예외 처리
     *
     * - {@link BusinessException} 타입의 예외를 처리
     * - 서비스 로직 상의 예외 상황(예: 유효하지 않은 요청, 데이터 불일치 등)을 담당
     * - 예외에 포함된 {@link ResponseCode}를 기반으로 에러 응답을 생성
     *</pre>
     *
     * @param e 발생한 BusinessException
     * @return 에러 정보를 담은 ResponseEntity
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<CustomResponse<Void>> handleBusinessException(BusinessException e) {
        log.error("Business Exception: {}", e.getMessage(), e);
        ResponseCode code = e.getResponseCode();
        return ResponseEntity.status(code.getStatus()).body(CustomResponse.error(code));
    }

    /**
     * Spring Security 권한 위반 예외 처리
     * - {@code @PreAuthorize} 등으로 보호된 리소스에 권한이 없는 사용자가 접근할 때 발생
     * - 예: 일반 회원이 어드민 전용 API(AdminGroupController)를 호출하는 경우
     * - 항상 403 Forbidden을 반환
     *
     * @param e 발생한 AccessDeniedException
     * @return 403 에러 응답을 담은 ResponseEntity
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<CustomResponse<Void>> handleAccessDeniedException(AccessDeniedException e) {
        log.error("AccessDeniedException: {}", e.getMessage(), e);
        ResponseCode code = ResponseCode.FORBIDDEN; // 권한 없음
        return ResponseEntity.status(code.getStatus()).body(CustomResponse.error(code));
    }

    /**
     * 요청 본문 검증 실패 예외 처리
     * - {@code @Valid @RequestBody} DTO의 필드 검증(@NotBlank 등)이 실패할 때 발생
     * - 예: GroupWarningRequestDto.warningReason이 빈 값인 경우
     * - 항상 400 Bad Request(C001)를 반환하며, 첫 번째 실패 필드의 메시지를 포함
     *
     * @param e 발생한 MethodArgumentNotValidException
     * @return 400 에러 응답을 담은 ResponseEntity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CustomResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.warn("MethodArgumentNotValidException: {}", e.getMessage());
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(ResponseCode.INVALID_INPUT_VALUE.getMessage());

        return ResponseEntity
                .status(ResponseCode.INVALID_INPUT_VALUE.getStatus())
                .body(CustomResponse.error(ResponseCode.INVALID_INPUT_VALUE, message));
    }

}
