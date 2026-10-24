# DB ERD 설계

## 1. ER 다이어그램
> 데이터베이스 테이블 간의 관계를 시각화한 다이어그램입니다.
(여기에 ERDCloud, AQueryTool 등에서 그린 이미지 캡처본을 첨부하거나, 하단에 설명할 Mermaid 코드를 넣습니다.)

---

## 2. 테이블 명세서 (Data Dictionary)

### 2.1 회원 (users)
| 물리명 (Column) | 논리명 (Description) | 데이터 타입 (Type) | PK/FK | Null | 기본값 (Default) |
| :--- | :--- | :--- | :---: | :---: | :--- |
| `user_id` | 사용자 고유 ID | BIGINT | PK | N | Auto Increment |
| `email` | 로그인 이메일 | VARCHAR(100) | | N | |
| `password` | 암호화된 비밀번호 | VARCHAR(255) | | N | |
| `nickname` | 닉네임 | VARCHAR(50) | | N | |
| `created_at` | 가입일시 | DATETIME | | N | CURRENT_TIMESTAMP |

### 2.2 계획 (plans)
| 물리명 (Column) | 논리명 (Description) | 데이터 타입 (Type) | PK/FK | Null | 기본값 (Default) |
| :--- | :--- | :--- | :---: | :---: | :--- |
| `plan_id` | 계획 고유 ID | BIGINT | PK | N | Auto Increment |
| `user_id` | 작성자 ID | BIGINT | FK | N | |
| `title` | 계획 제목 | VARCHAR(100) | | N | |
| `status` | 달성 상태 (TODO/DONE)| VARCHAR(20) | | N | 'TODO' |

*(마찬가지로 알림, 통계 등 필요한 테이블을 계속 추가합니다.)*

---

## 3. 핵심 설계 결정 사항 (💡 포트폴리오용 핵심 포인트)
> 개발자로서 DB 구조를 짤 때 스스로 고민하고 결정한 내용들을 기록합니다.

- **Soft Delete 적용 여부:** 사용자가 계획을 삭제했을 때 DB에서 완전히 날릴 것인가(Hard Delete), 아니면 `is_deleted` 컬럼을 두어 숨길 것인가(Soft Delete)? (통계 데이터를 위해 Soft Delete를 선택했다 등)
- **데이터 타입 선택 이유:** 상태값을 관리할 때 정수형(0, 1)을 쓸지, 문자열(TODO, DONE)을 쓸지, Enum을 쓸지 고민한 결과.
- **인덱스(Index) 고려:** 나중에 사용자가 많아져서 '오늘 날짜의 알림'을 빠르게 조회하려면 어떤 컬럼에 인덱스를 걸어야 할지.