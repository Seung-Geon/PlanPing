# [의사결정 주제]

* **작성일:** 2026-11-10
* **상태:** [결정됨]

## 1. 배경 (Context)
* 개발 시 Pagination을 어떻게 구현할 것인가에 대한 고민

## 2. 고려한 대안 (Alternatives)
### **Spring Data의 Page\<T\> 클래스 사용** 
* 장점
    * 별도의 DTO나 변환 로직을 작성할 필요 X
    * Repository가 반환하는 Page를 그대로 Controller에서 반환
    * 현재 페이지 번호, 전체 페이지 수, 전체 데이터 개수, 다음/이전 페이지 여부, 정렬 정보 등 다양한 메타 데이터를 기본 제공
* 단점
    * Page 객체 내부에는 프론트에서 사용하지 않는 내부 속성이나 복잡한 객체(Pageable 등)가 포함되어 있어 응답 JSON이 불필요하게 비대해질 수 있음
    * Spring Data 버전이 업그레이드되거나 내부 구현이 바뀔 경우, API 응답 구조가 영향을 받을 수 있음
### **Pagination을 위한 클래스 만들기** 
* 장점 
    * 프론트엔드에서 실제로 필요로 하는 필드만 깔끔하게 추려서 줄 수 있음
    * 무한 스크롤 등으로 페이징 방식을 전환하거나, 프론트엔드 요구사항에 따라 응답 필드명을 변경해야 할때 백엔드 비즈니스 로직이나 엔티티를 건드릴 필요 없이 DTO만 수정하면 됨
* 단점
    * 별도의 DTO나 변환 로직이 필요

## 3. 결정 (Decision)
-  ***Pagination을 위한 클래스 만들기***

## 4. 선택 이유 (Reason)
1. 프로젝트 자체가 학습을 위함
2. JSON의 비대화 방지
3. Entity -> DTO 변환은 페이징 유무와 상관없이 항상 존재하는 과정으로, 추가적인 단점이 되지 않음

* Page\<T\>에 있는 필드
```
{
  "content": [ ... ],                // O: 실제 데이터 리스트 (필요함)
  "pageable": {                      // X: 불필요
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 45,               // O: 전체 데이터 개수 (필요할 수 있음)
  "totalPages": 5,                   // O: 전체 페이지 수 (필요함)
  "last": false,                     // O: 마지막 페이지 여부 (필요함)
  "size": 10,                        // X: 'pageSize'와 중복됨
  "number": 0,                       // X: 'pageNumber'와 이름이 직관적이지 않음 (보통 0부터 시작해서 혼동 줌)
  "sort": {                          // X: 'pageable' 안에도 있고 바깥에도 중복으로 들어감
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "numberOfElements": 10,            // X: 현재 페이지의 데이터 개수는 content.length로 바로 알 수 있음
  "first": true,                     // O: 첫 번째 페이지 여부 (가끔 쓰임)
  "empty": false                     // X: 비어있는지 여부는 content.length == 0 으로 쉽게 확인 가능
}
```

## 5. 예상 결과 및 트레이드오프 (Consequences)
* **긍정적 효과:** 프론트엔드에서 실제 필요한 페이징 데이터만 깔끔하고 직관적인 구조로 받을 수 있음
* **부정적 효과 (Trade-off):** 새로운 페이징 목록 조회 API를 만들 대마다 Entity -> DTO 변환 과정 또는 매핑 코드를 반복 작성해야 한다.