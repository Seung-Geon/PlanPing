# API Specifications
PlanPing 프로젝트의 클라이언트-서버 간 API 명세서를 관리하는 디렉토리입니다.

##  작성 기준
- **Base URL:** `http://localhost:8080/api` (개발 환경)
- 응답 형식은 항상 일관된 JSON 포맷을 유지하도록 설계합니다.

## 도메인별 API 문서
| 도메인 | 문서 링크 (파일명) | 설명 |
| :---: | :--- | :--- |
| 회원 (User) | [User API](./01_user_api.md) | 회원가입, 로그인, 인증 관련 API |
| 계획 (Plan) | [Plan API](./02_plan_api.md) | 계획 생성, 조회, 수정, 삭제 (CRUD) API |
| 통계 (Stats)| [Stats API](./03_stats_api.md) | 달성률 및 실천 통계 조회 API |

> **💡 참고:** 추후 Spring Boot에 Swagger(Springdoc)나 Spring REST Docs를 도입하여 API 문서 자동화를 구축할 경우, 해당 설정이나 접근 링크를 이 문서에 업데이트할 예정입니다.