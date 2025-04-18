# 프로그래머스 데브코스 4차 프로젝트
# Bid & Buy - 중고 거래 사이트
기본적인 중고거래 사이트이나 중고물품 구매 희망자가 많을 경우 경매를 통해 판매를 하는 서비스를 제공하는 사이트

## 기획의도
- 대규모 트래픽, 실시간 기능, 경매, 결제 시스템 구현을 희망함
- 구현 희망한 기능을 종합하여 현재 시장에 있는 중고거래 플랫폼 내의 대화 기능이 아닌 카카오톡 오픈 채팅으로 단톡방을 만드는 불편을 해소하기 위해 프로젝트를 기획함
    - 인기있는 매물의 경우 여러 명의 사람이 문의를 하게 되고 그러한 경우 판매자가 카카오톡으로 경매를 하는 경우가 종종 있음

## 주요기능
- 게시글 CRUD
- 실시간 채팅
- 경매
- 결제
- 알람
- 팔로우

## 스팩
| 카테고리 | 기술 |
| --- | --- |
| **프로그래밍 언어** | Java, Kotlin |
| **프레임워크** | Spring Boot |
| **데이터베이스** | MySQL, Redis (캐싱 & 세션 관리) |
| **메시지 큐** | WebSocket, Redis pub/sub |
| **API 설계** | RESTful API (데이터 최적화) |
| **문서화** | Swagger, opneapi-generator |
| **인증 & 보안** | JWT |
| **배포 환경** | Docker + AWS |
| **CI/CD** | GitHub Actions |
| **테스트** | JUnit5, H2, Jmeter |

## 역할
- 백엔드 팀장
- 채팅 기능 구현
  - WebSocket과 Redis pub/sub를 이용하여 메시지 브로커와 통신을 구현하였고 Redis를 통해 캐싱 저장 및 조회하며 MySQL에 메시지 저장
- 팔로우 기능 구현
- Swagger, Opneapi-generator로 문서화하여 프론트 개발자에게 전달

## 추후계획
- 채팅 프론트엔드 수정 예정머스 데브코스 4차 프로젝트
# Bid & Buy - 중고 거래 사이트
기본적인 중고거래 사이트이나 중고물품 구매 희망자가 많을 경우 경매를 통해 판매를 하는 서비스를 제공하는 사이트

## 기획의도
- 대규모 트래픽, 실시간 기능, 경매, 결제 시스템 구현을 희망함
- 구현 희망한 기능을 종합하여 현재 시장에 있는 중고거래 플랫폼 내의 대화 기능이 아닌 카카오톡 오픈 채팅으로 단톡방을 만드는 불편을 해소하기 위해 프로젝트를 기획함
    - 인기있는 매물의 경우 여러 명의 사람이 문의를 하게 되고 그러한 경우 판매자가 카카오톡으로 경매를 하는 경우가 종종 있음

## 주요기능
- 게시글 CRUD
- 실시간 채팅
- 경매
- 결제
- 알람
- 팔로우

## 스팩
| 카테고리 | 기술 |
| --- | --- |
| **프로그래밍 언어** | Java, Kotlin |
| **프레임워크** | Spring Boot |
| **데이터베이스** | MySQL, Redis (캐싱 & 세션 관리) |
| **메시지 큐** | WebSocket, Redis pub/sub |
| **API 설계** | RESTful API (데이터 최적화) |
| **문서화** | Swagger, opneapi-generator |
| **인증 & 보안** | JWT |
| **배포 환경** | Docker + AWS |
| **CI/CD** | GitHub Actions |
| **테스트** | JUnit5, H2, Jmeter |

## 추후계획
- 채팅 프론트엔드 수정 예정

## 스웨거
https://api.app1.springservice.shop/swagger-ui/index.html

## 시연영상
https://drive.google.com/file/d/1nl0QqyB6O7Nyd0Og_o1dPnd6lk60GyjS/view?usp=sharing

## PPT
[결과보고서_7팀(중고거래+경매).pdf](https://github.com/user-attachments/files/19807668/_7.%2B.pdf)

## 배포 링크
https://www.app1.springservice.shop/
