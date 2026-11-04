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
## Hướng dẫn sử dụng script đo (thư mục `D6_scripts`)

Bộ script PowerShell đo TC01 đến TC07 cho hai nhánh (`baseline` và `optimized`) bằng `adb` trên emulator
(đã thử với Pixel 6 API 35). Mỗi script lưu minh chứng vào `D6_evidence/TCxx/<nhánh>/` và in ra các dòng
TSV để dán thẳng vào Google Sheet.

### 1. Chuẩn bị mỗi lần mở cửa sổ PowerShell mới

Chạy từ **thư mục gốc dự án** (nơi có `D6_scripts` và `D6_evidence`). Thêm `adb` vào PATH và kiểm tra thiết bị:

```powershell
$env:Path += ";$env:LOCALAPPDATA\Android\Sdk\platform-tools"
adb devices
```

Phải thấy emulator ở trạng thái `device`. Nếu là `offline` thì chạy `adb kill-server; adb start-server; adb devices`.
Nếu máy chặn script, chạy thêm hai lệnh sau (chỉ có tác dụng trong cửa sổ hiện tại):

```powershell
Set-ExecutionPolicy -Scope Process Bypass
Get-ChildItem .\D6_scripts -Recurse -Filter *.ps1 | Unblock-File
```

### 2. Cấu trúc thư mục

```
D6_scripts/
  common.ps1                          hàm dùng chung (adb, đọc log, meminfo, gfxinfo...)
  tools/
    dataset.ps1                       đổi bộ dữ liệu products.json (normal | large | errors | restore)
    check_log.ps1                     kiểm tra bản app đang cài có ghi log IMG không
  TC01_cold_load/TC01_cold_load.ps1
  TC02_hot_warm_cache/TC02_hot_warm_cache.ps1
  TC03_fast_scrolling/TC03_fast_scrolling.ps1
  TC04_large_images/TC04_large_images.ps1
  TC05_offline_mode/TC05_offline_mode.ps1
  TC06_error_urls/TC06_error_urls.ps1   và slow_server.py
  TC07_scale_100_500_1000/TC07_scale.ps1
```

### 3. Quy trình đo cho mỗi nhánh

1. `git checkout` đúng nhánh (`baseline/no-coil` hoặc `optimized/coil`).
2. Trong Android Studio chọn cấu hình **`app`** (không phải test), bấm **Run** để cài lại app. Không dùng Debug.
3. Chạy `.\D6_scripts\tools\check_log.ps1`. Phải thấy các dòng `D/IMG ... OK source=... ms=... url=...`.
   Nếu không có dòng nào, bản đang cài chưa có `Log.d("IMG", ...)` trong `ImageLoading.kt`.
4. Đổi bộ dữ liệu nếu TC cần (mục 4), bấm Run lại để cài, rồi chạy script của TC.
5. Chạy script **bằng cách gọi file**, không dán nội dung script vào terminal.

Nhánh baseline cần có đoạn ghi log `IMG` trong `ImageLoading.kt` và nên **commit** để không bị mất khi đổi nhánh.

### 4. Lệnh chạy từng TC và bộ dữ liệu cần dùng

Thay `optimized` bằng `baseline` khi đo nhánh còn lại. Thêm `-Runs 1` để chạy thử một lần trước.

| TC | Nội dung | Dữ liệu (`tools\dataset.ps1 -Mode`) | Lệnh |
|---|---|---|---|
| TC01 | Cold load | `normal` | `.\D6_scripts\TC01_cold_load\TC01_cold_load.ps1 -Branch optimized` |
| TC02 | Hot (a) / Warm (b) | `normal` | `.\D6_scripts\TC02_hot_warm_cache\TC02_hot_warm_cache.ps1 -Branch optimized` |
| TC03 | Cuộn nhanh | `normal` | `.\D6_scripts\TC03_fast_scrolling\TC03_fast_scrolling.ps1 -Branch optimized` |
| TC07 | 100 / 500 / 1.000 ảnh | `normal` | `.\D6_scripts\TC07_scale_100_500_1000\TC07_scale.ps1 -Branch optimized` |
| TC04 | Ảnh 3000x2000 | `large` | `.\D6_scripts\TC04_large_images\TC04_large_images.ps1 -Branch optimized` |
| TC06 | URL lỗi / server chậm | `errors` (kèm `slow_server.py`) | `.\D6_scripts\TC06_error_urls\TC06_error_urls.ps1 -Branch optimized` |

