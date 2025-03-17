# 비행기 타고 가조
- Team Notion : https://www.notion.so/teamsparta/1962dc3ef51480dfa4e6e36afa0f2e16?pvs=4
- 프로젝트 기간 : 2025년 2월 10일 → 2025년 3월 17일

![image](https://github.com/user-attachments/assets/669d610c-4c26-4c8a-83ca-85e0cddd392c)

----
# 🏬 프로젝트 소개

(gif)

<aside>

> ✈️ **비행기 타고 가조**는
사용자들이 손쉽게 항공권을 검색하고, 간편 로그인 및 간편 결제 시스템을 통해 쉽고 빠르게 항공권을 예매할 수 있도록 지원하는 웹 서비스입니다.
> 

약 1,000만건의 항공 스케줄 데이터를 이용하여, 동시 다발적인 대량의 트래픽에도 안정적으로 대응할 수 있는 웹 서비스를 만들고자 하였습니다.

웹 서비스로서의 기능을 구현하며 단일된 테스트 환경 구성, 대용량 데이터 처리와 쿼리 최적화를 통한 성능 개선 등 프로젝트를 통해 개발 역량을 한층 더 끌어올리는 데에 집중하고자 하였습니다.

</aside>

----
# ⚙️ 서비스 구성도

### ERD
![image](https://github.com/user-attachments/assets/53b4351c-265f-4689-8c98-8e887bb55b3b)

### 인프라 구성도
![image](https://github.com/user-attachments/assets/683c284c-e1d6-474f-985a-77730eb8bbee)

### API 명세
| Method | URL                                    | 설명                 | HttpStatus     | Role       | HttpHeader               |
|--------|----------------------------------------|--------------------------------|----------------|------------|--------------------------|
| POST   | /oauth2/authorization/google           | 회원가입 / 로그인            | 200            | CUSTOMER   | -                        |
| GET    | /admin/users                           | 유저 다건 조회        | 200            | ADMIN      | {"Authorization":"token"}|
| GET    | /users/me                              | 유저 단건 조회        | 200            | CUSTOMER   | {"Authorization":"token"}|
| PATCH  | /user/me                               | 유저 수정             | 200            | CUSTOMER   | {"Authorization":"token"}|
| DELETE | /user/me                               | 회원 탈퇴             | 204            | CUSTOMER   | {"Authorization":"token"}|
| POST   | /admin/airplanes                       | 항공기 생성           | 201            | ADMIN      | {"Authorization":"token"}|
| GET    | /admin/airplanes                       | 항공기 다건 조회      | 200            | ADMIN      | {"Authorization":"token"}|
| POST   | /admin/airplanes/{airplaneId}/flight-plans | 항공 시간표 생성      | 201            | ADMIN      | {"Authorization":"token"}|
| GET    | /flight-plans                          | 항공 시간표 검색      | 200            | CUSTOMER   | {"Authorization":"token"}|
| POST   | /orders                                | 주문 생성             | 201            | CUSTOMER   | {"Authorization":"token"}|
| GET    | /orders                                | 주문 다건 조회        | 200            | CUSTOMER   | {"Authorization":"token"}|
| PUT    | /orders/{order_id}                     | 주문 정보 변경        | 200            | ADMIN      | {"Authorization":"token"}|
| POST   | /discounts/admin                       | 할인 이벤트 생성      | 201            | ADMIN      | {"Authorization":"token"}|
| PATCH  | /discounts/admin/{discount_id}         | 할인 기간 수정        | 200            | ADMIN      | {"Authorization":"token"}|
| GET    | /discounts                             | 할인 이벤트 목록      | 200            | CUSTOMER   | {"Authorization":"token"}|
| GET    | /discounts/grade                       | 멤버십 할인 조회      | 200            | CUSTOMER   | {"Authorization":"token"}|

### API 명세 상세보기
https://www.notion.so/teamsparta/API-1b62dc3ef51480a48230f2ebb7b4c74d?pvs=4

----
# 🖥️ 주요 기능

- 간편 로그인 (OAuth 2.0)
    - 구글의 소셜 로그인으로 간편하게 회원가입 및 로그인
- 항공권 검색 및 예매
    - 항공권 제공을 위해 1,000만건의 데이터를 삽입해두었습니다.
    - 각 회원은 출발지와 도착지, 비행출발시간을 조건으로 비행스케줄을 검색합니다.
    - 특정 항공권에 트래픽이 몰려도 안전하게 예매가 진행됩니다.
- 회원 등급 및 이벤트 기반 할인
    - 각 비행스케줄 결제에 앞서, 예매를 진행하는 회원의 등급과 적용되는 이벤트를 확인합니다.
    - 적용되는 할인 정보에 맞춰 결제 금액의 할인을 적용하고 결제를 진행합니다.
- 간편 결제 시스템
    - Toss Payments PG를 이용하여 사용자들이 간편하고 빠르게 항공 티켓을 예매할 수 있게 결제 위젯을 제공합니다.
    - 다양한 예외 상황에 안전하게 결제가 진행될 수 있도록 예상시나리오를 작성하여 처리하였습니다.

----
# ⌨️ 사용 기술

### 🔹 Backend                    
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 


### 🛠️ Infra     

### ⛑️ Test Tool 

----

# 🧭 기술적 의사결정

<details>
  <summary>배포는 어떤 기술스택을 사용하는게 좋을까?</summary>

  # 배경

5주라는 기간안에 서비스 구현 및 배포까지 모두 완료하여야 했습니다.

기간안에 모두 구현 및 배포가 가능하면서, 필요한 기능이 모두 있는 기술스택을 고민하였습니다.

# 선택지

## GCP

빠른 배포속도와 비용면에서 가장 효율적이라 판단되었습니다.
관리 또한 간편한 편이라 현재 프로젝트에 가장 알맞는 플랫폼이 아닐까 생각됩니다.
하지만 해당 기술스펙을 지닌 팀원이 없어, 학습시간이 어느정도 소요됩니다.

## Azure

Microsoft 의 제품들과의 연동이 좋고, 보안과 인증체계에서 강점을 보입니다.
하지만 해당 장점이 현재 프로젝트에 어울리는 장점인지는 고찰이 필요합니다.

## AWS

AutoScaling 및 LB 가 자동화되어 있고, 배포속도 면에서는 가장 빠르게 가능합니다.
하지만 상세한 설정은 다른 플랫폼에 비해 더 복잡한 면이 있습니다.

# 결론

알아본 선택지 3가지 가운데 AWS Beanstalk 를 선택하였습니다.

AWS 가 가지는 압도적인 이점으로는, 바로 팀원들의 공통 기술스펙이 AWS 였기 때문입니다.

GCP, Azure 역시 저희가 원하는 배포 후 설정을 통하여 AutoScaling, 무중단배포 등이 모두 가능하였지만, AWS 는 팀원 모두가 공유하는 기술이었기에 작업시간이 다른 플랫폼에 비하여 압도적으로 빠를 것이라 생각하였습니다.

또한 문서와 자료가 많아 다른 플랫폼보다 필요한 학습시간도 매우 짧을 것이라 생각하였습니다.

→ 저희 프로젝트는 AWS Beanstalk 를 사용하기로 선택하였습니다.

- Elastic Beanstalk를 선택한 이유
    
    ### 배경
    
    배포 및 관리를 자동화하여 빠르고 안정적인 운영이 필요합니다.
    
    ### 선택지
    
    1. **Elastic Beanstalk**
    2. Kubernetes (EKS, GKE, AKS)
    3. Docker Swarm
    
    ### 비교 분석
    
    - **Elastic Beanstalk**: AWS에서 제공하는 PaaS 솔루션으로, 배포 자동화 및 로드밸런서 연동이 용이함. 코드 변경 시 빠른 롤백과 확장이 가능함.
        - Auto Scaling 연동이 쉽고 다른 부과적인 기능을 확장하기에 용이함
    - **Kubernetes**: 확장성이 뛰어나지만, 운영 및 유지보수가 복잡하며 직접 관리해야 할 요소가 많음.
    - **Docker Swarm**: 경량 컨테이너 오케스트레이션이 가능하지만, AWS 환경과의 연계성이 Kubernetes에 비해 부족함.
    
    ### 결론
    
    Elastic Beanstalk는 AWS 환경에서 EC2, S3, 로드밸런서와 원활하게 통합되어 배포와 운영을 간소화할 수 있어 최적의 선택이었습니다.

</details>

<details>
  <summary>CI/CD는 어떤 기술스택을 사용하는게 좋을까?</summary>

  # 배경

배포와 마찬가지로, 5주라는 기간안에 서비스 구현 및 배포까지 모두 완료하여야 했습니다.

기간안에 CI/CD 설정이 가능하면서, 필요한 기능이 모두 있는 기술스택을 고민하였습니다.

# 선택지

## Github Action

현재 배포 플랫폼이 AWS Beanstalk 와 공식/비공식 액션이 매우 풍부하며 배포가 편리하다는 장점이 있습니다. 비용 또한 무료에 가까우며 사용이 간편합니다.

하지만 Yaml 파일의 설정 난이도가 있는 편입니다.

## Jenkins

확장성과 커스터마이징이 선택지들 중 가장 뛰어납니다. 플러그인을 통하여 EB 와의 연동도 쉬운편입니다. 문서와 자료 역시 매우 많고 비용 역시 무료입니다.

하지만 설치와 관리에 대한 학습시간이 소모됩니다.

## CodePipeline

AWS 자체 제공하는 기능으로 EB 와의 통합이 매우 쉽고, AWS 하나로 배포와 CI/CD 를 모두 관리할 수 있다는 장점이 있습니다.

하지만 이 역시 학습곡선이 필요하며, 다른 선택지들에 비해 유독 학습시간이 많이 필요할 것으로 생각됩니다.

# 결론

현재 프로젝트 상황에서는 Github Action 이 가장 합리적인 선택으로 보입니다.

yaml 파일 작성에 있어서 학습시간이 소모됩니다만, 이 역시 가장 적은 학습시간이 소모될 것으로 판단됩니다.

또한 yaml 파일 하나만으로 자동배포까지 EB 에서 관리하기 때문에, 가장 빠른 작업속도를 보일 것으로 예측됩니다.

</details>

<details>
  <summary>DB는 어떤 DB를 사용하는게 좋을까?</summary>

  # 배경

비행기 티켓 예매 라는 성격상, 결제는 필수 불가결한 부분입니다.

이는 곧 데이터의 일관성과 정합성, 안정성이 매우 중요하다는 말과 같습니다.

거기에 더해 빠른 작업속도와 낮은 학습곡선이 필요한 상황입니다.

# 선택지

## PostgreSQL

JSONB 데이터 지원, MVCC 및 복잡한 쿼리 최적화까지 모두 가능하다는 장점이 있습니다.
JPA 와의 호환도 좋으며 비용 역시 합리적인 수준이라 생각됩니다.

하지만 기존 프로젝트 및 팀의 경험이 적어 유지보수가 어려울 수 있기에 그에 따른 학습시간 소요는 필요할 것입니다.

## MySQL

JPA 와 완벽히 호환되며 작업속도가 가장 빠릅니다. 또한 문서와 자료 또한 많아 서비스 구현 작업에 있어서는 가장 뛰어날 것으로 예측됩니다.]

하지만 DB 자체에서 List 형 자료구조를 지양한다는 점에서 해당 프로젝트의 각 테이블들이 어떠한 형태일지 비교가 필요해 보입니다.

## MongoDB

NoSQL 을 사용해야 한다면, 가장 자료가 많고 작업이 빠른 MongoDB 가 후보로서 유력하다고 생각합니다.

하지만 현재 프로젝트가 NoSQL 이 어울릴지 고민해보아야 할 것입니다.

# 결론

해당 프로젝트에 가장 어울리는 DB 는 PostgreSQL 이라 판단하였습니다만, 결론적으로 선택한 DB 는 MySQL 입니다.

프로젝트의 각 테이블들과의 연관관계와 구조를 생각한다면 PostgreSQL 이 맞다고 생각합니다만, 현재 팀원들의 공유 기술스펙과 그에 따른 학습시간, 작업속도를 생각한다면 MySQL 이 가장 합리적이라 판단하였습니다.

</details>

<details>
  <summary>외부 결제 시스템은 무엇을 도입해야 할까?</summary>

  # 배경

현재 프로젝트에서 사용자가 항공권 예매 후 결제를 빠르고 안전하게 진행할 수 있도록 결제 시스템을 도입해야 했습니다.

---

# 선택지

| 기술 선택 | 장점 | 단점 |
| --- | --- | --- |
| 토스 페이먼츠 위젯 | UI 개발 불필요, 다양한 결제 수단 지원, PG 연동 간소화 | 커스텀 UI 제어의 어려움과 테스트시 제약사항이 많음. |
| 토스 일반 결제 API 연동 | 커스텀 UI 가능, PG 선택 자유로움 | 직접 연동해야 함, 개발 복잡도 증가 |
| 타사 PG | 없음. | 개발 문서가 토스에 비해 불친절함. |

왜 토스 페이먼츠 위젯을 선택했나?

- **PG 연동 부담 감소** - 위젯이 결제 수단별로 로직을 자동으로 처리해 주기 때문에 구현 부담이 줄어들었습니다.
- **다양한 결제 수단 지원** - 신용카드, 카카오페이 등 여러 결제 수단을 쉽게 추가가 가능합니다.
- 타사에 비해 많은 정보와 친절한 개발 문서를 제공합니다.

→ 위와 같은 이유로 일반 API 연동, 타사 PG 대신 토스 페이먼츠 위젯을 활용하는 것이 적절하다고 판단했습니다.

---

# 결론 및 회고

위젯을 통해 결제 연동이 빠르고 좋았지만 결제 시스템을 처음 구축하다 보니 생소하고 익숙하지 않아 결제 연동에 예상보다 많은 시간을 투자해야 했습니다.

이번 경험을 통해 결제 시스템의 흐름을 어느정도 이해했기 때문에 다음에는 일반 결제 API를 도전해보고 싶습니다.
현재 테스트 환경에서 위젯으로 결제를 진행하면 WebHook, 결제 완료/취소 시 이메일 발송, API 로그 등을 사용할 수 없는 제약이 있었습니다.

이러한 제약으로 다음에는 직접 API를 연동하여 더 세밀하게 결제 로직을 컨트롤해보고 싶은 생각을 했습니다.

</details>

<details>
  <summary>결제 시나리오에는 어떤 것들이 있을까?</summary>

  <details>
    <summary>결제 시나리오 1. 결제 재시도</summary>
    # 배경

결제 파트를 맡으면서 단순히 결제 연동을 구현하는 것보다 사용자의 편의성과 안정성을 높이는 것이 더 중요한 문제라는 것을 인지했다.

결제는 돈과 직결되기 때문에 실패 시 적절한 대응이 필요했고 그래서 생각한 방안은 결제 시나리오 중 하나인 결제 재시도 구현이었습니다.

![image](https://github.com/user-attachments/assets/0dc12962-5096-480d-ac04-b5ae03e23464)


결제 과정에서는 네트워크 장애, 일시적인 PG사 장애, API 연결 TimeOut 등 다양한 이유로 결제가 실패할 수 있는데 이때는 무조건 실패처리 하는 것이 아니라 재시도를 통해 결제가 성공할 수 있도록 보장하는 것이 사용자 경험 개선의 핵심 요소라고 생각했습니다.

하지만 **모든 실패 상황에서 무조건 재시도를 하면 불필요한 리소스 낭비와 서버 부하가 발생할 수 있다고 판단 하여 재시도가 필요한 경우와 불가능한 경우를 구분**하여 적절한 조치를 취했습니다.

---

# 선택지

| 기능 | 선택한 기술 | 고려한 대안 |
| --- | --- | --- |
| 결제 실패 재시도 | @Retryable | Scheduler 활용, 직접 재시도 로직 구현 |

왜 @Retryable을 선택했나?

- @Scheduler는 실시간 응답이 어렵고, 직접 재시도 로직을 구현하는 것은 코드가 복잡해지고 유지보수가 어려움이 있습니다.
- @Retryable은 간단한 설정만으로도 재시도 로직을 적용할 수 있고, 트랜잭션과의 연계도 가능하기 때문에 가장 적합하다고 판단했습니다.

---

# 구현 기능 및 주요 로직

- 결제 승인 또는 취소 요청이 실패할 경우, 일정 횟수까지 자동으로 재시도
- 불필요한 재시도를 방지하기 위해서 재시도가 필요한 오류와 불가능한 오류를 구분하여 적용

```java
@Retryable(
         maxAttempts = 3,
         backoff = @Backoff(delay = 2000, multiplier = 2),
         retryFor = { PaymentException.class, CustomException.class },
         noRetryFor = ClientPaymentException.class
)
public JsonNode approvePayment(String paymentKey, String orderId, int amount) throws Exception {
     String url = "https://api.tosspayments.com/v1/payments/confirm";
     JSONObject jsonObject = createApprovalRequestJson(paymentKey, orderId, amount);

     try {
         HttpResponse<String> response = executePost(url, jsonObject.toString());
         log.info("Payment executed successfully: {}", response.body());

         JsonNode responseJson = objectMapper.readTree(response.body());
         handlingPaymentError(responseJson);
         return responseJson;
     } catch (HttpStatusCodeException | ResourceAccessException e) {
         throw new CustomException(ServerErrorResponseCode.NETWORK_ERROR);
     } catch (Exception e) {
         log.error("Payment failed: {}\n Error Trace: {}", e.getMessage(), e.getStackTrace());
         throw e;
     }
 }
```

```java
private void handlingPaymentError(JsonNode responseJson){
        String errorCodeString = "";
        String errorMessage = "";
        if(responseJson.get("code") != null) {
            errorCodeString = responseJson.get("code").asText();
            errorMessage = responseJson.get("message").asText();
        }

        Optional<ClientPaymentErrorResponseCode> clientEx = ClientPaymentErrorResponseCode.has(errorCodeString);
        if (!errorCodeString.isBlank()) {
            if (clientEx.isPresent()) {
                throw new ClientPaymentException(clientEx.get());
            } else {
                PaymentErrorResponseCode paymentFailed = PaymentErrorResponseCode.setPaymentFailedMessage(errorMessage);
                throw new PaymentException(paymentFailed);
            }
        }
    }
```

### **1. @Retryable 주요 설정**

- **maxAttempts = 3** → 최대 3번까지 재시도합니다.
- **backoff = @Backoff(delay = 2000, multiplier = 2)** →
    - 처음 재시도는 2초 후에 실행됩니다.
    - 두 번째 재시도는 4초(2초 × 2), 세 번째 재시도는 8초(4초 × 2) 후에 실행됩니다.
    - 즉 점점 증가하는 방식(지수 백오프)으로 재시도 간격을 늘려 **서버 부하를 줄이도록 설계**되었습니다.
- **retryFor = { PaymentException.class, CustomException.class }** →
    - **PaymentException** 또는 **CustomException**이 발생하면 재시도합니다.
    - 예를 들어 TossPayments에서 보내주는 **`"일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."`** 또는 **`서비스에서 발생하는 서버 오류 같은 경우`** 는 재시도하면 성공할 가능성이 있기 때문입니다.
- **noRetryFor = ClientPaymentException.class** →
    - ClientPaymentException이 발생하면 재시도하지 않습니다.
    - 예를 들어 **“잔액 부족”**, **“유효하지 않은 카드 정보”** 같은 문제는 재시도해도 성공할 가능성이 없기 때문입니다.

### 2. approvePayment() 주요 동작

- 최종 승인 요청
    - executePost(url, jsonObject.toSting())를 통해 TossPayments 측에 최종 승인 요청을 보냅니다.
    - 만약 TossPayments 측에서 오류를 보낼 시 Json 형태로 Message와 Code를 보내줍니다.
- 오류 처리
    - 응답 데이터를 JsonNode로 변환 후, handlingPaymentError()를 호출하여 결제 오류  여부를 판단합니다.
- 예외 처리
    - HttpStatusCodeException, ResourceAccessException 발생 시 CustomException을 던져서 재시도를 유도합니다.
    - 기타 예외 발생 시 로그를 기록하고 예외를 다시 던집니다.

### 3. handlingPaymentError 주요 동작

- 응답 JSON에서 오류 코드 및 메시지 추출
    - 응답 데이터에 `code` 와 `message` 필드가 있으면 가져옵니다.
- 클라이언트 오류 여부 확인
    - ClientPaymentErrorResponseCode.has(errorCodeString)을 호출하여 **클라이언트 오류인지 확인합니다.**
- 클라이언트 오류면 ClientPaymentException 발생
    - 이 예외는 notryFor에 지정되어 있어 **재시도를 하지 않습니다.**
- 서버 오류면 PaymentException 발생
    - PaymentException은 retryFor에 포함되어 있어 **자동으로 재시도됩니다.**

---

# 결론 및 회고

이번 결제 재시도 기능을 구현하면서 **결제 과정의 신뢰성과 사용자 경험을 향상**시키는 것이 핵심 목표였습니다.
단순히 결제 API를 호출하는 것이 아니라, **실패 시 재시도를 통해 결제가 성공할 가능성을 높이고**,
**불필요한 재시도를 방지하여 서버 부하를 최소화하는 전략을 적용**했습니다.

Spring의 @Retryable을 활용함으로써 재시도 로직을 간결하게 구현할 수 있었고, 재시도가 필요한 오류와 불가능한 오류를 구분하여 효율저인 결제 실패 대응이 가능하도록 만들었습니다.
  </details>
  
  <details>
    <summary>결제 시나리오 2. 중복 결제 방지</summary>
    # 배경

중복 결제에 대해 고민을 하게된 이유는 처음 결제 시스템을 개발할 때 중복 결제가 실제로 발생할 가능성이 있을까? 라는 질문에서 시작했습니다.
아직 서비스가 운영되지 않았기 때문에 실제 사례는 없었지만 한 번이라도 중복 결제가 발생하면 사용자 신뢰도에 치명적인 영향을 미칠 수 있다 생각했습니다.

결제는 단순한 API 호출이 아니라 사용자의 돈이 직접적으로 움직이는 중요한 프로세스 입니다. 따라서 이런 문제가 발생할 수도 있다는 가정만으로도 사전 대비가 필요했다고 판단했습니다.

특히 토스페이먼츠를 연동하는 과정에서 짧은 시간 안에 동일한 결제 요청이 여러 번 들어올 경우 어떻게 처리될까? 라는 의문이 들었습니다.

- 사용자가 실수로 결제 버튼을 여러 번 클릭하면?
- 네트워크 지연으로 인해 서버가 응답을 받지 못하고 동일 요청을 재전송하면?

이런 상황에서 토스페이먼츠의 PaymentKey 중복 방어 기능이 충분한가? 아니면 추가적인 대비책이 필요할까?
추가적인 대비책으로는 멱등키 를결제 승인 요청에도 적용해야 할까? 라는 고민에서부터 이번 의사 결정이 시작되었습니다.

---

# 선택지

1. **PaymentKey를 활용한 중복 방지**
    - 토스 페이먼츠의 PaymentKey는 결제 인증 단계에서 중복 요청을 방지하는 기능을 기본적으로 제공합니다.
    - 동일한 PaymentKey로 승인 요청이 들어오면, PG에서 자체적으로 방어하여 이미 승인된 결제라고 응답이 옵니다.
2. **클라이언트 레벨에서 중복 요청 방지**
    - 결제 버튼을 여러 번 클릭하는 경우를 방지하기 위해, 결제 버튼을 비활성화하는 등의 UX 개선
    - 네트워크 장애로 인해 동일 요청이 재전송될 경우를 대비해서 프론트엔드에서 일정 시간 동안 중복 요청을 차단하는 로직도 필요합니다.
3. 멱등키 활용
    - 멱등키를 활용하면 같은 요청이 여러 번 들어와도 한 번만 처리됨을 보장할 수 있습니다.
    - 멱등키를 보관할 Redis 등이 필요.

현재 백엔드에서 서비스가 운영중이기 때문에 PaymentKey만을 활용하여 중복 요청을 방지하고 있는데 프론트엔드도 참여한다면 UX도 개선할 예정입니다.

그럼 멱등키는 사용해야할까? 

만약 멱등키를 도입하게 된다면

1. 결제 승인 요청마다 새로운 멱등키를 생성해야 합니다.
2. 멱등키를 DB나 Redis에 저장하고 유효기간을 설정해야합니다.
3. 중복 요청이 들어오면 이전에 사용된 멱등키를 조회하여 동일 요청인지 확인을 합니다.

하지만 고민이 있었습니다.

1. 이미 PaymentKey와 클라이언트에서 중복 방어 기능을 제공하고 있는데 추가적인 멱등키가 필요할까?
2. 멱등키 도입을 위해 Redis를 추가하는 것이 적절한가?
3. 삼중 보호 장치가 필요할 정도로 중보 결제 요청이 빈번한가?

⇒ 현재 구조에서는 토스페이먼츠 PG에서 제공하는 PaymentKey 자체가 멱등성을 보장해줍니다.
동일한 PaymentKey로 승인 요청이 들어오면 페이먼츠 측에서 이를 감지하여 중복처리를 막아줍니다.
따라서 결제 승인 단계에서는 추가적인 멱등키 없이도 충분히 중복 결제를 방지할 수 있습니다. 
추가로 클라이언트에서도 결제 버튼을 한 번 누르면 즉시 비활성화 시키는 것을 추가하면 사용자의 실수로 
인한 중복 요청을 차단합니다.

이러한 방어 로직이 이미 2개나 존재하므로 멱등키를 추가로 도입하는 것은 불필요한 복잡성을 초래로 오히려 **오버 엔지니어링**이 될 가능성이 크다고 판단했습니다.

---

# 결론 및 추후 고려 사항

**(1) 멀티 PG(Payment Gateway) 지원**

- 현재는 토스페이먼츠만 사용하지만, 향후 여러 PG를 도입할 경우 **다른 PG가 PaymentKey를 제공하지 않는다면 멱등키가 필요할 가능성이 있습니다**.

**(2) 부분 취소(Partial Refund) 도입 시 중복 요청 문제**

- 현재는 전체 취소만 지원하므로 PaymentKey만으로도 충분합니다.
- 하지만 **부분 취소**가 가능해지면, **클라이언트의 재시도 로직 때문에 중복 취소가 발생할 위험**이 있음.
- EX
    1. 사용자 결제 완료 (100,000원)
    2. 사용자 A 상품(30,000원) 부분 취소 요청
    3. PG에서 취소 성공 → 하지만 네트워크 오류로 클라이언트가 실패 응답을 받음
    4. 클라이언트에서 **재시도 로직 실행** → 다시 30,000원 취소 요청
    5. 결국 60,000원이 취소되는 문제 발생 
    - 따라서 **부분 취소를 지원할 경우, 반드시 멱등키를 도입하여 중복 요청을 방지해야 합니다**.
  </details>
  
  <details>
    <summary>결제 시나리오 3. 악의적 예약 선점</summary>
    # 배경

악의적인 사용자가 여러 좌석을 선점한 후 결제를 진행하지 않을 경우 다른 이용자가 좌석을 예약할 수 없는 심각한 문제가 발생할 수 있습니다. 이를 해결하기 위해 결제 대기 상태에서 일정 시간이 지나면 자동으로 예약이 취소되는 시스템을 구현해야 합니다.

# 선택지

### [RabbitMQ를 활용한 방법]

RabbitMQ는 메시지 브로커로 TTL과 DLQ(Dead Letter Queue) 기능을 활용하면 메시지를 특정 시간 동안 보관하고, 시간이 지나면 자동으로 만료되도록 설정할 수 있습니다. 

### 구현 방식

1. 사용자가 항공권을 예매하면 예약 메시지를 RabbitMQ의 큐에 저장하며 TTL을 설정하여 N분 후 자동 만료되도록 설정
2. TTL이 만료되면 메시지는 Dead Letter Queue로 이동
3. DLQ에서 메시지를 감지한 Consumer가 해당 예약을 취소하고 좌석을 해제

### 장점

**실시간 처리 가능** - 메시지가 자동으로 만료되므로 불필요한 DB조회를 줄일 수 있음

**확장성이 높음** - RabbitMQ는 분산 환경에서도 안정적으로 작동

**비동기 처리 가능** - 다수의 예약을 동시에 관리할 수 있음

### 단점

RabbitMQ 운영 부담 - 별도의 메시지 브로커 설치, 운영, 장애 대응이 필요하고 장애 발생 시 메시지가 지연되거나 유실될 가능성이 있습니다.
Ex. RabbitMQ 서버 다운, 메시지 유실

---

### [RedisMQ를 활용한 해결 방법]

Redis는 빠른 데이터 저장 및 조회가 가능하며 Key Expiry와 Keyspace Notification 기능을 활용하면 TTL 기반의 자동 만료 이벤트를 감지할 수 있습니다.

### 구현 방식

1. 사용자가 항공권을 예매하면 Redis에 예약 정보를 저장하며 TTL을 설정하여 자동 만료되도록 함
2. TTL이 만료되면 Redis Keyspace Notification을 통해 해당 만료 이벤트를 감지
3. 감지된 이벤트를 처리하여 예약을 취소하고 좌석을 해제

### 장점

**간단한 구현** - 추가적인 메시지 브로커 없이 Redis만으로 해결 가능

**TTL을 활용한 자연스러운 만료** - 일정 시간이 지나면 자동으로 만료되어 데이터 정리 부담이 줄어듦

### 단점

이벤트 유실 가능성 - Redis Keyspace Notification이 활성화되지 않거나 장애가 발생 시 이벤트 감지가 되지 않을 수 있음.
EX. Redis 서버 다운

---

### [Spring Scheduler를 활용한 해결 방법]

Spring의 @Scheduler 기능을 활용하면 일정 주기로 DB를 조회하며 미결제된 예약을 취소하는 방식을 구현할 수 있습니다.

### 구현 방식

1. 일정 주기마다 예약 테이블을 조회하여 N분 이상 결제가 진행되지 않은 예약을 찾음
2. 해당 예약들을 자동으로 취소하고 좌석을 해제

### 장점

**구현이 간단함** - 추가적인 메시지 브로커 없이 Spring만으로 가능

유지보수가 용이 - 예약 테이블에서 직접 상태를 변경할 수 있어 로직이 직관적

### 단점

대규모 트래픽에서 성능 이슈 - 예약 건수가 많아지면 주기적인 DB 조회 부담 증가

**정확한 시간 제어 어려움** - 1분 이상으로 실행되면 실시간성이 떨어지고 1분 이하로 설정한다면 DB 조회에 부담이 더 극심해짐

### [의사결정]

현재 프로젝트에서는 Spring의 Scheduler를 선택했습니다. 우리 시스템에서는 결제 대기 시간이 초과된 예약을 자동으로 취소하는 것이 주요 요구사항이며, 실시간성이 중요한 요구사항이 아닙니다.

RabbitMQ나 RedisMQ는 실시간 이벤트 기반으로 동작하는 장점이 있지만, 운영 부담이 크고 추가적인 학습이 필요합니다. 특히 RabbitMQ는 메시지 브로커 운영 및 장애 대응이 필요하며, RedisMQ는 Keyspace Notification 설정이 필요하고 이벤트 유실 가능성이 존재합니다.

따라서 현재 프로젝트에서는 가장 간단하면서도 안정적인 Spring Scheduler를 사용하여 일정 주기로 DB를 조회하는 방식이 최적이라는 결론을 내렸습니다.

### [구현 기능]

- 10분 이상 결제 대기 상태(IN_PROGRESS)인 예약을 자동으로 취소
- 좌석을 다시 예매 가능 상태로 변경

# 결론

현재 프로젝트에서는 **결제 대기 시간이 초과된 예약을 자동으로 취소하는 기능**이 필요하지만, **실시간성이 필수적인 요구사항이 아니기 때문에** 가장 간단하고 유지보수가 쉬운 **Spring Scheduler 기반 방식**을 채택했습니다.

RabbitMQ와 RedisMQ는 **실시간 이벤트 처리**에 유리하지만, 운영 및 설정 부담이 크고, 장애 발생 시 복구가 복잡할 수 있습니다. 반면, Spring Scheduler는 추가적인 메시지 브로커 없이도 **예약 상태를 직접 조회하고 처리할 수 있어 안정적이며 유지보수도 용이**합니다.

현재 **10분 이상 결제 대기(IN_PROGRESS) 상태인 예약을 자동 취소**하는 로직을 도입하여 **좌석이 장시간 선점되는 문제를 방지**할 수 있도록 하였습니다.
  </details>

</details>

