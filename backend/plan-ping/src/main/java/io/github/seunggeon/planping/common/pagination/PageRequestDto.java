package io.github.seunggeon.planping.common.pagination;

import lombok.Getter;
import lombok.ToString;

/**
 * <pre>
 * Class Name: PageRequestDto
 * Description: 페이지네이션의 설정값을 받기 위한 클래스
 *     
 * History
 * 2026.11.10: 클래스 생성
 * </pre>
 */
@Getter
@ToString
public class PageRequestDto {
    private int page;
    private int size;
    private int navigateSize;   // 번호 목록의 개수

    public PageRequestDto() {
        this.page = 1;
        this.size = 10;
        this.navigateSize = 10;
    }

    /**
     * 몇 번째 페이지인지 설정하는 Setter
     *
     * @param page 보고자 하는 page
     */
    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    /**
     * 한번에 보여줄 데이터의 개수를 설정하는 Setter
     *
     * @param size 설정하고자 하는 데이터의 개수
     */
    public void setSize(int size) {
        this.size = (size <= 0 || size >= 100) ? 10 : size;
    }

    /**
     * 한번에 보여줄 번호 목록의 개수를 설정하는 Setter
     *
     * @param navigateSize 한번에 보여줄 목록의 개수
     */
    public void setNavigateSize(int navigateSize) {
        this.navigateSize = (navigateSize <= 0 || navigateSize >= 30) ? 10 : navigateSize;
    }

    /**
     * query에서 사용할 offset을 얻기 위한 Getter
     *
     * @return query의 offset으로 넣을 값
     */
    public int getSkip() {
        return (page - 1) * size;
    }
}
