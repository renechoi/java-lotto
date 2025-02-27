# 기능 구현 목록

---

## 일반 기능


### 구입 금액 입력
- 1,000원 단위 입력 
- 음수, 0을 제외한 정수만 입력

### 당첨번호 입력
- 1 이상 45 이하의 자연수
- 중복 불가
- 6개 입력 

### 보너스 번호 입력
- 1 이상 45 이하의 자연수
- 당첨 번호와 중복 불가
- 1개 입력

### 구입 금액 당 발행할 로또 개수 설정
- 1,000원 당 1개 

### 발행 로또 숫자 생성
- 1 이상 45 이하의 자연수
- 6개의 랜덤 숫자 
- 중복 불가

### 당첨 기준 설정
- 일치 여부에 따른 등수 및 당첨금 설정

### 사용자 로또 번호와 당첨 번호 비교 계산
- 일치 여부 판단 및 계수

### 총 수익률 계산

### 사용자 구매 로또 개수 출력 

### 발행한 로또 수량 및 번호 출력
- 로또 번호 오름차순 정렬

### 당첨 기준에 따른 결과 출력(당첨 통계)

### 총 수익률 출력
- 소수점 둘째 자리 반올림

## 예외 기능

### 사용자 입력 오류 처리
- 공통 
  - 입력을 안하거나 빈칸으로 한 경우
- 구입 금액 입력시 조건 불일치
  - 1,000원 단위가 아닌 경우
  - 숫자가 아닌 경우(쉼표나 한글을 포함한 경우도 포함)
- 로또 번호 입력시 조건 불일치
  - 쉼표 구분 형식을 지키지 않은 경우
  - 개수 입력이 틀린 경우(당첨 6, 보너스 1)
  - 1 이상 45 이하의 자연수가 아닌 경우
  - 중복을 입력한 경우
