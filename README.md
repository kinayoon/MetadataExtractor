# MetadataExtractor
### for 'Music Finder' Project - <https://github.com/kinayoon/Music_Streaming_and_Dowmload>


+ mp3파일에서 메타데이터를 파싱하여 엑셀파일을 만들고, 파싱한 데이터를 DB에 저장하는 코드입니다. 

+ 이 Repository의 소스코드는 Spring Framework을 이용한 웹 어플리케이션 프로젝트 'Music Finder'
  (https://github.com/kinayoon/Music_Streaming_and_Dowmload) 에게 필요한 데이터를 공급하기 위한 역할로서 만들어졌습니다.
  사용자와의 인터랙션이 있는 'Music Finder'와는 사용 목적이 다르기 때문에 별도로 분리했습니다.


## Table of Contents
   1. Features
   2. Development Environment 
   3. Architecture
  
## 1. Features
  - 모든 데이터를 담고 있는 최상위 디렉토리에서 재귀함수를 사용하여 각 파일을 찾아낸 후,
   - mp3파일에 담긴 메타데이터를 파싱합니다.
   - 파싱한 데이터는 엑셀파일로 만들어지고,
   - 이 데이터는 그대로 MySQL에 삽입됩니다.
   - 이렇게 DB에 저장된 데이터는 'Music Finder' 웹 어플리케이션에서 사용됩니다.
  
## 2.Development Environment
   - Intellij Community Edition (ver.2016.2.4)
   - Window7 (64 bit)
   - Java SE(Standard Edition)
   - Java 1.8
   - MySQL 5.1.60
   
   - Using External Libraries
     - dom4j-1.6
     - mybatis-3.4.1
     - mysql-connector-java-5.1.40-bin
     - poi-3.15
     - tika-core-1.8  

## 3. Architecture
  ![](https://cloud.githubusercontent.com/assets/21224368/20290154/ac2fab32-ab21-11e6-8ed1-c410e6bf7d88.jpg)
  ![](https://cloud.githubusercontent.com/assets/21224368/20290356/e53d941a-ab22-11e6-8244-ee3666d9ff24.JPG)
