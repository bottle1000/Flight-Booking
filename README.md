# 비행기 타고 가조
- Team Notion : https://www.notion.so/teamsparta/1962dc3ef51480dfa4e6e36afa0f2e16?pvs=4
- 프로젝트 기간 : 2025년 2월 10일 → 2025년 3월 17일

![image](https://github.com/user-attachments/assets/669d610c-4c26-4c8a-83ca-85e0cddd392c)

----
## 👥 팀원 소개

| 이름 | 직책 | 담당한 작업 |
|------|------|--------------------------------------|
| ✈️ **우혁규** | 팀장 **(설계 및 주문 담당)** | 인프라 설계 및 배포, DB 설계, 주문 CRUD 작성, 결제 시나리오 구상 |
| ✈️ **박병천** | 부팀장 **(결제 담당)** | 결제 시스템 구축, 결제 시나리오 구상 및 구현 |
| ✈️ **최순우** | 팀원 **(배포 및 모니터링 담당)** | 배포, 모니터링, OAuth 2.0, CI/CD |
| ✈️ **송준일** | 팀원 **(할인 담당)** | 할인 이벤트 CRUD 작성, 캐싱으로 DB 최적화 |
| ✈️ **김신우** | 팀원 **(항공 담당)** | 항공기, 항공 시간표 CRUD, DB 최적화 인덱싱, 부하 테스트 |

----
# 🏬 프로젝트 소개

![Image](https://github.com/user-attachments/assets/4b31c274-6765-4f31-8b18-c93729be6413)

<aside>

✈️ **비행기 타고 가조**는
사용자들이 손쉽게 항공권을 검색하고, 간편 로그인 및 간편 결제 시스템을 통해 쉽고 빠르게 항공권을 예매할 수 있도록 지원하는 웹 서비스입니다.

약 1,000만건의 항공 스케줄 데이터를 이용하여, 동시 다발적인 대량의 트래픽에도 안정적으로 대응할 수 있는 웹 서비스를 만들고자 하였습니다.

웹 서비스로서의 기능을 구현하며 단일된 테스트 환경 구성, 대용량 데이터 처리와 쿼리 최적화를 통한 성능 개선 등 프로젝트를 통해 개발 역량을 한층 더 끌어올리는 데에 집중하고자 하였습니다.

</aside>

----
# ⚙️ 서비스 구성도

### ERD
![image](https://github.com/user-attachments/assets/53b4351c-265f-4689-8c98-8e887bb55b3b)

### 인프라 구성도
![image](https://github.com/user-attachments/assets/683c284c-e1d6-474f-985a-77730eb8bbee)

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
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-0088CC?style=for-the-badge&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![OAuth2.0](https://img.shields.io/badge/OAuth2.0-3C3C3C?style=for-the-badge&logo=oauth&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)


### 🛠️ Infra     
![AWS Elastic Beanstalk](https://img.shields.io/badge/Elastic_Beanstalk-FF9900?style=for-the-badge&logo=amazon-aws&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)
![Elastic Load Balancer](https://img.shields.io/badge/Elastic_Load_Balancer-FF9900?style=for-the-badge&logo=amazon-aws&logoColor=white)
![Grafana](https://img.shields.io/badge/Grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white)
![AWS S3](https://img.shields.io/badge/AWS_S3-569A31?style=for-the-badge&logo=amazon-s3&logoColor=white)
![Prometheus](https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white)
![AWS RDS](https://img.shields.io/badge/AWS_RDS-527FFF?style=for-the-badge&logo=amazon-aws&logoColor=white)
![Loki](https://img.shields.io/badge/Loki-008000?style=for-the-badge&logo=grafana&logoColor=white)


### ⛑️ Test Tool 
![JMeter](https://img.shields.io/badge/JMeter-D22128?style=for-the-badge&logo=apache-jmeter&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![K6](https://img.shields.io/badge/K6-7D64FF?style=for-the-badge&logo=k6&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-00C853?style=for-the-badge&logo=mockito&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Testcontainers](https://img.shields.io/badge/Testcontainers-2D79C7?style=for-the-badge&logo=testcontainers&logoColor=white)

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

<details>
  <summary>모니터링은 어떤 기술을 사용하는게 좋을까?</summary>

  <details>
    <summary>Grafana를 선택한 이유</summary>
      
### 선택지
1. **Grafana**
    2. Kibana
    3. Datadog
    ### 비교 분석
    - **Grafana**: Prometheus와 같은 모니터링 시스템과 원활하게 연동되며, 오픈소스 기반으로 커스터마이징이 자유로움. 대시보드가 직관적이고 확장성이 뛰어남.
    - **Kibana**: Elastic Stack과 연동이 강점이지만, Prometheus 기반 메트릭 수집과의 통합이 상대적으로 어려움.
    - **Datadog**: 강력한 모니터링 기능을 제공하지만, 비용이 높고 커스텀 설정이 제한적임.
    ### 결론
    세 선택지 모두 Prometheus 및 Loki와 원활하게 연동 가능하고 대시보드가 강력하지만,
    **Grafana는 무료이며 사용이 쉽다는 점에서 선택하였습니다.**
    
  </details>

  <details>
    <summary>Loki를 선택한 이유</summary>

### 선택지
1. **Loki**
    2. ELK Stack (Elasticsearch, Logstash, Kibana)
    3. AWS CloudWatch Logs

    ### 비교 분석
    - **Loki**: Prometheus와 유사한 아키텍처로, 메트릭 기반의 로그 수집이 가능하며, Grafana와의 통합이 용이함. 경량 설계로 리소스 부담이 적음.
    - **ELK Stack**: 강력한 검색 및 분석 기능을 제공하지만, 리소스 소모가 크고 운영 비용이 높음.
    - **CloudWatch Logs**: AWS에 최적화되어 있지만, AWS 외의 인프라에서는 활용도가 낮으며, 비용이 증가할 가능성이 있음.

    ### 결론
    **Loki는 Grafana 와의 원활한 연동이 가능하며, 가벼운 로그 분석 환경을 제공하기에 가장 적합한 선택이었습니다.**
  </details>

  <details>
    <summary>Prometheus를 선택한 이유</summary>

### 선택지
1. **Prometheus**
2. InfluxDB
3. AWS CloudWatch Metrics

### 비교 분석
- **Prometheus**: 오픈소스이며, Kubernetes 및 컨테이너 환경과 원활하게 연동됨. 커뮤니티 지원이 활발하고, Grafana와의 통합이 강력함.
- **InfluxDB**: 시계열 데이터베이스로 강력한 분석 기능을 제공하지만, Prometheus에 비해 커뮤니티 및 확장성이 부족함.
- **CloudWatch Metrics**: AWS에 최적화되어 있지만, AWS 외의 인프라에서는 활용도가 제한적이며, 비용이 증가할 가능성이 있음.

### 결론
**확장성과 커뮤니티 지원이 뛰어나고 Grafana 와의 연동이 쉬우며, 비용적인 측면에서 무료라는 점에서 Prometheus를 선택하였습니다.**
  </details>

  <details>
    <summary>Spring Actuator를 선택한 이유</summary>

    
### 선택지
1. **Spring Actuator**
2. Micrometer
3. 직접 개발한 모니터링 API

### 비교 분석
- **Spring Actuator**: Spring Boot와 기본적으로 통합되어 있으며, 간단한 설정으로 다양한 메트릭을 제공. Prometheus 및 Grafana와 연동이 용이함.
- **Micrometer**: 다양한 모니터링 시스템과 연동 가능하지만, Spring Boot에 내장된 기능을 사용하지 않는다는 점에서 추가적인 설정이 필요함.
- **직접 개발한 API**: 커스터마이징이 가능하지만, 유지보수 비용이 높아지고, 표준적인 솔루션보다 신뢰성이 떨어질 가능성이 있음.

### 결론
**Spring Actuator는 Spring Boot 애플리케이션에 기본적으로 제공되며, Prometheus 및 Grafana와 쉽게 연동할 수 있기에 가장 적합한 선택이었습니다.**
  </details>

</details>

<details>
	<summary>어떠한 캐싱 전략을 도입해야할까?</summary>

	
 # 배경

성능 테스트를 위해 약 1000만건의 데이터를 DB에 저장한 후 테스트를 진행하였습니다.
그 결과 매우 저조한 성능(평균 검색시간 9.3초)을 보였습니다.

성능개선이 필수적이라 판단되어, 인덱싱을 적용하여 DB최적화를 하였지만 테스트 결과 사용자 수(Vuser)가 늘어남에 따라 다시 성능 저하가 발생하였습니다.

그렇기에 항공권 조회 API의 검색 성능 향상을 위한 캐싱 도입이 필요하다 판단하였습니다.

# 선택지

1. 캐싱 전략
    - Look Aside vs Read Through
        1. 쓰기가 없고 반복적인 읽기가 많아서 읽기 위주의 전략을 선택했음
        2. Look Aside는 스프링부트에서 제공하는 @Cacheable기능에서 자체적으로 지원하므로 편리하게 사용 가능
        3. Look Aside cache는 애플리케이션과 DB와 직렬로 연결되지 않고 독립적으로 존재하기에 장애가 발생하더라도 DB에 끼치는 영향이 적어 안정적이라고 생각됨
2. 로컬 캐시 vs 글로벌 캐시
    - 로컬 캐시
        - 각 서버의 로컬에서 캐시 저장소를 두어 데이터를 처리함
        - 데이터 처리속도가 빠름
        - 서버가 다운되면 데이터가 사라질 수 있음
        - 서버가 Scale-out되면 데이터 불일치 문제가 발생할 수 있다
    
    ### 글로벌 캐시
    
    - 캐시 저장소가 서버와 별도로 존재하고 각 서버에서 캐시 저장소에 접근하여 데이터를 처리함각 서버와 데이터 공
    - 캐시 저장소에 접근하는 네트워크 비용이 발생하고 이에 따라 로컬 캐시 대비 처리 속도가 느림
    - (Redis Cache) 확장성이 좋음
    - 현재 1000만건의 데이터를 조회하는데에 있어 로컬 캐시를 사용하게 된다면 Jvm 메모리의 한계, GC의 부담이 커질 것으로 예상됨
3. Redis vs Memcached
    - Redis
    - Memcached
    - 현재 프로젝트에서 이미 Redis를 사용중
    - 현재 사용중인 Spring 프레임워크에서 Redis를 추상화하여 사용할 수 있는 라이브러리 제공함
    - 다양한 자료구조를 지원하고 서버를 확장할 때에 유리하다고 판단함

# 결론

위와 같은 근거를 통해 Look Aside 전략과 Redis Cache를 현재의 프로젝트, 항공권 조회 API에 적용하기로 하였음
</details>

--------------


#  🖊️ 트러블 슈팅

  <details>
    <summary>Lock 구현 #1 - Persistence Context 문제</summary>
      
# 배경
비행기 티켓 예매를 진행함에 있어, 타임특가 세일등으로 같은 비행기 티켓에 다수의 사람이 몰릴 수 있습니다.

해당 시나리오에서 발생하는 오류를 막고자 비관적 Lock 을 도입하기로 하였고, 저희가 초기 Lock 을 구현한 코드는 다음과 같습니다.

```java
@Transactional
public void sellTicket(Long ticketId) {
		//...
		
		Ticket ticket = ticketRepository.findById(ticketId);
		if(ticket.getStatus() == Status.SOLD)
				return;
		
		//...
		
		lockService.withLock {
				Ticket currentTicket = ticketRepository.findById(ticketId);
				if(currentTicket.getStatus() == Status.SOLD)
						return;
				
				currentTicket.updateStatus(Status.SOLD);
				//...
		}
}

```

해당 코드 구조의 목적과 플로우는 다음과 같습니다.

![image](https://github.com/user-attachments/assets/4315af83-9596-4c9c-837f-a78cf96f2af7)


Lock 을 걸기 전과 후에 이중확인을 진행하여, 데이터의 정합성을 맞추고 불필요한 Lock 접근을 줄이려고 하였습니다.

하지만 해당 코드를 실행하였더니, 의도대로 진행되지 않고 Lock 이 제대로 실행되지 않고 있었습니다.

# 해결 과정

어째서 이런 문제가 발생하나 디버그하여 확인한 결과, Lock 점유 전후의 Entity 객체들의 HashCode 값이 같았습니다.

즉, Lock 점유 후에는 DB 에 쿼리를 날려 Entity 를 가져오는 것이 아니었습니다.

Lock 점유 전 객체를 기준으로 상태갱신 작업이 이루어지다보니, 의도된대로 상태가 갱신되고 있지 않고 있었으며 오히려 Dirty Read 문제가 발생하고 있었습니다.

이 문제가 일어나는 원인이 무엇인가 찾아본 결과,
원인은 Entity Manager 의 1차캐싱(Persistence Context)이 문제였습니다.

하나의 트랜잭션 안에서 같은 ID 를 이용한 DB 색인을 진행하면,
Entity Manager 가 같은 ID 색인에는 같은 객체를 반환하기 위해, 또한 DB Query 횟수의 최적화를 위해 그대로 사용했던 객체를 반환하는 것이, 해당 문제의 원인이었습니다.

# 결론

다음과 같이 NativeQuery 를 통하여 직접 DB 에서 Entity 를 조회하도록 코드를 수정하여,
Persistence Context 문제를 해결할 수 있었습니다.

```java
@Transactional
public void sellTicket(Long ticketId) {
		//...
		
		Ticket ticket = ticketRepository.findById(ticketId);
		if(ticket.getStatus() == Status.SOLD)
				return;
		
		//...
		
		lockService.withLock {
				//Ticket currentTicket = ticketRepository.findById(ticketId);
				Ticket currentTicket =
						ticketRepository.findOneByNativeQuery(ticketId);
						
				if(currentTicket.getStatus() == Status.SOLD)
						return;
				
				currentTicket.updateStatus(Status.SOLD);
				//...
		}
}
```
  </details>

  <details>
    <summary>Lock 구현 #2 - Dirty Read 문제</summary>

# 배경
EM 의 1차캐싱 문제를 해결하여도, 코드는 의도된대로 Lock 이 적용되지 않고 요청한 여러명의 사용자에게 동시에 티켓이 판매되는 문제가 발생하였습니다.

# 원인

어째서 Lock 이 예상한대로 진행되지 않는지, 다음과 같이 Log 를 작성하여 확인하였습니다.

```java
@Slf4j
@Transactional
public void sellTicket(Long ticketId) {
		//...
		Ticket ticket = ticketRepository.findById(ticketId);
		
		log.debug("BEFORE_LOCK=Ticket ID: {}, Ticket status: {}, Thread ID: {}",
			ticket.getId(), ticket.getStatus(), Thread.currentThread().getId())
			
		if(ticket.getStatus() == Status.SOLD)
				return;
		//...
		lockService.withLock {
				log.debug("Thread {} acquired lock", Thread.currentThread().getId());

				Ticket currentTicket =
						ticketRepository.findOneByNativeQuery(ticketId);

				log.debug("AFTER_LOCK=Ticket ID: {}, Ticket status: {}, Thread ID: {}",
						ticket.getId(), ticket.getStatus(), Thread.currentThread().getId())

				if(currentTicket.getStatus() == Status.SOLD)
						return;		
				currentTicket.updateStatus(Status.SOLD);
				//...

				log.debug("Thread {} release lock", Thread.currentThread().getId());
		}
}
```

로그를 확인한 결과 매우 이상한 결과가 나왔습니다.

Hibernate 의 SQL 로그와 Lock 로그가 다음과 같이 뒤죽박죽 섞여있는 것이었습니다.

![image](https://github.com/user-attachments/assets/31b31c3e-4fd0-41ff-b949-e8b75bfcee79)


@Transactional 의 범위가 Lock 범위보다 커지면서,
Transactional 의 쓰기지연이 Lock 보다 늦어지는 것이 원인이었습니다.

즉, DB 에 Query 를 날려 실제 DB 값이 갱신되는 것보다 다른 쓰레드에서 해당 Lock 을 점유하는것이 더 빨라서
Dirty Read 문제가 발생하고 있었습니다.

# 해결방법

그렇다면, Lock 의 범위가 Transactional 보다 넓어지면 해결할 수 있는 문제라고 판단하여 다음과 같이 코드를 수정하였습니다.

```java
public void sellTicket(Long ticketId) {
		//...
		
		Ticket ticket = ticketRepository.findById(ticketId);
		if(ticket.getStatus() == Status.SOLD)
				return;
		
		//...
		
		lockService.withLock {
				Ticket currentTicket =
						ticketRepository.findOneByNativeQuery(ticketId);
						
				if(currentTicket.getStatus() == Status.SOLD)
						return;
				
				//currentTicket.updateStatus(Status.SOLD);
				updateTicketStatusToSold(currentTicket);
				
				//...
		}
}

@Transactional
public void updateTicketStatusToSold(Ticket ticket) {
		ticket.updateStatus(Status.SOLD);
		ticketRepository.save(ticket);
}
```

# 결론

해당 방법으로 Dirty Read 문제는 해결할 수 있었습니다만, 이 방법은 다른 문제를 야기하였습니다.

같은 클래스의 메소드안에서 스스로의 메소드를 호출하는 것으로 인해, AOP 기반 Proxy 를 거치지 않아 실제로 트랜잭션이 생성되지 않는 문제입니다.
  </details>

  <details>
    <summary>Lock 구현 #3 - Self-Invocation 문제</summary>

# 배경
1,2 번 문제를 해결하며 나온 코드는 결국 Self-invocation 문제를 발생시키는 구조가 되었습니다.

이 문제를 해결하기 위해 다른 코드 구조를 생각하였습니다.

# 해결 과정

자기호출을 발생시키지 않기 위해, @Transactional 메소드만을 추출하여 다른 클래스로 분리를 시도하였습니다.

시도한 코드는 다음과 같습니다.

```java
public void sellTicket(Long ticketId) {
		//...
		
		Ticket ticket = ticketRepository.findById(ticketId);
		if(ticket.getStatus() == Status.SOLD)
				return;
		
		//...
		
		lockService.withLock {
				Ticket currentTicket =
						ticketRepository.findOneByNativeQuery(ticketId);
						
				if(currentTicket.getStatus() == Status.SOLD)
						return;
				
				//updateTicketStatusToSold(currentTicket);
				ticketTransactionalService.updateTicketStatusToSold(currentTicket);
				
				//...
		}
}

--- 별도의 파일

@Service
public class TicketTransactionalService {
		@Transactional
		public void updateTicketStatusToSold(Ticket ticket) {
				ticket.updateStatus(Status.SOLD);
				ticketRepository.save(ticket);
		}
}
```

# 결론

자기호출 문제는 이 방법으로 해결하였습니다만, 몇 가지 의문이 들었습니다.

메소드 단 하나만을 위하여 분리할 클래스와 그 Transactional method 가,
과연 내가 아닌 다른 작업자가 보아도 이해하기 쉽고 쓰기 쉬운가? 라는 의문이었습니다.

TransactionalService 클래스로 분리하였다면, Transactional 관련 메소드는 모두 분리하는 것이 맞는것은 아닌가? 라는 의문 역시 들었습니다.

하지만 두 의문에 대한 답은 모두 부정적이라 판단되었습니다.

가독성은 매우 떨어진다 생각되었고, 일일이 모든 transactional 메소드를 분리하는 것은 비효율적이고 불필요한 일이라 생각되었습니다.

→ 결국 다시한번 코드의 구조를 바꾸기로 결정하였습니다.
  </details>

  <details>
    <summary>Lock 구현 #4 - 코드 구조와 가독성에 대한 고찰</summary>

# 배경
현재 코드의 구조로는 가독성이 낮고 코드의 구조가 이해하기 힘듭니다.

이를 바꾸기 위해서는 코드의 구조를 바꿔야합니다.

# 해결과정

코드의 구조를 바꾸면서 충족해야하는 조건은 다음과 같습니다.

## Dirty Read 문제가 발생하지 않아야 한다.

→ Lock 범위가 Transactional 범위보다 넓어야 한다.

## 자기호출 문제가 발생하지 않아야 한다.

→ 같은 클래스 내부에서 Annotation 메소드를 호출하지 않아야 한다.

## 다른 작업자가 보더라도 바로 이해가 가능하여야 한다.

→ 클래스의 분리에 대한 목적이 명확하여야 한다.

이 세 가지를 모두 충족하는 방법이 무엇일까 고민한 결과,
현재 주요 문제부분인 Lock 과 Transaction 부분을 모두 분리하여 함께 관리하자 라는 답에 도달하였습니다.

Dirty Read 문제
→ Lock 과 Transactional 의 범위를, 따로 분리한 클래스자체에서 관리하는 방식이라면 다른 작업자가 작업을 진행하더라도 해당 부분에서 같은 문제를 겪을 가능성은 사라질 것입니다.

자기호출 문제
→ 애초에 분리한 클래스의 메소드안에서 Lock 과 Transactional 을 모두 관리한다면, 구태여 Lock 이나 Transactional 을 호출하지 않을테니 자기호출 문제는 발생하지 않을 것입니다.

가독성 문제
→ 단순히 Transactional 을 분리하는 것이 아니라, Lock 부분을 따로 관리하는 것으로 보여질 것입니다. 그렇다면 Lock 을 쓰지 않는 메소드에서는 Transactional 을 분리하지 않아도 될 것입니다.

그렇다면 어떻게 구현해야 Lock 과 Transactional 을 함께 관리할 수 있을까 고민하였습니다.

그 고민의 결과로 제가 생각한 것은,
AOP 방식으로, 재사용이 가능하면서 Transactional 보다 먼저 수행이 되도록 하는 것이었습니다.

- AOP 로 구현한 실제코드
    
    ```java
    @Slf4j
    @Aspect
    @Component
    @org.springframework.core.annotation.Order(Ordered.HIGHEST_PRECEDENCE)
    @RequiredArgsConstructor
    public class TicketLockAspect {
        private final LockRepository lockRepository;
    
        @Around("@annotation(TicketLock)")
        public Object applyLock(ProceedingJoinPoint joinPoint) throws Throwable {
            String prefix = "ticket_lock:";
    
            Object[] args = joinPoint.getArgs();
            Order order = Arrays.stream(args).filter(argument -> argument instanceof Order).findFirst()
                    .map(argument -> (Order) argument)
                    .orElseThrow(IllegalArgumentException::new);
    
            Set<String> locked = new HashSet<>();
            try {
                order.getTicketIds().forEach(ticketId -> {
                    String key = prefix + ticketId;
                    if (!lockRepository.lock(key, 0, 5)) {
                        throw new CustomException(ServerErrorResponseCode.LOCK_CONFLICT);
                    }
                    locked.add(key);
                });
                log.info("Ticket Lock for Order: {} & Lock: [{}]", order.getId(), locked);
    
                return joinPoint.proceed();
            } finally {
                locked.forEach(lockRepository::unlock);
                log.info("Ticket Unlock for [{}]", locked);
            }
        }
    }
    ```
    

# 결론

AOP 로 구현하여 분리한 예시코드는 다음과 같습니다.

```java
public void sellTicket(Long ticketId) {
		//...
		
		Ticket ticket = ticketRepository.findById(ticketId);
		if(ticket.getStatus() == Status.SOLD)
				return;
		
		//...
		
		// lockService.withLock {
		Ticket currentTicket =
				ticketRepository.findOneByNativeQuery(ticketId);
						
		if(currentTicket.getStatus() == Status.SOLD)
				return;
				
		lockedTicketService.updateTicketStatusToSold(currentTicket);
		// }
		//...
}

--- 별도의 파일

@Service
public class LockedTicketService {
		@Transactional
		@TicketLock
		public void updateTicketStatusToSold(Ticket ticket) {
				ticket.updateStatus(Status.SOLD);
				ticketRepository.save(ticket);
		}
}
```

AOP 를 이용하여 Lock 과 Transactional 의 순서와 범위를 강제하는것으로,
해당 문제를 해결하고 Lock 을 안전하게 적용시킬 수 있었습니다.
  </details>

  <details>
    <summary>부하 테스트 및 병목 분석</summary>
    https://www.notion.so/teamsparta/1b42dc3ef51480ec9d50edd917b6a984?pvs=4
  </details>

  <details>
    <summary>주문 취소 시 결제도 취소되는 기능</summary>
    
# 문제 상황
고객이 주문을 취소하면 환불을 진행하는 과정에서 트러블이 발생했습니다.

만약 고객이 주문을 취소하면 환불을 진행하는 과정이 **명백히 결제 도메인에서 일어나야하는 작업**이라 생가했습니다.

![image](https://github.com/user-attachments/assets/96c7b973-84c3-455d-97c3-292bdc20f83a)


하지만 위 그림과 같이 OrderService가 PaymentService를 직접 호출하면 단일 책임 원칙을 위반하고 서비스 간 강한 결합이 발생합니다.
즉 주문 도메인은 주문 취소만 담당해야지 결제 취소까지 직접 처리하면 도메인 특성에 맞지 않는 작업을 하는 것입니다.
또한, PaymentService에서 PG사 추가 또는 cancelPayment() 메서드의 파라미터가 변하게 되면 OrderService도 함께 수정해야 하므로 유지 보수성이 떨어집니다.

---

# 해결 과정

이를 해결하기 위해 Event 기술을 적용하여 **주문 도메인은 취소됨을 알리기**만 하고 결제 도메인은 **이를 감지**하여 취소룰 수행하도록 구현했습니다.

### 기술 선택지

| 기능 | 선택한 기술 | 대안 기술 |
| --- | --- | --- |
| 이벤트 기반 결제 취소 | @TransactionalEventListener | Kafka, RabbitMQ, @EventListener |

Spring의 **@TransactionalEventListener**을 선택한 이유

1. 현재 이벤트를 사용하는 곳은 단순한 주문 취소 이벤트 1개 뿐입니다.
2. 이를 위해 **Kafka**나 **RabbitMQ** 같은 외부 시스템을 도입하는 것은 오버 엔지니어링이라고 판단했습니다.
3. 결론적으로 Spring Event로 충분히 해결 가능하다 생각하고 향후 시스템이 확장되거나 MS 아키텍처로 구조가 변경 되었을 때 이벤트 전파가 필요할 경우 대안 기술을 도입할 생각입니다.
4. 또 **@EventListener**가 아닌 **@TransactionalEventListener**를 선택한 이유는 주문 취소 데이터베이스에 반영된 후에 결제 취소 이벤트가 발생하도록 하기 위함입니다.
    - 만약 @EventListener를 사용하면 이벤트가 발생하여 주문 취소가 실패하면 트랜잭션에 의해 롤백이 될텐데 결제는 정상적으로 취소되는 데이터 정합성 문제가 발생할 수 있기 때문입니다.
    - 그래서 @TransactionalEventListener를 사용하여 주문 취소 트랜잭션이 성공적으로 커밋된 후에만 결제 취소 이벤트가 실행되어 데이터 정합성을 보장할 수 있게 하기 위해 선택했습니다.

### 주요 로직

![image](https://github.com/user-attachments/assets/fae0a438-4034-47c8-8324-8f75678f7a5c)


1. **주문 취소 시 이벤트 발행(OrderService.class)**

```java
@Transactional
public void cancel(Long id) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new CustomException(ORDER_ID_NOT_FOUND));

    order.updateState(OrderState.CANCELING);

    eventPublisher.publishEvent(new PaymentRefundEvent(order.getPayment().getUid()));
}
```

- 주문 상태를 CANCELING으로 변경한 후 결제 취소 이벤트를 발행합니다.
1. **이벤트 리스너에서 결제 취소 처리(PaymentService.class)**

```java
@TransactionalEventListener
    public void handleOrderCancelEvent(PaymentRefundEvent event) throws Exception {
        Payment payment = paymentRepository.findByUid(event.getOrderId())
                .orElseThrow();

        String paymentKey = invoiceRepository.findKeyByPaymentId(payment.getId());
        paymentApprovalService.cancelPayment(paymentKey);

        payment.updatePaymentStatus(PaymentState.CANCEL);
        payment.getOrder().updateState(OrderState.CANCELED);
    }
```

- 트랜잭션이 정상적으로 커밋된 후에만 이벤트가 실행됩니다.
- 이벤트가 발생하면 결제 취소 API를 호출하고 상태를 업데이트 합니다.

---

# 결론 및 회고

Spring Event 기반 설계로 인하여 도메인 서비스 간 결합도를 낮출 수 있었습니다. 트랜잭션 커밋 이후 이벤트가 실행되므로 결제 시스템에서 중요한 데이터 정합성을 유지할 수 있었습니다.

하지만 Event 기반 아키텍쳐가 많아지면 전체적인 흐름을 파악하기 어려울 수 있고 어떤 이벤트가 어디서 실행되는지를 추척하기 어려운 단점이 있겠다는 생각을 했습니다.

그래서 이벤트 사용이 많아져 추적하기 어려운 단점을 이벤트 로깅을 추가하여 추적 가능성을 높이는 방안을 고려했습니다.
  </details>

  <details>
    <summary>결제 시나리오 1 - 결제 재시도 Self-Invocation 문제</summary>

# 문제 상황
결제 시나리오 1번 재시도 로직을 구현하던 중 `@Transactional` 이 적용된 메서드 안에서 `@Retryable` 이 적용된 메서드를 호출하는데 `@Retryable` 이 적용되지 않는 문제를 발견했습니다.

**[기대한 결과]**

![image](https://github.com/user-attachments/assets/0d3eff7a-d14d-4850-b782-e0530eff9ece)


**[실제 결과]**

![image](https://github.com/user-attachments/assets/9a3b7fc7-4c3c-484a-8ce7-c9cbc63a6533)


![image](https://github.com/user-attachments/assets/e959dd8b-436a-4454-9258-6a6c589e78d2)


- 실제 결과를 보면 예상과 달리 재시도 로직이 실행되지 않고 바로 예외가 터지는 모습을 보여줍니다.

---

# 해결 과정

위 문제를 해결하기 위해 총 7가지 테스트 케이스를 만들어 확인을 진행하였습니다.

@Retryable **단독 케이스(Case 1~3)**

| 케이스 | 설명 | 결과 |
| --- | --- | --- |
| Case1 | @Retryable 이 적용된 메서드 단독 실행 | 성공 |
| Case2 | **같은 클래스** 내부에서 일반 메서드가 @Retryable 메서드 호츌 | 실패 |
| Case3 | 일반 메서드에서 **다른 클래스**의 @Retryable 메서드 호출 | 성공 |

**@Transactional + @Retryable 조합 테스트 (Case 4~7)**

| 케이스 | 설명 | 결과 |
| --- | --- | --- |
| Case4 | @Transactional 이 적용된 메서드에서 **같은 클래스**의 @Retryable 메서드 호출 | 실패 |
| Case5 | 같은 클래스 내에서 @Retryable 과 @Transactional 을 분리하여 호출 | 실패 |
| Case6 | @Transactional 이 적용된 메서드에서 다른 클래스의 @Retryable 메서드 호출 | 성공 |
| Case7 | 일반 메서드에서 같은 클래스의 @Transactional 메서드와 다른 클래스 @Retryable 메서드 호출 | 성공 |

**같은 클래스 내부에서 호출을 한다면 동작하지 않고** 다른 클래스에서 주입을 받아 호출을한다면 정상적으로 동작한다는 것을 알 수 있었습니다.

다른 클래스에서 주입 받았을 경우 결과 재시도 로직이 정상적으로 수행됨을 보여줬습니다.

![image](https://github.com/user-attachments/assets/61dac591-8ac9-4dc2-9526-4653f2ec3c13)


### [원인 분석]

왜 같은 **같은 클래스 내부에서 호출을 한다면 동작하지 않고** 다른 클래스에서 주입을 받아 호출을한다면 정상적으로 동작하는지에 대해서 분석을 해보기로 했습니다.

원인은 Intellij 에서 제공하는 노란 밑줄로 힌트를 얻었습니다.

![image](https://github.com/user-attachments/assets/5dbb5ac1-0516-4e43-a9dc-9707bbc9ecd0)


- 원인 분석 결과 **Self-Invocation** 문제였습니다.
**@Transactional, @Retryable** 등과 같은 **AOP Annotation**은 프록시 객체를 통해 호출될 때만 동작을 합니다.
- 하지만 동인 클래스 내부에서 직접 자신의 메서드를 호출하게 된다면 프록시 객체를 거치지 않고 원래 객체 메서드를 호출하게 되어 AOP Annotation 기능이 적용되지 않게 되는 것이었습니다.

### [해결 방법]

Self-Invocation은 2가지 케이스로 해결할 수 있었습니다.

Case1. 다른 클래스에서 @Retryable 메서드 호출
→ @Retryable이 적용된 메서드를 별도 클래스로 분리해서 해결할 수 있습니다.

![image](https://github.com/user-attachments/assets/86e936f6-19bf-4019-893b-431b2ccce522)



Case2. ApplicationContext에서 자기 자신을 주입 받아 호출
→ SpringContainer에서 자기 자신을 가져와 프록시를 거치도록 처리하여 해결할 수 있었습니다.

---

# 결론 및 회고

Case 2를 선택할 시 코드의 구조 변경은 하지 않지만 만약 다른 사람이 내 코드를 본다면 왜 이렇게 의도한지 눈에 들어오지 않아 직관성이 떨어질 것입니다.
코드는 나 자신 혼자 짜는 것이 아니라 협력하는 것이라 생각하여 코드가 조금 분리 되더라도 직관성을 해치지 않는게 우선이라 판단하여 Case 1을 선택하여 해결했습니다.

1. 재시도 로직이 실행되지 않는 문제 발생
2. 테스트 케이스를 통해 Self-Invocation 문제 라고 판단
3. 클래스를 분리하여, 프록시를 주입해 문제해결

@Transactional과 @Retryable이 같은 클래스에서 함께 동작하지 않는 이유를 분석하면서 여태까지 애매하게 생각 되었던 Spring 컨테이너부터 어떻게 객체가 주입되고 Spring Aop가 프록시 기반으로 동작한다는 점과 Self-Invocation 문제가 발생하면 AOP가 적용되지 않는 다는 사실까지 알게되었습니다.
  </details>

  <details>
    <summary>결제 시나리오 2 - 악의적 선점 데이터 정합성 문제 (1)</summary>

# 문제 상황
Spring Scheduler를 활용하여 10분이 지난 IN_PROGRESS 상태의 결제를 자동으로 취소하는 방식으로 구현하면서 트러블 슈팅이 발생했습니다.

(악의적 사용자가 아닌)일반 사용자가 어떠한 사유로 9분 5x초에 결제를 진행하면 결제 요청과 스케줄러의 실행이 동시에 이루어질 수 있습니다.
이로 인해 **실제 결제는 성공했지만 시스템에서는 결제가 취소되는 데이터 정합성 문제가 발생**했습니다.

### [문제 상황 설명]

![image](https://github.com/user-attachments/assets/32bada37-1f8e-4692-92f2-8f039c7a93ef)


1. **사용자가 결제 페이지에 진입**
    - Payment 상태 : IN_PROGRESS
    - 사용자는 결제 정보 입력 후, 9분 5x초에 결제 진행
2. **TossPayments에 결제 요청**
    - TossPayments에서 /success 엔드포인트로 PaymentKey, orderId, amount(가격) 정보와 함께 POST 요청을 보냄
    - DB에 저장된 결제 금액과 TossPayments의 요청 금액을 검증
    - 검증을 진행하면서 **Payment 상태를 CONFIRMING으로 변경**
3. **동시에 10분이 지나면서 스케줄러 실행**
    - 스케줄러가 IN_PROGRESS 상태이고 10분이 지난 Payments들을 조회
    - 스케줄러가 조회한 시점은 IN_PROGRESS 이므로 CONFIRMING으로 변경 되어도 스케줄러는 주문 취소를 진행
4. **TossPayment에 최종 승인 요청**
    - TossPayments로 최종 승인 요청을 보냄
    - **실제 결제는 성공했지만 금액이 차감됨.**

**→문제 발생!**

- 결제는 성공했는데, 시스템에서는 결제가 취소된 것으로 기록
- 결국 사용자는 돈을 냈지만 결제 취소가 되는 치명적인 오류가 발생!

### [문제 발생 원인 분석]

- 트랜잭션 타이밍 이슈
    - 스케줄러가 실행될 때 Payment 상태가 IN_PROGRESS로 조회됨
    - 하지만 거의 동시에 결제 승인 요청이 들어와 Payment 상태가 CONFIRMING으로 변경됨
    - 스케줄러는 이미 조회된 Payment 정보를 기반으로 CANCEL 처리를 진행하여
    실제 결제가 완료되었음에도 불구하고 시스템에서는 결제가 취소된 상태가 되어버림

---

# 해결과정

- 스케줄러가 실행될 때 Payment 데이터를 조회하면서 상태를 IN_PROGRESS로 확인한다.
- 이 상태가 CONFIRMING으로 변경되었더라도 스케줄러가 이미 조회한 데이터 기준으로 취소를 진행할 가능성이 있다.
- 즉 **이미 조회된 데이터(IN_PROGRESS)를 기반으로 취소를 실행하면 결국 결제가 완료되었는데도 취소될 수 있다!**

⇒ 그럼 스케줄러가 조회하고 다시 한번 최신상태로 조회하는 로직을 짜면 어떨까? 생각을 했습니다.

![image](https://github.com/user-attachments/assets/0b145e87-5522-4a33-9588-d8c753595547)


```java
  	@Scheduled(cron = "0 */10 * * * *")
    public void paymentTimeoutScheduler() {
        List<Payment> payments = paymentRepository.findAllExpired(); // 10분이 지난 IN_PROGRESS 상태들을 불러옴.
        for (Payment payment : payments) {
            paymentStateService.cancelTimeOutPayments(payment);
        }
    }
```

```java
 		@Transactional
    public void cancelTimeOutPayments(Payment payment) {
        // 여기서 다시 한번 조회하여 최신 상태 조회
        Payment paymentInDB = paymentRepository.findById(payment.getId())
                .orElseThrow();

        if (paymentInDB.getState() != PaymentState.IN_PROGRESS) {
            return;
        }

        paymentInDB.updatePaymentStatus(PaymentState.CANCEL);
        paymentInDB.getOrder().updateState(OrderState.CANCELED);
        paymentInDB.getOrder().getTickets().forEach(orderTicket ->
                orderTicket.getTicket().updateState(SeatState.IDLE));
        paymentRepository.save(paymentInDB);
    }
```

- 스케줄러가 실행되고 1차적으로 결제 진행이 10분 이상된 결제 정보들을 불러오고 그 사이에 결제 상태가 바뀐 것을 해결하기 위해 최종적으로 조회하여 최신 상태를 반영 합니다.
    - IN_PROGRESS 상태가 CONFIRMING 또는 COMPLETED로 변경되었다면 취소하지 않고 바로 return하여 스케줄 메서드를 종료합니다.

---

# 결론

이번 트러블 슈팅을 통해 **스케줄러가 조회한 오래된 데이터(IN_PROGRESS 상태)로 인해 결제가 완료되었음에도 취소되는 문제**를 해결할 수 있었습니다. 
기존에는 **스케줄러가 조회한 데이터를 바로 취소 처리**하는 방식이었지만, 최신 상태를 다시 한 번 조회하는 로직을 추가함으로써, **CONFIRMING 또는 COMPLETED 상태로 변경된 결제는 취소되지 않도록 방어 로직을 적용**하였습니다.

이를 통해 **데이터 정합성을 유지하면서도 불필요한 취소를 방지**할 수 있었으며, 결제 과정에서 발생하는 타이밍 이슈를 보다 안전하게 처리할 수 있게 되었습니다.
  </details>

  <details>
    <summary>결제 시나리오 3 - 악의적 선점 데이터 정합성 문제 (2)</summary>
    
# 문제 상황
이전 트러블 슈팅을 통해 스케줄러가 조회한 후 결제 상태가 변경되었을 가능성을 고려하여 최신 상태를 다시 조회하는 로직을 추가함으로써 데이터 정합성 문제를 해결할 수 있었습니다.

그러나 여전히 결제 승인 요청과 스케줄러가 동시에 실행되는 경우 **아주 짧은 시간 간격으로 정합성이 깨질 가능성**이 남아있었습니다.

→ 여전히 발생할 수 있는 문제

1. **사용자가 9분 5x초에 결제 진행 → CONFIRMING 상태로 변경**
2. **동일한 시점에 스케줄러 실행 → 기존 IN_PROGRESS 상태를 조회 후 취소 처리 시도**
3. **스케줄러가 findById()로 최신 상태를 다시 조회했지만, 아주 미세한 타이밍 차이로 CONFIRMING 상태 변경이 반영되기 직전이면 여전히 취소 진행**
4. **결제 승인 요청이 완료되었음에도, 결제 내역이 취소되는 문제 발생 가능성**

즉 **트랜잭션의 순서와 타이밍에 따라 여전히 결제 성공 후 취소가 이루어질 수 있는 잠재적 문제가 남아 있습니다.**

---

# 해결 과정

문제를 해결하기 위한 접근 방법으로 **Redisson 기반의 락을 플래그로 활용하여 “현재 결제 진행 중” 이라는 신호를 시스템적으로 보장하는 방식**을 적용하려 합니다.

이를 통해 스케줄러가 결제 취소를 시도하기 전에 해당 결제가 진행 중인지 여부를 락을 통해 확인하도록 보완할 수 있습니다.

특히 우리 팀에서는 이미 다른 팀원이 Redisson 기반의 락을 구현해 놓은 상태였기 때문에 새로운 락 구현을 할 필요 없이 기존의 로직을 재사용하는 것이 일관성 유지, 개발 효율성 측면에서 더 적절한 선택이라 판단했습니다.

### [VerifyReqeust() 락 적용]

EX

- 사용자 A가 “A 1번 좌석”을 선택하고 결제를 진행한다고 가정하겠습니다
- 이때 해당 결제의 orderId = “order-1234”
- verifyRequest()가 실행되면서 “현재 결제 진행 중”을 나타내기 위한 락을 생성합니다.

이 락이 걸려있는 동안은 동일한 orderId에 대한 다른 작업(스케줄러 취소 등)이 실행되지 않습니다.

![image](https://github.com/user-attachments/assets/9b6a59c6-0621-45d7-9dbd-b6d9a8a00b25)


```java
@Transactional
@Lock(key = "#orderId", prefix = "payment_lock:") // 특정 orderId에 대한 락 설정
public void verifyRequest(String orderId, int amount) {
    Payment payment = paymentRepository.findByUid(orderId)
        .orElseThrow(() -> new CustomException(ServerErrorResponseCode.ORDER_UUID_NOT_FOUND));

		// 결제 상태를 CONFIRMING으로 변경
    payment.updatePaymentStatus(PaymentState.CONFIRMING);
		
		// PG사에서 요청한 금액과 DB 저장 금액이 다르면 예외 발생
    if (payment.getAmount() != amount) {
        throw new PaymentException(PaymentErrorResponseCode.PAYMENT_AMOUNT_MISMATCH);
    }
}
```

1. 결제 검증이 진행 중일 때(IN_PROGRESS → CONFIRMING)락이 걸려있으므로 해당 orederId에는 다른 작업이 접근하지 못합니다.
2. 특히 스케줄러(cancelTimeOutPayments())가 실행되더라도 락을 획득하지 못하므로 취소되지 않습니다.
- 즉 “지금 이 결제는 진행 중이니 취소시키지마” 라는 **플래그 역할**을 락이 수행하는 것입니다.

**위에서는 verifyRequest()에만 락을 걸면 해결될 것 같이 말했지만 사실은 의미가 없는 행동입니다.
verifyRequest()에만 락을 걸면, 스케줄러가 이미 조회한 IN_PROGRESS 상태를 기반으로 결제를 취소할 가능성이 남아있습니다.**

즉, 스케줄러(cancelTimeOutPayments)에서도 같은 orderId에 대한 락을 확인해야, 결제 진행 중인 상태를 보호할 수 있습니다. 따라서 두개의 메서드 모두 락을 확인해야 데이터 정합성을 완벽하게 보장할 수 있습니다.

### [cancelTimeOutPayments() 락 적용]

Ex

- 10분 후 스케줄러가 실행되면서 “order-1234”를 취소하려고 시도를 할 것입니다.
- 하지만 이미 verifyRequest()에서 “payment_lock:order-1234”락이 걸려있는 상태
- 스케줄러는 락을 획득할 수 없으므로 해당 결제 건을 취소하지 않고 넘어갈 것입니다.

![image](https://github.com/user-attachments/assets/7a3a7cdf-b0ad-4984-877c-8e35f363e10e)


```java
@Transactional
    @Lock(key = "#payment.getUid()", prefix = "payment_lock:")
    public void cancelTimeOutPayments(Payment payment) {
        Payment paymentInDB = paymentRepository.findById(payment.getId())
                .orElseThrow();

        if (paymentInDB.getState() != PaymentState.IN_PROGRESS) {
            return;
        }

        paymentInDB.updatePaymentStatus(PaymentState.CANCEL);
        paymentInDB.getOrder().updateState(OrderState.CANCELED);
        paymentInDB.getOrder().getTickets().forEach(orderTicket ->
                orderTicket.getTicket().updateState(SeatState.IDLE));
        paymentRepository.save(paymentInDB);
    }
```

1. 스케줄러가 실행될 때 동일안 orderId에 대한 락이 걸려 있다면 락을 획득하지 못하고 넘어갑니다.
2. 즉 verifyRequest()가 진행 중인 동안 스케줄러가 결제를 취소하는 문제를 방지합니다.
3. 락이 해제된 후 다시 스케줄러가 실행되었을 때 상태를 확인하고 취소할지 결정합니다.

![image](https://github.com/user-attachments/assets/6cdeceb3-59a6-4f23-8fd3-c88d1fb0cc93)


**verifyRequest()에서 락이 해제되었을 경우 스케줄러 동작 방식**

1. verifyRequest()에서 검증이 완료되고 **CONFIRMING**으로 상태가 변경됨.
2. 스케줄러는 해당 orderId에 대해 Lock 체크를 하고 다시 DB에서 조회를 함.
3. DB에서 orderId의 상태가 이미 IN_PROGRESS → CONFIRMING으로 변경되었으므로 결제 취소 로직을 실행하지 않고 그냥 넘어감

---

# 결론 및 회고

- 결제 승인 요청(verifyRequest)과 스케줄러(cancelTimeOutPayments)는 서로 직접 호출하지 않지만 같은 결제 데이터(orderId)를 다루므로, 동시에 실행되면 정합성이 깨질 가능성이 있습니다.
- 따라서 Redisson 기반의 락을 활용하여, "현재 결제가 진행 중이므로 취소하면 안 된다"는 **신호(플래그)**를 설정하는 것이 최적의 해결책이었습니다.
- 이를 통해 스케줄러가 결제를 임의로 취소하는 문제를 방지하고, **결제 과정의 데이터 정합성을 보장**할 수 있게 되었습니다.
  </details>

  <details>
    <summary>항공권 조회 API에 Redis 캐싱</summary>

# 문제 상황
타임 특가 할인 이벤트로 vuser 500명이 1초마다 요청을 보내는 상황입니다.

인덱스만 적용했을 때 95퍼센타일이 8.6초를 넘어가는 응답 지연이 발생했습니다.

# 해결 과정

응답 속도 개선을 위해 캐싱을 도입하여 데이터 접근방식을 최적화했습니다.

**[캐싱 적용]**

- Redis Cache 적용
- 적용 대상 API : 항공 시간표 다건 조회 API (GET /api/flight-plans)
    - 항공 시간표는 수정, 삭제가 없고 실시간성 데이터가 아니기 때문에 데이터 정합성 문제에서 자유로움
- 주요 캐싱 로직 :
    - @Cacheable(Look Aside 전략) 을 사용하여 항공 시간표 조회 시 DB 호출 없이 Redis에 저장된 데이터를 조회함
    - Redis에 저장된 데이터가 없을 경우 DB에서 데이터를 조회한 후 Redis에 저장함

**[성능 테스트]**

- 캐싱 적용 전 테스트

![image](https://github.com/user-attachments/assets/4193cf25-091f-45a9-8aa4-4adcb7043689)


- 캐싱 적용 후 테스트

![image](https://github.com/user-attachments/assets/2bd8f697-6992-48de-a975-72555df705fd)


- Response Time Percentiles 비교
    - 적용 전
    
    ![image](https://github.com/user-attachments/assets/e52cd89e-5704-4f2f-8c74-5a413c897efa)

    
    - 적용 후
    
    ![image](https://github.com/user-attachments/assets/7a2ba303-4fcb-49d7-9b9f-c1f352facf6c)

    

- Response Time Percentile : (95퍼센타일 기준) 8658 ms → 155 ms
    
    → 응답시간 **98% 감소**
    
- Troughput : 56.84 tps → 1049.76 tps
    
    **→ 1746.87% 증가**
    

# 결론

캐싱을 적용하여 응답속도와 처리량이 높은 수준으로 개선된 것을 확인할 수 있었습니다.
  </details>

  <details>
    <summary>캐싱 적용과정 직렬화 문제</summary>

# 문제 상황

CacheConfig 클래스로 Redis 캐싱 설정을 마치고 실행하였을때 에러가 발생했다.

**[에러 로그]**

```java
2025-02-26T17:45:17.571+09:00 ERROR 86123 --- [Flight-Booking] [nio-8080-exec-2] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.springframework.data.redis.serializer.SerializationException: Cannot serialize] with root cause

java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [flight_booking.demo.utils.Page]
```

캐싱하려는 대상 객체가 (Page) Serializable 인터페이스를 구현하지 않았기 때문에 기본 직렬화 방식(JdkSerializationRedisSerializer)에서 직렬화하지 못함

1. 대상 객체에 Serializable 인터페이스 구현
    - 이 방법은 기존 코드를 수정해야 하기 때문에 채택하지 않음
2. 직렬화 방식 변경
    - 기존에는 스프링부트의 기본 직렬화 방식인 JdkSerializationRedisSerializer이 아니라 JSON기반의 GenericJackson2JsonRedisSerializer를 사용
    
    ```java
    @Configuration
    public class RedisConfig {
        @Bean
        public RedisCacheConfiguration cacheConfiguration() {
            RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                    .serializeKeysWith(RedisSerializationContext.SerializationPair
                            .fromSerializer(new StringRedisSerializer()))
                    .serializeValuesWith(RedisSerializationContext.SerializationPair
                    .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                    .entryTtl(Duration.ofHours(1L));
        }
    }
    ```
    

대상 객체 직렬화 문제는 GenericJackson2JsonRedisSerializer를 통해 해결하였지만 다른 에러가 발생하였다. 해당 에러는 ZonedDateTime을 직렬화 하지 못해서 문제가 발생.
  </details>

  <details>
    <summary>ZonedDateTime 직렬화</summary>

# 문제 상황

캐싱 설정에서 ZonedDateTime이 직렬화 되지 않아 에러가 발생함

Redis 캐시 직렬화 방식으로 사용한 GenericJackson2JsonRedisSerializer를 사용중.

Jackson은 Java8의 Java.time패키지의 날짜 타입을 지원하지 않음

**[에러 로그]**

```java
2025-02-26T18:10:27.269+09:00 ERROR 86623 --- [Flight-Booking] [nio-8080-exec-1] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.springframework.data.redis.serializer.SerializationException: Could not write JSON: Java 8 date/time type java.time.ZonedDateTime not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: org.springframework.http.ResponseEntity["body"]->flight_booking.demo.utils.Page["content"]->java.util.Collections$UnmodifiableRandomAccessList[0]->flight_booking.demo.domain.flight.dto.response.FlightPlanGetListResponse["boardingAt"])] with root cause

com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type java.time.ZonedDateTime not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: org.springframework.http.ResponseEntity["body"]->flight_booking.demo.utils.Page["content"]->java.util.Collections$UnmodifiableRandomAccessList[0]->flight_booking.demo.domain.flight.dto.response.FlightPlanGetListResponse["boardingAt"])
```

# 해결 과정

**[해결 방안]**

1. Custom ObjectMapper
2. Dto에서 @JsonFormat으로 ZonedDateTime을 String으로 변환
    - 해당 방법도 기존 Dto를 수정해야 하므로 CacheConfig 설정을 변경하기로 함

Custom ObjectMapper를 생성하고, JavaTimeModule을 등록한 후 GenericJackson2JsonRedisSerializer의 파라미터로 해당 ObjectMapper를 넘겨줌

```java
@Configuration
public class CacheConfig {
    ...
    
    ...
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        // Jackson ObjectMapper 설정 (JavaTimeModule 추가)
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // ZonedDateTime 직렬화 지원
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer())) // 키를 문자열로 직렬화
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(serializer)) // 값 직렬화를 JSON 방식으로 변경
                .entryTtl(Duration.ofHours(1));
        ...
        
        ...
    }
}
```

# 결론

objectMapper객체에 JavaTimeModule을 등록한 후 GenericJackson2JsonRedisSerializer의 인자로  objectMapper객체를 넘겨주어 ZonedDateTime을 직렬화 할 수 있었다. 그리고 항공권 조회 API에 @Cacheable을 달아준 뒤 포스트맨 요청을 보내어 성공적으로 동작하는 것을 확인할 수 있었다. 하지만 여기서 Cacheable이 동작하지 않는 문제가 발생하였다.
  </details>

  <details>
    <summary>Cacheable 미동작 문제</summary>

# 문제 상황

CacheConfig 클래스를 생성하고 Redis Cache 설정을 완료하고 항공권 조회 메서드에 Cacheable을 붙여준 뒤 포스트맨으로 실행했을 때 실행 시간의 차이가 거의 없었다. 

### 1. 캐시 키 문제

**[원인]**

1. 객체의 기본 toString() 사용
    - Java의 기본 toString()은 클래스명과 메모리 주소(해시코드)를 포함하므로, 같은 내용이라도 인스턴스가 다르면 결과가 달라짐.
2. equals(), hashCode() 미구현
    - PageQuery와 같은 객체에 대해 equals()와 hashCode()를 오버라이드하지 않으면, 동일한 값임에도 불구하고 객체 비교가 제대로 되지 않음.
    - Redis에 저장된 키값을 확인해보니 PageQuery객체가 각각 다른 주소값을 가지고 있었음
**[해결 방안]**

- PageQuery 객체에 @EqualsAndHashCode 사용

```java
@Getter
@Setter
@EqualsAndHashCode
public class PageQuery {
/// field, constructor, method...
]
```

### 2. 제네릭 Custom Page 역직렬화 문제

캐시된 데이터를 조회할 때,

```java
java.lang.ClassCastException: java.util.LinkedHashMap incompatible with flight_booking.demo.utils.Page
```

와 같은 예외 발생.

**[원인]**

1. 제네릭 타입 소거:
Jackson으로 JSON 직렬화 시 제네릭 타입 정보가 충분히 포함되지 않으면, 역직렬화 시 구체 타입(예: Custom Page 구현체) 대신 LinkedHashMap으로 복원됨.

**[해결 방안]**
**ObjectMapper에 타입 정보 포함 설정**:
activateDefaultTyping을 사용하여 직렬화 시 타입 정보를 포함시키도록 설정.

```java
ObjectMapper objectMapper = new ObjectMapper();
objectMapper.registerModule(new JavaTimeModule());
objectMapper.activateDefaultTyping(
		LaissezFaireSubTypeValidator.instance,
		ObjectMapper.DefaultTyping.NON_FINAL,
		JsonTypeInfo.As.PROPERTY
);
```

**@JsonTypeInfo 어노테이션 사용**:
Custom Page 클래스에 @JsonTypeInfo 어노테이션을 추가하여, 직렬화/역직렬화 시 구체 타입 정보를 포함시킴.

1. **반드시 구체적인 구현체 사용**:
반환 타입을 인터페이스가 아닌 구체적인 구현체(예: PageImpl)로 명시하면, Jackson이 올바른 타입으로 역직렬화할 가능성이 높아짐.

### 3. 정적 팩토리 메소드와 생성자 관련 역직렬화 문제

Custom Page 등의 객체를 역직렬화할 때, 기본 생성자가 없어서 에러 발생.

**[원인]**

1. **@AllArgsConstructor만 존재**:
Lombok의 @AllArgsConstructor는 모든 필드를 받는 생성자만 제공하므로, Jackson이 기본 생성자 없이 객체를 생성할 수 없음.
2. **정적 팩토리 메소드 사용**:
정적 팩토리 메소드를 사용하고 있다면, Jackson이 이를 인식하지 못할 경우 역직렬화에 실패함.
[해결 방안]
기본 생성자 추가:
가능하다면 @NoArgsConstructor를 추가하여 Jackson이 기본 생성자를 통해 객체를 생성할 수 있도록 함.
```
@Getter
@Setter
@NoArgsConstructor
public class Page<T> {
/// field, constructor, method...
}
```
  </details>

  <details>
    <summary>ClassCastException 문제 해결</summary>

### 문제 상황
- SecurityContextHolder에서 가져온 principal이 User 객체가 아니라 String(예: 이메일)로 설정되어 있음.

### 해결 과정

1. **DB 조회 방식**:
    - principal이 String이면, 이를 이용해 UserRepository에서 User 엔티티 조회.
2. **커스텀 UserDetails 사용**:
    - CustomUserDetails 클래스를 만들어 User 엔티티를 래핑하고, Authentication의 principal에 해당 객체를 저장.
  </details>

  <details>
    <summary>JWT 토큰 생성</summary>

### 문제 상황
- 기존 토큰 생성 및 검증 방식에서 보안 및 성능 이슈 발생. ← 정확히 뭔 이슈요?

### 해결 과정

1. **토큰 생성 (makeToken 메서드)**:
    - `jwtProperties.getSecretKey()`를 UTF-8 인코딩 후, `Keys.hmacShaKeyFor()`로 secret key 생성.
    - 토큰의 헤더, issuer, 발급시각, 만료시각, subject(사용자 이메일) 및 추가 클레임(사용자 ID, 이름, membership, role) 설정.
    - HS256 알고리즘으로 서명하여 토큰 생성.
2. **토큰 검증 (validToken 메서드 및 getClaims 메서드)**:
    - 토큰 검증 시에도 동일한 secret key 객체를 생성하여 사용.
    - `Jwts.parserBuilder()`를 활용하여 토큰 파싱 및 Claims 추출.
    - `getClaims()` 메서드도 동일한 방식으로 secret key를 생성 후 사용하도록 수정.
  </details>

  <details>
    <summary>TokenProvider 클래스 전체 구현</summary>

### 문제 상황
- 기존 TokenProvider 클래스의 로직이 불완전하여, 인증 흐름에서 발생한 문제 해결 필요.

### 해결 과정

1. `generateToken()`: User와 만료시간 정보를 받아 JWT 토큰 생성.
2. `getAuthentication()`: JWT 토큰에서 Claims 추출 후, UserRepository를 통해 사용자 조회하여 Authentication 객체 생성 및 SecurityContextHolder에 저장.
3. `getAccessToken()` / `getRefreshToken()`: HTTP 요청의 헤더 및 쿠키에서 각각 액세스 토큰과 리프레시 토큰 추출.

### 결론

- 수정한 JWT 관련 로직과 TokenProvider 전체 코드가 문제 없이 동작함.
- 토큰 생성, 검증, 그리고 인증 흐름에서 발생했던 문제들이 모두 해결됨.
  </details>

  <details>
    <summary>Redis 연결 문제</summary>

  ### 문제 상황

- Spring Boot 애플리케이션 실행 시 `RedisConnectionException` 발생.

### 해결 과정

1. Redis가 제대로 실행되고 있는지 확인 (`systemctl status redis`).
2. `CONFIG SET save ""` 명령어로 스냅샷 비활성화.
3. `netstat -tulnp | grep 6379`로 포트 확인 후 정상 작동.
  </details>

  <details>
    <summary>Spring Boot 로그 전송 문제</summary>

### 문제 상황

- `logback-spring.xml`에서 Loki로 로그가 전송되지 않음.

### 해결 과정

1. `lokiLabels` 설정이 잘못되어 `{application="spring-boot-app"}`가 인식되지 않음.
2. JSON Encoder 형식 및 `lokiLabels`를 올바르게 수정하여 로그 전송 정상화.

### 결론

- Loki, Redis, Grafana 등과 관련된 문제들이 해결되었으며, 전체 로그 수집 및 모니터링이 정상 작동함.
  </details>


