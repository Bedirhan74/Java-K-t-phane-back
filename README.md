# 📚 Book Tracker - Spring Boot + MongoDB + Thymeleaf

Bu proje, kullanıcıların kitap ekleyip düzenleyebileceği basit bir kitap takip uygulamasıdır.  
Veriler MongoDB üzerinde saklanır, kullanıcı arayüzü Thymeleaf ile sağlanır.

---

## 🚀 Kullanılan Teknolojiler

- Java 17
- Spring Boot 3.2.x
- Spring Data MongoDB
- Thymeleaf
- Maven
- MongoDB

---

## 📦 Özellikler

- 📘 Kitap ekleme
- ✏️ Kitap düzenleme
- ❌ Kitap silme
- 📋 Listeleme (Thymeleaf ile)
- 🧠 MongoDB ObjectId otomatik oluşturma

---

## ⚙️ MongoDB Ayarı

MongoDB'nin localhost'ta çalışıyor olması gerekiyor.  
Kullanılan varsayılan URI şu şekildedir:

```
spring.data.mongodb.uri=mongodb://localhost:27017/booktracker
```

> Gerekirse bu URI'yi `src/main/resources/application.properties` içinden değiştirebilirsin.

---

## ▶️ Uygulama Nasıl Başlatılır?

### 1. Terminal Üzerinden

```bash
mvn clean install
mvn spring-boot:run
```

### 2. Veya VSCode/Eclipse Üzerinden

- Projeyi `Import as Maven Project` olarak içe aktar
- `BookTrackerApplication.java` (main class) üzerinden `Run` et

---

## 🔗 Uygulama Arayüzü

Uygulama başlatıldığında:  
🖥️ [http://localhost:8080/](http://localhost:8080/) adresinden ulaşılabilir.

---

## 📌 Geliştirme Notları

- Yeni kayıt eklenirken `id` alanı boş bırakılırsa MongoDB otomatik ObjectId oluşturur.
- Güncelleme işlemi için formda `id` hidden input olarak gönderilir.
- Thymeleaf üzerinden form validasyonları HTML5 ile sağlanmıştır.

---

## 🧑‍💻 Katkıda Bulunmak

PR'ler, öneriler ve katkılar memnuniyetle karşılanır!
