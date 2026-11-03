# 📱 Android Image Optimization

## 🔎 Giới thiệu

Ứng dụng minh họa danh sách sản phẩm có ảnh, màn hình chi tiết và khả năng cuộn qua nhiều mục. Dự án phục vụ việc tìm hiểu và đánh giá các kỹ thuật tải ảnh, cache, resize và hiển thị ảnh trên Android.

## 🌿 Các nhánh Git

Repository có hai nhánh để đối chiếu giữa phiên bản cơ sở và phiên bản tối ưu:

| Nhánh | Nội dung |
|---|---|
| `baseline/no-coil` | Phiên bản cơ sở, không sử dụng Coil để tải ảnh. |
| `optimized/coil` | Phiên bản tối ưu, sử dụng Coil để tải và quản lý ảnh. |

Có thể chọn nhánh trong giao diện GitHub hoặc chuyển nhánh bằng Terminal tại thư mục dự án:

```bash
git switch baseline/no-coil
```

Hoặc:

```bash
git switch optimized/coil
```

Sau khi chuyển nhánh, hãy sync Gradle rồi build hoặc chạy ứng dụng theo hướng dẫn bên dưới.

## ✨ Chức năng

- Hiển thị danh sách bằng `RecyclerView`.
- Chọn số lượng sản phẩm: **100, 500 hoặc 1.000**.
- Chạm vào sản phẩm để xem ảnh lớn, tên và mô tả.
- Hiển thị placeholder khi tải ảnh và ảnh thay thế khi xảy ra lỗi.
- Có mục xóa cache trong menu.
- Dữ liệu sản phẩm được lưu trong `products.json`, gồm URL ảnh thu nhỏ và URL ảnh chi tiết.

> **Trạng thái mã nguồn:** Trong bản ZIP hiện tại, giao diện danh sách, màn hình chi tiết và placeholder đã có; ảnh thật, Coil và chức năng xóa cache cần được hoàn thiện trước khi mô tả là đã hoạt động.

## 🗂️ Cấu trúc dự án

```text
app/src/main/
├── assets/
│   └── products.json              Dữ liệu 1.000 sản phẩm
├── java/.../
│   ├── MainActivity.kt             Danh sách, chọn số lượng và menu
│   ├── DetailActivity.kt           Màn hình chi tiết
│   ├── model/
│   │   └── Product.kt              Mô hình dữ liệu sản phẩm
│   ├── data/
│   │   └── ProductRepository.kt    Đọc và truy xuất dữ liệu
│   └── ui/
│       ├── ProductAdapter.kt        Adapter cho RecyclerView
│       └── ImageLoading.kt          Điểm đặt logic tải ảnh và cache
└── res/
    ├── layout/                      Các giao diện XML
    ├── menu/menu_main.xml           Menu chọn số lượng và xóa cache
    └── values/dimens.xml            Kích thước ảnh và khoảng cách
```

## 🧰 Yêu cầu

- Android Studio
- JDK tương thích với phiên bản Gradle của dự án
- Android Emulator hoặc thiết bị Android
- Kết nối Internet nếu tải ảnh từ URL công khai

## 🚀 Build và chạy ứng dụng

### Android Studio

1. Clone hoặc tải repository về máy.
2. Mở Android Studio, chọn **Open**, rồi chọn thư mục gốc của dự án.
3. Chờ **Gradle Sync** hoàn tất.
4. Chọn Android Emulator hoặc kết nối thiết bị Android.
5. Nhấn **Run ▶** để build, cài đặt và chạy ứng dụng.

### Dòng lệnh

Mở Terminal tại thư mục gốc của dự án.

**Windows:**

```bat
gradlew.bat assembleDebug
```

**macOS hoặc Linux:**

```bash
./gradlew assembleDebug
```

APK debug sau khi build thành công nằm tại:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Có thể cài APK lên thiết bị Android đã kết nối bằng:

**Windows:**

```bat
gradlew.bat installDebug
```

**macOS hoặc Linux:**

```bash
./gradlew installDebug
```

### Build APK bằng Android Studio

Chọn **Build > Build Bundle(s) / APK(s) > Build APK(s)**. Khi hoàn tất, nhấn **locate** trong thông báo để mở thư mục chứa APK.

## 🖼️ Dữ liệu và URL ảnh

Dữ liệu nằm tại `app/src/main/assets/products.json`. Mỗi sản phẩm có mã, tên, mô tả, `thumbUrl` (ảnh thu nhỏ) và `imageUrl` (ảnh chi tiết).

Dữ liệu được tạo bằng `GenerateProductsTest`. Khi đổi nguồn ảnh:

1. Sửa `baseUrl` và cách tạo `thumbUrl`, `imageUrl`.
2. Chạy công cụ sinh dữ liệu theo hướng dẫn trong mã nguồn.
3. Kiểm tra URL ảnh có thể truy cập từ emulator hoặc thiết bị thật.

Nếu máy chủ ảnh chạy trên máy tính và ứng dụng chạy trong Android Emulator, dùng `http://10.0.2.2:<cổng>` để truy cập máy tính. Trên thiết bị thật, thay bằng địa chỉ IP phù hợp trong cùng mạng.

## ⚙️ Tải ảnh và cache

Điểm đặt logic tải ảnh là `ui/ImageLoading.kt`:

| Thành phần | Vai trò |
|---|---|
| `ImageView.loadImage(url)` | Tải và hiển thị ảnh, placeholder, trạng thái lỗi và cấu hình resize/cache |
| `clearImageCache(context)` | Xóa cache ảnh |
| `ProductAdapter` | Dùng `thumbUrl` cho ảnh trong danh sách |
| `DetailActivity` | Dùng `imageUrl` cho ảnh ở màn hình chi tiết |

Kích thước hiển thị được giữ cố định để thuận tiện so sánh: ảnh danh sách **96dp × 96dp**, ảnh chi tiết cao **240dp**; cả hai dùng `centerCrop`.

## 🌐 Quyền truy cập mạng

Ứng dụng cần quyền `INTERNET` để tải ảnh từ URL. Nếu dùng máy chủ HTTP nội bộ cho thực nghiệm, cần bật cấu hình cho phép HTTP trong manifest. Khi triển khai thực tế, nên sử dụng HTTPS.
