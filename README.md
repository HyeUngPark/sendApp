# SendAppServer

## 소개
```
retrofit을 이용해서 Android 앱에서 웹 어플리케이션에 대기번호를 전송하는 앱 입니다.

프로젝트 기간 : 2019.03.13 ~ 2020.03.16
프로젝트 인원 : 1인
```
![cafe2](https://user-images.githubusercontent.com/45528487/78789706-77f2dd00-79e8-11ea-8713-bc39572cad48.jpg)
![app](https://user-images.githubusercontent.com/45528487/78789700-76291980-79e8-11ea-95dd-87fdf4b8b48c.jpg)

## 목표

```
1. RestAPI를 활용한 통신
2. Android 기본 이벤트 학습
```
## 개발 및 운영 환경

```
- jdk : 1.8
- gradle:3.6.1
- compileSdkVersion : 29
- buildToolsVersion : 29.0.3
- minSdkVersion : 16
- targetSdkVersion : 29
- 개발 tool : Android Studio 3.6.1
- 형상관리 : Git / Github
```

## 프로젝트 트리
```
┌── .idea
│   ├── codeStyles
│   ├── └── Project.xml
│   ├── gradle.xml
│   ├── misc.xml
│   ├── runConfigurations.xml
│   ├── vcs.xml
├── app
│   ├── src
│   ├── ├── java/com/hyeung/sendapp
│   ├── ├── ├── MainActivity.java
│   ├── ├── ├── RequestHttpConnection.java
│   ├── ├── ├── RetrofitRepo.java
│   ├── ├── └── RetrofitService.java
│   ├── ├── res 
│   ├── ├── └── ...
│   ├── ├── AndroidManifest.xml
│   ├── ├── ic_launcher_clock-playstore.png
│   ├── ├── .gitignore
│   ├── ├── build.gradle
│   ├── └── proguard-ruls.pro
├── gradle/wrapper
│   ├── gradle-wrapper.jar
│   ├── gradle-wrapper.properties
├── .gitignore
├── README.md
├── build.gradle
├── gradle.properties
├── gradlew.bat
└── settings.gradle
```

## 사용한 모듈

```
"retrofit" : "2.4.0"
```
