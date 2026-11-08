package io.github.seunggeon.planping.common.exception;

import io.github.seunggeon.planping.common.response.ResponseCode;
import lombok.Getter;

/**
 * <pre>
 *  Class Name: BusinessException
 *  Description: 비즈니스 로직 처리 중 발생하는 예외를 위한 커스텀 클래스
 *
 *  History
 *  2026.11.08: Seung-Geon: 클래스 생성
 * </pre>
 */
@Getter
public class BusinessException extends RuntimeException {

  /**
   * 예외에 대한 상태 정보를 담는 {@link ResponseCode}
   */
  private final ResponseCode responseCode;

  /**
   * 에러 코드를 받아 예외 처리
   *
   * @param code 발생한 예외의 상태 정보를 담은 ResponseCode
   */
  public BusinessException(ResponseCode code) {
    super(code.getMessage());
    this.responseCode = code;
  }
}
