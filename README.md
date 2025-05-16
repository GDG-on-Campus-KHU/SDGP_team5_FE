<h1 align="center">🚨 ResQ 🚨</h1>

  
<div align="center">
  <h3><b><i>Instant support for your travel emergencies!</i></b></h3>
  <h4>🌐 This is the <code>English</code> version of the README. | <a href="README.ko.md">한국어 버전</a></h4>
</div>

## 🎯 Goal

<pre><code><b>Unexpected emergencies</b> can happen anywhere and at any time.<br>

<b>ResQ</b> aims to be a <b>reliable companion</b> that protects your <b>life</b> and <b>safety</b> in such moments.</code></pre>

<div align="center">
  <img src="https://drive.google.com/uc?id=1wOQnC2JamqkYSGTIdRcSwDGFH92ATsjX" width=60%>
</div>

---

### 💡 Key Features
<h4> 🆘 Quickly request help in emergency situations</h4>

<pre><code>* <b>👆One tap</b> to connect to an <b>emergency hotline</b> and start <b>recording</b> the situation
* Provides <b>guidelines</b> on how to handle common <b>emergency situations</b></code></pre>

---


#### 📌 Basic Features

- Log in with **Google** account

- **Emergency response guidelines** for each situation -- **`🌐ko(Korean)`**, **`🌐en(English)`**

- Add **favorite** emergency responses

- **Search** for emergency responses that are not displayed on the main screen

---

#### 📌 Medical Information Management

- Fill out **medical information** such as **allergies**, **medications**, etc. for emergencies

- Set **destination country** -- 🌐 `KR`, `US`, `GB`, `JP`, `CN`, `DE`, `FR`, `MX`

- Add family and friends who are traveling with you to the **group**

- **Translate medical information** of the group members into the **language** of the destination country

- Convert **height** and **weight** units based on the **destination country's** measurement system

---

#### 📌 Situation Recording

- Automatically save **audio files** recorded during emergencies

- Save situation recordings as **text** using the **`Speech-to-Text`** API

---

<br>

## 🧩 Project Architecture

<div align="center">
  <img src="https://drive.google.com/uc?id=1WH0xObPY-U4_opNcNv_3qroycj5ra3ev" width=80%>
</div>

<br>

## ⚙️ Tech Stacks
| Component      | Stack              |
|----------------|--------------------|
| Architecture   | MVVM               |
| UI             | Jetpack Compose    |
| Asynchronous   | Coroutine          |
| Network        | Retrofit2, Okhttp3 |
| etc            | MediaRecorder      |

<br>

## 📂 Project Structure

```
.
├── app/
│   ├── manifests/
│   ├── kotlin+java/
│   │   └── com/example/resq/
│   │       ├── navigation/     
│   │       ├── network/        
│   │       ├── presentaion/    
│   │       │   ├── component/
│   │       │   ├── resq/
|   |       |   ├── room/
|   |       |   ├── sign/
│   │       │   ├── user/
│   │       │   └── ...         
│   │       ├── service/        
│   │       └── ui/             
│   ├── MainActivity.kt
│   └── ResQApp.kt
├── build.gradle.kts
├── gradle.properties
├── README.md
└── settings.gradle.kts
```

<br>

## ▶️ How to Run

```
https://drive.google.com/drive/folders/1z3-TDW1zmh7yXzowaQMQVtV6B4R-zbfA?usp=share_link
```

<br>


## 👥 Team Members

| Name       | English Name   | Role     | GitHub                                                |
|------------|----------------|----------|-------------------------------------------------------|
| 권동현      | DongHyeon Gwon | Mobile   | [GwonDongHyeon21](https://github.com/GwonDongHyeon21) |
| 김민        | Min Kim        | Backend | [kmin1231](https://github.com/kmin1231) |
| 김태훈      | Taehoon Kim    | Mobile  | [taeh-kim](https://github.com/taeh-kim) |
| 박상영      | SangYeong Park | Backend | [Imsyp](https://github.com/Imsyp) |
