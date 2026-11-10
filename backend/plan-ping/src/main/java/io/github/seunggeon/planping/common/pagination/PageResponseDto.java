package io.github.seunggeon.planping.common.pagination;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * <pre>
 * Class Name: PageResponseDto
 * Description: 페이지네이션 응답을 위한 DTO 클래스
 *
 * History
 * 2026.11.11: 클래스 생성
 * </pre>
 */
@Getter
@ToString
public class PageResponseDto<T> {
    private final List<T> dtoList;
    private final int totalCount; // 전체 데이터 개수
    private final int page;       // 현재 페이지
    private final int size;       // 한번에 보여줄 데이터 개수

    private final int start;      // 화면에 보여줄 시작 번호
    private int end;        // 화면에 보여줄 끝 번호
    private final boolean prev;   // 이전 페이지 존재 여부
    private final boolean next;   // 다음 페이지 존재 여부

    @Builder
    public PageResponseDto(PageRequestDto pageRequestDto, List<T> dtoList, int totalCount) {
        this.page = pageRequestDto.getPage();
        this.size = pageRequestDto.getSize();
        this.totalCount = totalCount;
        this.dtoList = dtoList;

        // 데이터가 0개일 때의 방어 로직 (음수 방지 및 기본값 설정)
        if (totalCount == 0) {
            this.start = 1;
            this.end = 1;
            this.prev = false;
            this.next = false;
            return;
        }

        // start 구하기
        int navigateSize = pageRequestDto.getNavigateSize();
        this.end = (int) (Math.ceil(this.page / (double) navigateSize)) * navigateSize;
        this.start = this.end - (navigateSize - 1);

        // end 구하기
        int last = (int) (Math.ceil((double) totalCount / size));
        this.end = Math.min(end, last);

        this.prev = this.start > 1;
        this.next = totalCount > (this.end * this.size);
    }

}
