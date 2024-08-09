# 💸 FISA Forex Pay

🌐FISA Forex Pay는 글로벌 금융 시장에서 **환율(Forex)** 과 **송금(Pay)** 의 중요성을 결합하여, 사용자가 외화를 보다 쉽게 송금하고 관리할 수 있도록 돕는 서비스를 제공합니다. 

📋 목차
- 👥 팀원
- ⏳ 개발 기간
- 🎯 주요 기능
- 🏗️ 아키텍처
- 🗄️ 데이터베이스 스키마
- 🔧 설정
- 🔍 문제 해결

## 👥팀원

| <img src="https://avatars.githubusercontent.com/u/104816148?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/79884688?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/90691610?v=4" width="100" height="100"/> | <img src="https://avatars.githubusercontent.com/u/127733525?v=4" width="100" height="100"/> |
| --- | --- | --- | --- |
| 박현서<br/>[@hyleei](https://github.com/hyleei) | 박장우<br/>[@lisiant](https://github.com/Lisiant) | 이석철<br/>[@SeokCheol-Lee](https://github.com/SeokCheol-Lee) | 구동길<br/>[@dkac0012](https://github.com/dkac0012) |

## ⏳ 개발 기간 
2024년 8월 1일 - 2024년 8월 9일

## 🎯 주요 기능
**실시간 환율 조회** : 한국수출입은행 Open API를 통해 최신 환율 정보를 가져옵니다.  
**외화 송금** : 송금 요청을 제출하고, 환율과 수수료를 적용하여 최종 송금 금액을 계산합니다.  
**송금 내역 조회** : 이전에 수행된 송금 내역을 상세히 확인할 수 있습니다.  
**우대 수수료 적용** : 자산이 많은 사용자는 우대 환율 및 수수료 혜택을 받을 수 있습니다.  

## 🏗️ 아키텍처
**Spring Boot** : 백엔드 애플리케이션을 구성하여 REST API를 제공합니다.  
**JPA (Java Persistence API)** : 데이터베이스에 송금 내역과 계좌 정보를 저장합니다.  
**JavaScript** : 사용자 인터페이스(UI)를 구현하고, 비동기 데이터 처리를 지원합니다.  
**ELK Stack** : 송금 요청 및 환율 변동 로그를 수집하고 분석합니다.  

## 🗄️ 데이터베이스 스키마
### 주요 테이블
