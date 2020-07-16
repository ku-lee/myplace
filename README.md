# myplace 서비스 소개

### # 구현 기술 및 사용 라이브러리
 * Springboot, gradle, lombok
 * VIEW : mustache, bootstrap
 * DB : jpa, H2

### # 실행 방법
 1. gradle로 실행
 ```sh
./gradlew build
./gradlew run
```
         
2. docker로 실행
```sh
docker run -d -p 8081:8081 --name myplace kuleee/myplace:latest
```

* 실행 후 localhost:8081 접속 후 진행


### # 요구사항
#### # 로그인 : 
  - 첫 화면에서 [가입하기] 통해 아이디 / 비밀번호 등록 후진행.
  - 비밀번호는 랜덤 salt + 사용자 입력 아이디 로 hash 암호화 되어 저장.

#### # 장소 검색 : 
  - 카카오 키워드 장소검색 API  연동
  - 페이지 하단의 previous, next 버튼 눌러 이동 가능. (첫페이지에서 previous 이동, 마지막페이지에서 Next 이동 시 에러 메시지 발생)
  - 검색 을 눌렀을 경우에만 인기 검색어에 카운팅 처리 됨.

#### # 상세 조회 : 
  - 검색 된 장소의 이름을 클릭하면 상세 정보가 노출이 됨.

#### # 인기검색어 목록 : 
  - 장소 검색 시 해당 키워드와 검색 횟수 저장, 우측 상단의 인기검색어 클릭 시 노출.
  - 검색 횟수 높은 순으로 노출 되며, 같은 검색 횟수일 경우 키워드 순서에 맞춰 정렬.
  - 상위 10개 까지만 노출 됨.
  - 매번 SQL DB에 저장하는 것보다는 향후 다른 라이브러리(elasticsearch) 등을 연동하는게 더 효율이 좋지 않을까 생각되어 DB에 저장하지 않고, map을 사용하여 저장 하였습니다.
  
  
  