Đổi bộ dữ liệu (nhớ bấm **Run** app để cài lại sau mỗi lần đổi, vì `products.json` nằm trong assets):

```powershell
.\D6_scripts\tools\dataset.ps1 -Mode normal    # ảnh 300/200 (TC01, 02, 03, 05, 07)
.\D6_scripts\tools\dataset.ps1 -Mode large     # ảnh 3000/2000 (TC04)
.\D6_scripts\tools\dataset.ps1 -Mode errors    # 5 mục đầu là URL lỗi/chậm (TC06), tự sao lưu
.\D6_scripts\tools\dataset.ps1 -Mode restore   # khôi phục sau khi đo TC06
```

Thứ tự gợi ý: làm hết nhóm `normal` cho một nhánh, rồi TC04 (`large`), cuối cùng TC06 (`errors`).

**Riêng TC06:** mở một cửa sổ terminal khác và giữ chạy `python .\D6_scripts\TC06_error_urls\slow_server.py`
trong lúc đo (lần đầu Windows hỏi tường lửa thì chọn Allow access). `usesCleartextTraffic` đã bật sẵn trong
`AndroidManifest.xml`, cần giữ nguyên ở cả hai nhánh.

### 5. Kết quả

- Minh chứng (log, meminfo, gfxinfo, ảnh chụp màn hình, file crash) nằm trong `D6_evidence/TCxx/<nhánh>/`.
- Cuối mỗi lần chạy, script in các dòng TSV (các cột cách nhau bằng Tab): chọn và copy rồi dán vào ô tương ứng trong sheet.
- Số trong bảng là trung vị của 5 run. Run 0 (khởi động) ở TC02 và TC03 được chạy nhưng không đưa vào kết quả.
- Script ghi đè file minh chứng cùng tên. Muốn giữ dữ liệu cũ, đổi tên thư mục trước khi chạy lại, ví dụ
  `Rename-Item D6_evidence\TC01\baseline baseline_old`.
- Cột "Nhầm ảnh SP" của TC03 phải xem bằng mắt qua các file `_screenshot.png`. Với TC06, xem `_screenshot.png`
  để xác nhận 5 mục đầu hiện `ic_error`.

### 6. Điều kiện để so sánh công bằng

- Hai nhánh phải dùng cùng thông số (tọa độ vuốt, số lần vuốt, thời gian chờ) và cùng bộ dữ liệu.
  Muốn đổi thông số thì đo lại cả hai nhánh.
- Thời gian `ms` trong log tính từ một mốc chung của cả đợt tải nên có cả thời gian xếp hàng.
  Thời gian tải còn dao động theo mạng (`picsum.photos`), nên dựa vào trung vị và ghi chú nhiễu khi báo cáo.
- TC04: chỉ số chính là PSS (bitmap nằm ở bộ nhớ native), không phải Java Heap hay thời gian tải.
- TC05 là script mới, nên chạy thử `-Runs 1` trước. Script luôn bật lại mạng khi kết thúc.

### 7. Lỗi thường gặp

| Hiện tượng | Nguyên nhân và cách xử lý |
|---|---|
| `Ensure-Device`, `Get-Median`... "not recognized" | Đã dán nội dung script vào terminal nên không nạp được `common.ps1`. Hãy chạy bằng lệnh gọi file như bảng ở mục 4. |
| `adb` không được nhận ra | Chạy lại lệnh thêm PATH ở mục 1 trong cửa sổ PowerShell hiện tại. |
| `device offline` hoặc `waiting for device` | `adb kill-server; adb start-server; adb devices`. Nếu vẫn lỗi thì Cold Boot emulator. |
| Script báo `ảnh=0` hoặc không có dòng OK | App đang cài không ghi log `IMG`. Chạy `check_log.ps1`, kiểm tra `ImageLoading.kt`, chọn cấu hình `app` rồi Run lại. |
| Đổi `products.json` nhưng app vẫn dùng dữ liệu cũ | Chưa cài lại app. Chọn cấu hình `app` (không phải test) và bấm Run. |
| TC06: thời gian "server chậm" quá ngắn | `slow_server.py` chưa chạy, hoặc thiếu `usesCleartextTraffic`, hoặc tường lửa chặn Python. |
| Script bị chặn "running scripts is disabled" | Chạy `Set-ExecutionPolicy -Scope Process Bypass` rồi `Unblock-File` như mục 1. |
