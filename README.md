### 목차

>
> 1. [프로젝트 소개](#프로젝트-소개)
     >
+ [팀원](#팀원)
> 2. [ERD](#ERD)
> 3. [기술 스택](#기술-스택)
> 4. [아키텍처](#아키텍처)
> 5. [주요 기능](#주요-기능)
     >
+ [전체 기능 요약](#전체-기능-요약)
>     + 공통 : [회원가입, 로그인](#회원가입-로그인) | [상품 목록 및 상품 상세 조회](#상품-목록-및-상품-상세-조회)
>     + 사용자 : [장바구니 관리](#장바구니-관리) | [주문 내역 관리](#주문-내역-관리) | [리뷰 관리](#리뷰-관리)
>     +
관리자 : [상품 및 상품 카테고리 관리](#상품-및-상품-카테고리-관리) | [옵션 카테고리 및 옵션 관리](#옵션-카테고리-및-옵션-관리) | [주문 수락 및 주문 상태 관리](#주문-수락-및-주문-상태-관리)
> 6. [api 명세](#api-명세)
> 7. [형상 관리](#형상-관리)
> 8. [팀 문화?](#팀-문화)

# 프로젝트 소개

> ### 개발 기간 및 인원
> 23.08.07 ~ 23.09.14 (5주) \
> 백엔드 5명

> ### 배포 링크 (Swagger API Test 가능) 👈 클릭!
> 링크 달아야 함!

## 팀원
<div align="center">
<table align="center"> <!-- 팀원 표 -->
  <tr>
   <th >
    Backend 강우영
   </th>
   <th>
    Backend 김민수
   </th>
   <th >
    Backend 김영선
   </th>
   <th >
    Backend 박예선
   </th>
   <th >
    Backend 백지연
   </th>
   </tr>
  <tr>
    <td align="center">
        <img src="https://github.com/YesunPark/cafe-bom/assets/108933466/801e2b25-e3fa-4c9e-83e3-43b3b0280a78" width=300px alt="강우영"> 
        <br/>
    </td>
    <td align="center">
        <img src="https://github.com/YesunPark/cafe-bom/assets/108933466/871f84d5-a2da-49e0-927e-8c023f2419ed" width=300px alt="김민수"> 
        <br/>
    </td>
    <td align="center">
        <img src="https://github.com/YesunPark/cafe-bom/assets/108933466/98f1d69f-3890-45af-9c90-2221b478dd7b" width=300px alt="김영선"> 
        <br/>
    </td>
    <td align="center">
        <img src="https://user-images.githubusercontent.com/61264510/220117269-9ca3a740-5483-4c26-83f1-3fe2aa3f957b.png" width=300px alt="박예선"> 
        <br/>
    </td>
    <td align="center">
        <img src="https://github.com/YesunPark/cafe-bom/assets/108933466/77fe609d-7718-434e-b1ca-98dc61a40816" width=300px alt="백지연"> 
        <br/>
    </td>
  </tr>
  <tr>
    <td align="center" class="우영">
        <a href="https://github.com/sunlake123"><img alt="github-link" height="25" src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/></a>
        <br/>
        <a href="https://velog.io/@sunlake123"><img alt="vlog-link" height="25" src="https://img.shields.io/badge/Tech blog-20C997?style=flat-square&logo=Velog&&logoColor=white"/></a>
   </td>
   <td align="center" class="민수">
        <a href="https://github.com/bingsoo95"><img alt="github-link" height="25" src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/></a>
   </td>
   <td align="center" class="영선">
        <a href="https://github.com/thsu1084"><img alt="github-link" height="25" src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/></a>
        <br/>
        <a href="https://velog.io/@lynn080"><img alt="vlog-link" height="25" src="https://img.shields.io/badge/Tech blog-20C997?style=flat-square&logo=Velog&&logoColor=white"/></a>
        <br/>   
        <a href="mailto:lynn08082@gmail.com"><img alt="gmail-link" height="25" src="https://img.shields.io/badge/Email-d14836?style=flat-square&logo=Gmail&&logoColor=white"/></a>
   </td>
   <td align="center" class="예선">
        <a href="https://github.com/YesunPark"><img alt="github-link" height="25" src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/></a>
        <br/>
        <a href="https://velog.io/@lynn080"><img alt="vlog-link" height="25" src="https://img.shields.io/badge/Tech blog-20C997?style=flat-square&logo=Velog&&logoColor=white"/></a>
        <br/>   
        <a href="mailto:lynn08082@gmail.com"><img alt="gmail-link" height="25" src="https://img.shields.io/badge/Email-d14836?style=flat-square&logo=Gmail&&logoColor=white"/></a>
   </td>
   <td align="center" class="지연">
        <a href="https://github.com/rkoji"><img alt="github-link" height="25" src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/></a>
   </td>
  </tr>
  <tr>
    <td align="center" class="우영">
        -
   </td>
    <td align="center" class="민수">
        <a href="https://velog.io/@lynn080">프로젝트 회고</a>
   </td>
    <td align="center" class="영선">
        <a href="https://velog.io/@lynn080">프로젝트 회고</a>
   </td>
    <td align="center" class="예선">
        <a href="">프로젝트 회고</a>
   </td>
    <td align="center" class="지연">
        <a href="https://velog.io/@lynn080">프로젝트 회고</a>
   </td>
  </tr>

</table>
</div>

# ERD

[👉 ERD Cloud에서 직접 보기](https://www.erdcloud.com/d/pqop6rtCyk7PzkgLD)

![ERD 0913](https://github.com/YesunPark/cafe-bom/assets/108933466/7a19031e-e20a-40b7-bb50-36f2a59bdcef)

# 기술 스택

### Back-end

- java 11 - 개발언어를 java로 선택해 사용(했어요?,했습니다?)
- springboot 3.0 - java를 기반으로 한 웹 어플리케이션인 spring boot 사용해 서버 구축
- spring security - 회원가입, 로그인 보안을 담당
- spring jpa - 구현된 클래스와 매핑을 해주기 위해 사용
- json web tokens - 로그인한 회원의 정보를 토큰으로 발급받기 위해 사용
- junit - 테스트하기 위해 사용
- mockmvc - API테스트 하기 위해 사용

### Database

- MariaDB - 데이터베이스로 사용
- h2

### DevOps

- Amazon EC2 - 서버구축
- Amazon S3 - 이미지 파일 저장
- Docker - 서버구축
- swagger - API 문서화를 위해 사용

### Collaboration

- jira, notion, slack - 팀프로젝트를 위해 다양한 커뮤니티 tool 사용

# 아키텍처

도식화 그림만

# 주요 기능

### 요약

<table align="center"><!-- 팀원 표 -->
  <tr>
   <th>
    공통
   </th>
   <th>
    사용자
   </th>
   <th >
    관리자
   </th>
   </tr>
  <tr>
   <td align="left" width="350px" class="공통">
    - 회원가입, 로그인
    <br/>
    - 상품 목록 및 상품 상세 페이지 조회
   </td>
   <td align="left" width="350px" class="사용자">
    - 장바구니 관리
    <br/>
    - 주문 내역 관리
    <br/>
    - 리뷰 관리
   </td>
   <td align="left" width="350px" class="관리자">
    - 상품 및 상품 카테고리 관리
    <br/>
    - 옵션 및 옵션 카테고리 관리
    <br/>
    - 주문 수락 및 주문 상태 관리
   </td>
  </tr>
</table>

## 공통 기능

### 회원가입, 로그인

### 상품 목록 및 상품 상세 조회

---

## 사용자 기능

### 장바구니 관리

### 주문 내역 관리

### 리뷰 관리

---

## 관리자 기능

### 상품 및 상품 카테고리 관리

- 관리자는 상품을 등록할 수 있다.
- 관리자는 상품을 수정할 수 있다.
- 관리자는 상품을 삭제할 수 있다.
- 관리자는 상품을 전체, 단건 조회할 수 있다.
- 관리자는 상품 품절여부를 변경할 수 있다.

- 관리자는 상품 카테고리를 등록할 수 있다.
- 관리자는 상품 카테고리를 수정할 수 있다.
- 관리자는 상품 카테고리를 삭제할 수 있다.
- 관리자는 상품 카테고리를 전체,단건 조회할 수 있다.

### 옵션 및 옵션 카테고리 관리

- 옵션 및 옵션 카테고리의 관리를 위해 전체 목록을 조회하거나 각각 조회할 수 있다.
- 옵션과 옵션 카테고리를 새로 등록하거나, 이름 또는 가격을 수정, 삭제해서 관리할 수 있다.
- 옵션 카테고리 별로 옵션을 나눠 관리할 수 있다.

### 주문 수락 및 주문 상태 관리

---

# 형상 관리

| Notion                                                                                                  | Jira                                                                                                 |
|:--------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------|
| ![Notion1](https://github.com/YesunPark/cafe-bom/assets/108933466/daab4e2f-1859-4b6f-9d91-d4cdeabe9687) | ![Jira](https://github.com/YesunPark/cafe-bom/assets/108933466/63d9e02e-49f4-458a-992f-46f490d1da30) | 

# api 명세

| Domain       | URL                                                                        | Http Method                 | description       | 접근 권한 |
|:-------------|:---------------------------------------------------------------------------|:----------------------------|:------------------|:------|
| **Auth**     | /signup                                                                    | `POST`                      | 사용자 회원가입          | -     |
|              | /signup/admin                                                              | `POST`                      | 관리자 회원가입          | -     |
|              | /signin                                                                    | `POST`                      | 사용자/관리자 로그인       | -     |
| **Prouduct** | /product/list/{categoryId}                                                 | `GET`                       | 카테고리 별 상품 목록 조회   | -     |
|              | /product/best-list                                                         | `GET`                       | 베스트 상품 목록 조회      | -     |
|              | /product/{productId}                                                       | `GET`                       | 상품 상세 조회          | -     |
|              | /admin/product                                                             | `POST`                      | 상품 등록             | ADMIN |
|              | /admin/product/{productId}                                                 | `GET` `PUT` `DELETE`        | 상품 조회, 수정, 삭제     | ADMIN |
|              | /admin/product?productId={productId}&soldout={soldOutStatus}               | `PUT`                       | 상품 품절 여부 수정       | ADMIN |
|              | /admin/option/{optionId}                                                   | `GET` `PUT` `POST` `DELETE` | 상품 옵션 CRUD        | ADMIN |
|              | /admin/category/{categoryId}                                               | `GET` `PUT` `POST` `DELETE` | 상품 카테고리 CRUD      | ADMIN |
|              | /admin/option-category                                                     | `GET` `PUT` `POST` `DELETE` | 옵션 카테고리 CRUD      | ADMIN |
| **Orders**   | /auth/pay/list?viewType={viewType}&startDate={startDate}&endDate={endDate} | `GET`                       | 구매 내역 조회          | USER  |
|              | /auth/orders/elapsed-time/{ordersId}                                       | `GET`                       | 주문 경과 시간 조회       | USER  |
|              | /auth/orders/cancel/{ordersId}                                             | `PATCH`                     | 주문 취소             | USER  |
|              | /admin/orders/status/{ordersId}                                            | `PATCH`                     | 주문 상태 변경          | ADMIN |
|              | /admin/orders/cooking-time/{ordersId}                                      | `PATCH`                     | 예상 조리 시간 선택       | ADMIN |
|              | /admin/orders/receipt-status/{ordersId}                                    | `PATCH`                     | 주문 수락 또는 거절       | ADMIN |
| **Cart**     | /auth/cart                                                                 | `GET`                       | 장바구니 상품 목록 조회     | USER  |
|              | /auth/cart                                                                 | `POST` `DELETE`             | 장바구니 상품 수량 변경, 삭제 | USER  |
|              | /auth/cart/save                                                            | `POST`                      | 장바구니 상품 추가        | USER  |
|              | /auth/pay                                                                  | `PUT`                       | 장바구니 전체 결제        | USER  |
| **Review**   | /auth/review                                                               | `POST`                      | 리뷰 등록             | USER  |

# 팀 문화

1. 상호 존중과 화합
    - 질문할 때나 리뷰할 때나 미팅할 때나 늘 상호존중! 즐거운 협업이 될 수 있도록 노력합니다.
2. 바쁜 하루를 마무리하는 미팅
    - ❤️‍ Daily Scrum 🔥
        - 주중 저녁, 매일 서로 어떤 하루를 보냈는지를 알기 위해 데일리 스크럼을 진행합니다.
        - 무엇을 하고 있었는지, 무엇을 새로 시작했는지, 앞으로 무엇을 할 것인지, 그리고 그 날의 Trouble Shooting과 PR 확인 여부 등을 공유합니다.
        - 논의 사항이 있다면 Notion에 미리 기록해 Daily Scrum에서 논의합니다.
3. 한 주를 마무리하는 미팅
    - ✈️ Sprint Plan ✈️
        - 일주일을 시작하는 월요일에 한 주 동안 어떤 일을 할지 계획을 세웁니다.
    - 🏆 Sprint Result 🏆
        - 지난 Sprint Plan에서 세웠던 계획을 얼마나 이행했는지에 대해 돌아봅니다.
    - 📬 Sprint Review 📬
        - 전체적으로 어떠한 한 주를 보냈는지에 대해 업무적으로 좋았던 부분/아쉬웠던 부분, 개인적으로 좋았던 부분/아쉬웠던 부분에 대해 작성하고 발표합니다.
4. 즐거운 개발 시간, 🕜 Core Time 🕜
    - 협업 효율을 올리기 위해 주중에 다 같이 개발하는 시간인 코어 타임을 가집니다.
    - 주중 오후 2 ~ 9시는 필수, 주말은 자유입니다.
5. 만남의 광장 GatherTown
    - GatherTown에서 회의를 진행하고, 대화할 일이 있다면 모여서 토의합니다.
6. 내가 아는 건 모두가!
    - Notion을 통해 프로젝트 전반의 결정 사항과 회의 내용, 회의 결과, 컨벤션, ERD 등을 기록합니다.
    - Slack을 통해 전달할 사항이나 개발 외적인 논의사항 그리고 Jira, GitHub 과의 프로젝트 PR 연동을 통해 즉각적인 피드백이 이루어질 수 있도록 합니다.
    - Jira를 통해 서로의 작업 계획, 진척도, 현재 어떠한 작업을 하고 있는지에 대해 파악할 수 있도록 합니다.
7. 모두의 Pull Request
    - PR을 올린 당사자를 제외한 3인 이상의 Approve를 받아야 PR을 Merge할 수 있습니다.