# IoT MQTT Controller (Android + ESP8266)

IoT MQTT Controller adalah aplikasi Android untuk mengontrol perangkat IoT berbasis **ESP8266 (Wemos D1 Mini)** menggunakan protokol **MQTT**.  
Aplikasi ini memungkinkan pengguna menyalakan dan mematikan LED secara real-time serta menampilkan status koneksi perangkat IoT.

---

## 📱 Fitur Aplikasi Android
- Koneksi ke MQTT Broker
- Indikator status IoT (**CONNECTED / DISCONNECTED**)
- Kontrol LED Hijau
- Kontrol LED Merah
- Tombol **ALL OFF**
- Sinkronisasi status LED dari ESP8266 (real-time)
- UI berbasis **Material Design**

---

## 🔌 Fitur ESP8266
- Koneksi WiFi otomatis
- Koneksi MQTT dengan autentikasi
- Last Will Message (LWT) → status OFFLINE
- Publish status ONLINE saat terkoneksi
- Publish status LED (HIJAU / MERAH / ALL OFF)
- Auto reconnect WiFi & MQTT

---

## 🛠 Teknologi yang Digunakan

### Android
- Java
- Android Studio
- Eclipse Paho MQTT Client
- Material Components

### IoT
- ESP8266 (Wemos D1 Mini)
- Arduino Framework
- PubSubClient
- MQTT Broker

---

## 🧩 Arsitektur Sistem

```yaml
Android App
  |
  |
  MQTT (Publish / Subscribe)
  |
  MQTT Broker
  |
  |
  ESP8266 (Wemos D1 Mini)
  |
  |
  GPIO
  |
  LED Hijau & LED Merah
```

---

## 📡 Topik MQTT

| Fungsi | Topic | Payload |
|------|------|---------|
| Kontrol LED | `esp8266/led` | `ON Hijau`, `ON Merah`, `OFF ALL` |
| Status IoT | `esp8266/status` | `ONLINE`, `OFFLINE` |
| Status LED | `esp8266/state` | `HIJAU_ON`, `MERAH_ON`, `ALL_OFF` |

---

## 🚀 Cara Menjalankan Project

### 1️⃣ MQTT Broker
Pastikan MQTT Broker sudah berjalan dan dapat diakses oleh Android dan ESP8266.

Contoh:

tcp://154.19.37.27:1883

---

### 2️⃣ ESP8266 (Wemos D1 Mini)
1. Buka project ESP8266 di Arduino IDE
2. Install library:
    - ESP8266WiFi
    - PubSubClient
3. Sesuaikan konfigurasi WiFi & MQTT
4. Upload ke board

```cpp
const char *ssid = "NAMA_WIFI";
const char *password = "PASSWORD_WIFI";
```
---

### 3️⃣ Android App
1. Buka project di Android Studio 
2. Sync Gradle 
3. Pastikan dependency MQTT tersedia 
4. Jalankan aplikasi di Emulator atau HP Android
---

# 📸 Screenshot

---

# ⚠ Catatan Penting
- ESP8266 tidak mendukung WiFi 5GHz, gunakan WiFi 2.4GHz 
- Topic MQTT Android dan ESP harus sama 
- Gunakan retain message untuk sinkronisasi status 
- Pastikan broker dapat diakses publik / lokal
---

# 📂 Struktur Project
```css
IoTMqttController
├── app
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   ├── res
│   │   │   └── AndroidManifest.xml
│   └── build.gradle
├── gradle
├── build.gradle
├── settings.gradle
└── README.md

```
---

# 📄 Lisensi

Project ini dibuat untuk keperluan pembelajaran dan pengembangan IoT. 
Bebas digunakan dan dikembangkan lebih lanjut.

---

# 👤 Author
**Muhamad Alwan Fadhlurrohman**

Android & IoT Developer

---

# ⭐ Penutup
Jika project ini membantu, jangan lupa ⭐ repository ini di GitHub!

---