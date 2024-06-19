# ChatGPT를 이용한 변수명 생성기

## 개요

`js-variable-name-generator`는 주어진 메시지를 다양한 프로그래밍 언어의 변수명 형식으로 변환하는 Spring Boot 기반의 서비스입니다.
OpenAI의 ChatGPT API를 활용하여 사용자의 요청에 따른 변수명을 생성하고 여러 포맷으로 제공합니다.
(https://platform.openai.com/docs/api-reference/introduction)

## 기능

- 주어진 메시지를 기반으로 변수명을 다양한 포맷으로 변환합니다. (CamelCase, PascalCase, SnakeCase(대문자, 소문자))
- OpenAI API를 활용하여 변수명을 제안합니다.
- JSON 응답 포맷으로 결과 반환합니다.

## 설치 및 설정

### 요구 사항

- Java 11 이상(프로젝트는 17버전을 사용)
- Spring Boot 3.3.0
- Gradle
- OpenAI API Key

### 설치

1. 저장소를 클론합니다.
   ```
   git clone https://github.com/leejaesup/js-variable-name-generator.git
   cd js-variable-name-generator
   ```
   
2. 필요한 의존성을 설치합니다.
   ```
   ./mvnw clean install   # Maven 사용 시
   ./gradlew build        # Gradle 사용 시
   ```

3. application.properties 파일을 설정합니다.
   src/main/resources/application.properties 파일에 다음 설정을 추가합니다.
   ```
   #application.properties
   openai.model=request_openai_model
   openai.secret-key=your_openai_api_key
   openai.api.url=https://api.openai.com/v1/chat/completions
   ```

### 사용 방법

1. 애플리케이션을 실행합니다.
   ```
   ./mvnw spring-boot:run   # Maven 사용 시
   ./gradlew bootRun        # Gradle 사용 시
   ```
   
2. 서비스에 요청을 보냅니다. (GET 요청을 통해 변환할 메시지를 보낼 수 있습니다.)
   ```
   curl -X GET http://localhost:8080/convert?requestMessages=[your message]
   ```
   
응답 예시:
   ```
    {
        "header": {
            "resultCode": "200",
            "resultMsg": "성공"
        },
        "body": {
            "totalCount": 3,
            "items": [
                {
                "index": 1,
                "original": "test message",
                "camelCase": "testMessage",
                "pascalCase": "TestMessage",
                "snakeCase": "test_message",
                "upperSnakeCase": "TEST_MESSAGE"
                }
            ]
        }
    }
   ```