- `IllegalArgumentException` 발생 및 ["ERROR] 메시지 출력 후 종료
## <설계> 쟁점 및 아이디어

<details>
<summary>자세히</summary>

#### **개발을 시작하기 전 설계 단계에서의 쟁점 및 아이디어에 대한 기록이다**

1. 등수 및 당첨금을 설정하는 당첨 기준은 enum 클래스에서 작성하자
   - 등수 / 당첨금을 상수로써 설정할 수 있다. <br>
   

2. 테스트를 효율적으로 할 필요가 있다. 모든 세부 기능에 대해 테스트를 작성하기보단 <br>
중심이 되는 (반드시 필요한 기능) 위주의 테스트 코드로 작성해보자<br>


3. 출력부를 구현할 때 출력 메시지별 메서드를 각각 구현하도록 하자 (baseball에서는 println을 세팅하고 메시지까지 변수로 입력하였음) <br>


4. 당첨 통계를 계산하는 로직에서 2개와 7개의 경계를 잘 고려해야 한다. <br> 
if문을 나열하고 return하는 방식이 제일 먼저 떠오르지만 겹치는 상황을(ex: 5개 일치시 3개, 4개 일치도 해당) <br>
효율적으로 처리하여 리턴하는 방식을 고민해볼 필요가 있다. <br>


5. 만약 사용자가 너무 큰 금액 (ex : 1,000,000,000) 을 입력한다면 어떻게 할 것인가? 이에 대한 조건 설정에 대해서도 고민해보자 <br>


6. 5번 관련해서, 어느 정도의 금액에서 최고의 수익률을 내는지 탐구해서 최적값을 추천해주는 서비스를 생각해본다. <br>
   추후 애플리케이션을 develop하는 과제로 수행해보아도 좋을 것 같다. 데이터사이언스의 영역일텐데 이미 나와 있겠지만.<br>


7. 이번 과제에서 사용해보고 싶었던 자바 문법으로 Interface가 있는데 로또 구매가 중복으로 발생할 경우 기초 셋인 로또를 추상화하여<br>
구현해볼 수 있는 방법이 있지 않을까 고민해본다. <br>


8. 패키지 구성은 <br>
- Application
- ui(Input, Output)
- domain(로또, 숫자생성, 당첨기준, 비교계산, 수익률계산, 기타상수, *복권: 인터페이스 클래스)

이와 같이 우선 설정하도록 한다. 

</details>

<br>


## <개발> 쟁점 및 아이디어

<details>
<summary>자세히</summary>

#### **개발을 진행하는 단계에서의 쟁점 및 아이디어에 대한 기록이다**

1. Input으로부터 정보를 입력 받고 이는 유저에 속한 기능이다. 그렇다면 input의 기본이 되는 콘솔 메서드와 player로부터 amount, number를 받는 구체적인 메서드를 별도로 분리하는 게 맞을까 합치는 게 맞을까. 
또한 그 과정에서 어쨌든 정보를 메인(run) 메서드로 전달해주어야 한다. 이때 객체가 호출되어야 하는데 메서드를 더 감출 수는 없는 것일까. 


2. validation을 진행하는 별도의 클래스가 필요한가, 아니면 lotto, player 각각의 자체 클래스에서 수행되어야 하는가. 개인적으로 별도 클래스가 필요할 것으로 생각되나, 문제의 요구사항으로 제시된 로토 클래스를 활용할 것에 따라 
가이드라인이 lotto의 클래스 안에서 validation을 수행하라는 의도인 것으로 짐작되며 따라서 player의 validationTest 역시 자체 클래스에서 해주어야 할 듯하다.
그런 데.. . validate를 하다보면 or을 중첩시키는 상황이 발생한다. 좀 더 깔끔하게 자체 클래스를 사용하면 어떨까 싶다. 
=> 방법을 찾았는데 validate 세부 사항들에 대한 조건들을 별도 클래스로 만들어 관리한다 


3. 본 클래스에서 출력 클래스를 호출할 때 기능을 명시해주도록 하고자 굳이 import *를 해온 뒤 Message constant까지 import 해와 변수에 넣어주는 형식으로 구현했다.
printNotANumber();로 구현할 수 있는 것을 굳이 PrintErrorMessage(AMOUNT_NOT_A_NUMBER) 형식으로 한 것이다. 

4. Validation을 진행할 때 검증이 4개를 넘어가면 메서드 길이가 20행까지 길어진다.
   throw new IllegalArgumentException();을 출력부에서 한번에 처리할 수 있도록 했다. 
    3,4번은 기능적으로 이것이 맞는것인지 고민이 있다. 


5. enum 클래스에 int형으로 요소들을 구성했다. 그런데 regex도 상수로서 enum 클래스에서 같이 관리를 하고 싶은데 형이 다르므로 둘 중에 하나를 별도 변환을 해주어야 하는 문제가 생긴다. 


6. Numbers를 다루는 과정에서 자꾸 형변환이 발생한다. Stream을 통해 int화 시켜주었지만 stream은 1회용이라는 것을 알았다. 형변환을 깔끔하고 효율적으로 할 수 있어야 깔끔한 코드를 작성할 수 있을 것 같다. <br>


7. 클래스를 별도로 만들고 데이터를 전송하는 것이 영 어렵다. winning nnumber와 bonus number를 player 클래스에서 정리하고 필드에 올려 저장하였다. lotto에서 만들어지는 number들은 issuer에서 저장하였다.
이들을 저장하는 이유는 calculation에서 사용하기 위함이다. 
이런 방식으로 하면 여러번 자원이 낭비되는 느낌이 드는데 딱히 방법을 모르겠는 것이 문제다.<br>


8. 변수가 input으로 받고 흐름이 진행되면서 생기는 기능들에 대한 단위테스트를 위해서는 이전의 로직들이 반복되는 것이 필요하다.
이럴 때 void 메서드에 대해서 기능 자체 검증을 위한 단위테스트는 어떻게 가능한가? <br>


9. 제시된 예외 테스트에서 로그를 남기며 종료가 되어야 통과가 되는 문제를 발견하여 main 메서드에서 try catch 구문으로 잡아주었다.<br>
그런데 main에서 실행 파일 전체를 try catch로 잡기보다는 개별적인 오류에서 exception을 발생시키고 그것으로 내부적으로 메시지를 전달하는 방식이 되어야 할 것 같다.<br>


10. 제시된 기능 테스트에서 List.of를 통해 랜덤 숫자를 초기화하고 있는데 List.of는 Unmodifiable이므로 sort 기능을 사용할 경우 프로그램이 종료된다.
이 문제를 해결하기 위해 sort가 되어있는지 확인하고 수행을 하도록 하였는데 적절한 방법 같지는 않다. <br>

v1 release 

--- 

필요한 리팩토링 to do

0. [x]  Game에서 시작되는 프로그램 running이 잘 읽히는 느낌이 아니다. 데이터를 주고받을 때 static이 필요한 부분과 return으로 사용할 부분을 나눠서 정리해야 한다.
<br> 기능별 일관성... 정돈된 느낌이 필요...!!! <br>


1. [x] 사용자가 입력하는 구입금액에 대한 타입 정의 (Long으로 설정한 것을 Money라는 자체 타입으로 바꾸는 것을 고려해본다.) <br>


2. [x] validation 기능에 대한 클래스 분화가 필요하다. <br>


3. [ ] 더불어서 예외처리를 메서드별로 발생시킬 때 try catch를 개별적으로 사용하는 방식에 대한 고려가 필요하다.<br>


4. [x] setup과 rule은 같은 enum 클래스로서 기능적인 중복이 있다. <br>


5. [x] calculation 클래스는 너무 크다. 기능적인 분화를 고려해본다. <br>


6. [ ] 통계를 매기는 과정에서 일정 부분이 하드코딩 되어 있다. enum 클래스를 좀 더 효율적으로 사용하는 방법을 고민해본다.<br>


7. [ ] 마찬가지로 출력클래스도 상수들이 집약되어 있는데 효율적인 방식이 맞는지 고민해본다.<br>


8. [ ] 기능별 테스트 코드 작성 필요
<br>

v2 release

---

추가 리팩토링 to do

0. [x] 예외처리를 메서드별로 발생시킬 때 try catch를 개별적으로 사용하는 방식에 대한 고려가 필요하다.<br>
=> 내부적으로 try and catch를 해버리면 프로그램이 종료가 되지 않고 다음 단계로 진행이 되는 문제가 발생. 
=> Controller에서 처리하는 것으로 결론 
=> 내부 Exception 클래스(Handler와 분야별 Exception) 생성하여 기능 분리

1. [ ] 통계를 매기는 과정에서 일정 부분이 하드코딩 되어 있어 enum 클래스를 좀 더 효율적으로 사용하는 방법을 고민해본다.<br>
=> Enum 클래스 통합으로 호출 방식 간소화
=> 카운팅 로직 전체에 대한 더 좋은 방식으로의 개선 고려 

2. [x] 출력클래스도 상수들이 집약되어 있는데 효율적인 방식이 맞는지 고민해본다.<br>
=> Error(Exception) 관련 메시지들은 별도의 Exception 처리 클래스에서 관리

3. [x] 기능별 테스트 코드 작성 필요.


쟁점 사항 
- Exception 처리를 위한 Validation을 한 곳에서 처리하도록 기능을 집약했다. 그런데 이때의 문제점은 여러 군데에서 호출을 받는다는 것이다. 이럼으로써 생성자를 각각 매번 호출을 시키던지 static으로 변환해서 사용하던지 결정을 해야 하는 상황에 놓인다. <br>
현재의 상황에서는 static 처리를 했다. (e.g ConsolInput -> readLine -> Validator.validateInputNotEmpty)
- LottoMachine에서 발생하는 오류룰 IllegalArgument에러로 볼것인지의 문제가 있다. 객체의 현 상태가 메서드 호출을 처리하기 적절지 않다는 의미에서 IllegalStateException을 고려했다.<br>
=> 인자의 값이 적절하지 않다는 의미에서 IllegalArgumentException 고수. 
- Controller에서 예외를 잡아줄 때 IllegalArgumentException으로 특정 짓는 것이 맞는가의 문제가 있다. 지정한 오류는 전부 해당 에러가 맞지만, 특정하지 않은 (내가 모르는) 오류가 발생할 경우 이를 캐치하고 메시지를 보려고 한다면 exception 전체를 잡아야 하는 것인가 하는 의문이 든다. <br>
=> 그렇다 하더라도 현재는 메시지를 발생시키지는 않고 있으므로 IllegalArgumentException을 잡는 것으로 고수. (test셋에서도 IllegalArgumentException으로 잡고 있음)
- 객체화에 대한 일말의 깨달음이 있어 lotto, lottotickets, TotalProfit, TotalRank를 객체화 하도록 재구성했다. 
- result 부분에서 rank를 종합하는 로직이 난해하다. <br>
  - 기본 플로우는 컴퓨터 생성 로또 번호와 유저가 입력한 당첨 번호를 비교하여 match를 찾는다. 
  - 그리고 매치에 맞는 상금을 enum 클래스에서 가져온다. 
  - 그로부터 최종 상금을 구하고 수익률을 계산한다.
  - 출력 형식에 맞는 메시지로 구성한다.
  - 출력한다. 
- 이 과정에서 계산 부분은 Calculator가 담당하고 자료는 명사형 클래스(TotalRank, TotalProfit)이 보유한다. 계산한 값을 용이하게 보유한다는 의미에서 static으로 사용했다.
- 4개의 규칙적인 rank와 1개의 special rank(2등)를 계산하는 방식에서 하드코딩이 불가피했다. 이 로직을 짜는 과정에서 한계를 많이 느꼈는데 개선 과제로 남겨둔다.

<br>
v3 release

---

git merge renechoi3 -> renechoi

</details>

<br>
