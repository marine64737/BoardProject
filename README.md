# Board Project


## 프로젝트 개요

- 개인 프로젝트
- 프로젝트 명: Spring Boot 기반 CRUD 게시판 프로젝트
- 기능: 댓글, 파일 업로드, 로그인, 페이지네이션

## 프로젝트 기간

- 2025.05.08 ~ (진행 중)
- 기능 추가 및 리팩토링 진행 중

## 개발 환경

- Language: Java 17
- Framework: Spring Boot 3.5, Spring Security
- Build Tool: Gradle
- DB: H2 (MySQL 연동한 Branch도 존재)
- Template: Mustache
- 기타: Lombok, JPA, etc

---

## 주요 기능

- 회원가입 / 로그인 (세션 기반, Spring Security 적용)
- 게시글 CRUD
- 댓글 비동기 처리 (Javascript + JSON)
- 파일 업로드 (UUID 저장 방식)
- 페이징 처리 (Spring Pageable)
- 예외 처리 및 로깅

## 실행 및 테스트 방법

1. Git clone 후, application.yml 또는 application.properties 설정
2. DB 연동 (H2 사용)
3. 로컬에서 실행: `./gradlew bootRun`
4. 브라우저 접속: `http://localhost:8080/`

## 주요 개선 사례

### 1. 댓글 비동기 처리 (Javascript)
- **Before**: 댓글 작성 후 전체 페이지 리로드
- **After**: 비동기 방식으로 댓글 추가/삭제
- **이유**: UX 개선 + 서버 리소스 절약

### 2. Spring Security 로그인 구현
- 기존 세션 방식에서 보안성과 확장성을 고려해 Spring Security 도입
- 커스텀 로그인 화면 구현 및 인증/인가 분리

### 3. 파일 업로드 - UUID 적용
- 파일명 중복 문제 방지를 위해 UUID 기반 이름으로 저장
- 확장자 유지 및 원본명은 DB에 따로 저장

### 4. DTO와 Entity 분리
- API 응답 시 Entity 노출 → 보안/결합도 문제 발생
- DTO 사용해 명확한 데이터 전달 및 유지보수 용이성 확보

