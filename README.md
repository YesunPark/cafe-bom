### 목차

>
> 1. [ERD](#ERD)
> 2. [시스템 아키텍처](#시스템-아키텍처)
> 3. [기술 스택](#기술-스택)
> 4. [주요 기능](#주요-기능)
>   + 공통 : [회원가입, 로그인](#회원가입-로그인) | [상품 목록 및 상품 상세 조회](#상품-목록-및-상품-상세-조회)
>   + 사용자 : [장바구니 관리](#장바구니-관리) | [주문 내역 관리](#주문-내역-관리) | [리뷰 관리](#리뷰-관리)
>   + 관리자 : [상품 및 상품 카테고리 관리](#상품-및-상품-카테고리-관리) | [옵션 카테고리 및 옵션 관리](#옵션-카테고리-및-옵션-관리) | [주문 수락 및 주문 상태 관리](#주문-수락-및-주문-상태-관리)
> 5. [api 명세](#api-명세)
> 6. [형상 관리 - Notion, Jira](#형상-관리)
> 7. [팀 문화](#팀-문화)

# 프로젝트 소개


> 하루 한 번 가지는 음료 한잔의 소소한 행복을 더욱 편리하게 해주는 온라인 카페 주문 시스템입니다.\
> 사용자에게는 간편한 메뉴확인과 빠른 주문이라는 편의성, 포인트 적립이라는 이득을 제공합니다.\
> 관리자에게는 시스템 사용의 이득을 통한 사용자 모집 효과, 간편한 주문관리를 제공합니다.
> 
> SpringBoot와 Spring Data JPA를 사용해 기본적인 REST API를 구현하고,\
> Docker, AWS, S3 등을 이용해 서버를 배포했습니다.

> ### 개발 기간 및 인원
> 23.08.07 ~ 23.09.14 (5주) \
> 백엔드 5명

> ### [배포 링크 (Swagger API Test 가능)](http://43.201.95.177:8080/swagger-ui/#) 👈 클릭!

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
        <br/>   
        <a href="mailto:wl0wn1212@gmail.com"><img alt="gmail-link" height="25" src="https://img.shields.io/badge/Email-d14836?style=flat-square&logo=Gmail&&logoColor=white"/></a>
   </td>
  </tr>
  <tr>
    <td align="center" class="우영">
        -
   </td>
    <td align="center" class="민수">
        -
   </td>
    <td align="center" class="영선">
        -
   </td>
    <td align="center" class="예선">
        <a href="https://velog.io/@lynn080/BackEnd-%ED%8C%80-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%9A%8C%EA%B3%A0">프로젝트 회고</a>
   </td>
    <td align="center" class="지연">
        <a href="https://momentous-tulip-597.notion.site/0cfa442944d54907813b4125c7ed66e1?pvs=4">프로젝트 회고</a>
   </td>
  </tr>

</table>
</div>


# ERD

[👉 ERD Cloud에서 직접 보기](https://www.erdcloud.com/d/pqop6rtCyk7PzkgLD)

![ERD 0913](https://github.com/YesunPark/cafe-bom/assets/108933466/71aefe7c-ab4b-44fb-a7e9-9585f9875e15)



# 시스템 아키텍처

![cafe-bom 시스템 아키텍처](https://github.com/YesunPark/cafe-bom/assets/108933466/abc731cd-b95f-459f-af97-7886f3aca88d)



# 기술 스택

### Back-end

<img height="100px" src="https://github.com/YesunPark/cafe-bom/assets/108933466/92190376-2d45-48b1-bc7f-4ee9ffaa769c"/>

- **Java 11** 
- **SpringBoot 2.7.14**
- **Spring Data JPA**
- **Spring Security**
- **JWT(json web tokens)**
- **JUnit** 
- **Swagger**

### Database

<img height="100px" src="https://github.com/YesunPark/cafe-bom/assets/108933466/15a8434c-b76f-4249-9744-e8caa9e50de9"/>

- **MariaDB** 
- **H2**

### DevOps

<img height="100px" src="https://github.com/YesunPark/cafe-bom/assets/108933466/a4b60347-696e-4e30-845a-a440c78a7850"/>
<br/>

- **Docker**
- **Amazon EC2**
- **Amazon S3** - 상품 및 리뷰 이미지 저장

### Collaboration

<img height="100px" src="https://github.com/YesunPark/cafe-bom/assets/108933466/37fabc67-fd5d-4b7d-a702-b2e9992b7449"/>
<br/>

- **GitHub**
- **Jira**
- **Notion**
- **Slack**
- **GatherTown**



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

## [ 공통 기능 ]

### 회원가입, 로그인
- 사용자는 이메일, 비밀번호, 닉네임, 휴대전화번호를 이용해 회원가입할 수 있다.
- 관리자는 이메일, 비밀번호, 관리자 인증코드, 실명을 이용해 회원가입할 수 있다.
- 회원가입 시 사용한 이메일과 비밀번호를 이용해 로그인할 수 있다.

### 상품 목록 및 상품 상세 조회
- 상품 카테고리 별 상품 목록을 조회할 수 있다.
  - 판매중인 상품과 매진된 상품이 전부 조회되며, 카테고리 별 상품이 아닌 전체 상품 목록 조회는 불가능하다.
- 베스트 상품 목록
  - 주문량이 많은 순서대로 5개의 상품들이 조회된다.
- 상품의 상세 정보를 조회할 수 있다. 상세정보에는 상품의 정보와 추가할 수 있는 옵션이 포함되어 있다.


## [ 사용자 기능 ]

### 장바구니 관리
- 장바구니에 상품을 추가한 후, 상품의 수량을 조절하거나 상품을 삭제할 수 있다.

### 주문 관리
- 결제를 하면 장바구니에 담겨있는 모든 상품이 주문 내역으로 생성된다.
- 기본적으로 3개월간의 주문 내역이 조회되고, 기간을 설정할 시 설정한 기간동안의 내역이 조회된다.
- 본인의 모든 주문 내역을 조회할 수 있다.
- 주문한 상품들의 조리가 시작된 후 시간이 얼마나 경과되었는지 조회할 수 있다.
- 관리자가 주문을 수락하기 이전에는 사용자가 주문을 취소할 수 있다.


### 리뷰 관리
- 본인이 주문한 상품에 대해 리뷰 내용, 별점(1~5점), 사진 1장으로 리뷰를 등록할 수 있다.


## [ 관리자 기능 ]

### 상품 및 상품 카테고리 관리

- 상품을 등록할 수 있다.(상품 사진은 1장만 등록할 수 있다.)
- 등록한 상품의 정보를 수정, 삭제할 수 있다.
- 상품 전체 목록을 조회할 수 있고, 상품 하나의 정보도 조회할 수 있다.
- 상품의 품절 여부를 변경할 수 있다.

- 상품 카테고리를 등록할 수 있고, 등록한 상품 카테고리를 수정, 삭제할 수 있다.
- 상품 카테고리 전체 목록을 조회할 수 있고, 상품 카테고리 하나의 정보도 조회할 수 있다.

### 옵션 및 옵션 카테고리 관리
- 옵션 및 옵션 카테고리를 등록할 수 있고, 등록한 옵션을 수정, 삭제할 수 있다.
- 옵션 및 옵션 카테고리의 전체 목록을 조회할 수 있고, 하나의 정보도 조회할 수 있다

### 주문 관리
- 주문이 어떻게 진행되고 있는지 확인할 수 있도록 주문의 상태를 변경할 수 있다.
- 주문이 들어오면 예상 조리 시간을 선택해야 한다.
- 주문이 들어오면 주문을 수락하거나 거절할 수 있다.




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
| **Order**    | /auth/pay/list?viewType={viewType}&startDate={startDate}&endDate={endDate} | `GET`                       | 구매 내역 조회          | USER  |
|              | /auth/order/elapsed-time/{orderId}                                         | `GET`                       | 주문 경과 시간 조회       | USER  |
|              | /auth/order/cancel/{orderId}                                               | `PATCH`                     | 주문 취소             | USER  |
|              | /admin/order/status/{orderId}                                              | `PATCH`                     | 주문 상태 변경          | ADMIN |
|              | /admin/order/cooking-time/{orderId}                                        | `PATCH`                     | 예상 조리 시간 선택       | ADMIN |
|              | /admin/order/receipt-status/{orderId}                                      | `PATCH`                     | 주문 수락 또는 거절       | ADMIN |
| **Cart**     | /auth/cart                                                                 | `GET`                       | 장바구니 상품 목록 조회     | USER  |
|              | /auth/cart                                                                 | `POST` `DELETE`             | 장바구니 상품 수량 변경, 삭제 | USER  |
|              | /auth/cart/save                                                            | `POST`                      | 장바구니 상품 추가        | USER  |
|              | /auth/pay                                                                  | `PUT`                       | 장바구니 전체 결제        | USER  |
| **Review**   | /auth/review                                                               | `POST`                      | 리뷰 등록             | USER  |

<br/>




# 형상 관리

<table align="center"> <!-- 팀원 표 -->
  <tr>
   <th>
      [ Notion ]
      <br/>
      컨벤션, api 명세, 팀 규칙, 미팅 로그 등
      <br/>
      프로젝트 진행에 필요한 전반적인 사항 기록
   </th>
   <th>
      [ Jira ]
      <br/>
      개발을 진행하며 Sprint 단위로 계획한 사항의
      <br/>
      담당자, 마감일, 진행 상황, 세부 사항 기록
   </th>
   </tr>
  <tr>
   <td align="left" width="500px" class="Notion">
      <img src="https://github.com/YesunPark/cafe-bom/assets/108933466/349bfa12-b79b-47f0-88fa-d5b5e87b9133"/>
      <img src="https://github.com/YesunPark/cafe-bom/assets/108933466/cd231c74-2f02-4450-938d-5e413fa335cc"/>
   </td>
   <td align="left" width="500px" class="Jira">
      <img src="https://github.com/YesunPark/cafe-bom/assets/108933466/63d9e02e-49f4-458a-992f-46f490d1da30"/>
  </tr>
</table>

<br/>



# 팀 문화

1. 상호 존중과 화합
    - 질문할 때나 리뷰할 때나 미팅할 때나 늘 상호존중! 즐거운 협업이 될 수 있도록 노력합니다.
2. 바쁜 하루를 마무리하는 미팅
    - ❤️‍ **Daily Scrum** 🔥
        - 주중 저녁, 매일 서로 어떤 하루를 보냈는지를 알기 위해 데일리 스크럼을 진행합니다.
        - 무엇을 하고 있었는지, 무엇을 새로 시작했는지, 앞으로 무엇을 할 것인지, 그리고 그 날의 Trouble Shooting과 PR 확인 여부 등을 공유합니다.
        - 논의 사항이 있다면 **Notion에 미리 기록**해 Daily Scrum에서 논의합니다.
3. 한 주를 마무리하는 미팅
    - ✈️ **Sprint Plan** ✈️
        - 일주일을 시작하는 월요일에 한 주 동안 어떤 일을 할지 계획을 세웁니다.
    - 🏆 **Sprint Result** 🏆
        - 지난 Sprint Plan에서 세웠던 계획을 얼마나 이행했는지에 대해 돌아봅니다.
    - 📬 **Sprint Review** 📬
        - 전체적으로 어떠한 한 주를 보냈는지에 대해 업무적으로 좋았던 부분/아쉬웠던 부분, 개인적으로 좋았던 부분/아쉬웠던 부분에 대해 작성하고 발표합니다.
4. 즐거운 개발 시간, 🕜 **Core Time** 🕜
    - 협업 효율을 올리기 위해 주중에 다 같이 개발하는 시간인 코어 타임을 가집니다.
    - 주중 오후 **2 ~ 9시**는 필수, 주말은 자유입니다.
5. 만남의 광장 **GatherTown**
    - GatherTown에서 회의를 진행하고, 대화할 일이 있다면 모여서 토의합니다.
6. 내가 아는 건 모두가!
    - Notion을 통해 프로젝트 전반의 결정 사항과 회의 내용, 회의 결과, 컨벤션, ERD 등을 기록합니다.
    - Slack을 통해 전달할 사항이나 개발 외적인 논의사항 그리고 Jira, GitHub 과의 프로젝트 PR 연동을 통해 즉각적인 피드백이 이루어질 수 있도록 합니다.
    - Jira를 통해 서로의 작업 계획, 진척도, 현재 어떠한 작업을 하고 있는지에 대해 파악할 수 있도록 합니다.
7. 모두의 Pull Request
    - **3인 이상의 팀원들**에게 Approve를 받아야 PR을 Merge할 수 있습니다.