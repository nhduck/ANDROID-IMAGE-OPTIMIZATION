# Android Image Optimization (Đề tài 02)

Ứng dụng danh sách sản phẩm dùng để thực nghiệm tối ưu tải, cache và hiển thị ảnh.
Phần này: giao diện và dữ liệu. Phần tải ảnh bằng Coil đang ch bạn Duck dễ thương tố tính làm.

## Chức năng hiện có
- Danh sách sản phẩm (RecyclerView) cuộn được, chọn 100 / 500 / 1.000 mục từ menu.
- Bấm một mục để mở màn hình chi tiết (ảnh lớn, tên, mô tả).
- Placeholder (`ic_placeholder`) và ảnh lỗi (`ic_error`) dùng chung cho mọi nhánh.
- Nút Clear cache trong menu (đã có giao diện, chưa có logic).
- Chưa tải ảnh thật: tất cả ảnh hiện đang là placeholder.

## Cấu trúc mã nguồn
```
app/src/main/
  assets/products.json            Dữ liệu 1.000 sản phẩm
  java/.../
    MainActivity.kt               Danh sách, menu chọn số lượng, Clear cache
    DetailActivity.kt             Màn hình chi tiết
    model/Product.kt              id, name, description, thumbUrl, imageUrl
    data/ProductRepository.kt     Đọc products.json, getProducts(count), getProductById(id)
    ui/ProductAdapter.kt          Adapter của danh sách (ListAdapter + DiffUtil)
    ui/ImageLoading.kt            loadImage(url), clearImageCache(context), ImagePlaceholders
  res/layout/                     activity_main, item_product, activity_detail
  res/menu/menu_main.xml          Menu chọn 100/500/1000 và Clear cache
  res/values/dimens.xml           Kích thước ảnh, khoảng cách, cỡ chữ
src/test/.../GenerateProductsTest.kt   Công cụ sinh products.json
```

## Build và chạy
1. Mở project bằng Android Studio, chờ Gradle sync.
2. Chọn emulator, bấm Run.

## Đổi nguồn dữ liệu / nguồn ảnh
`products.json` được sinh bởi `GenerateProductsTest`.
1. Sửa `baseUrl` (hiện là `https://picsum.photos/seed`), ví dụ `http://10.0.2.2:8000` khi dùng server local.
2. Nếu cấu trúc đường dẫn ảnh khác, sửa hai dòng tạo `thumbUrl` và `imageUrl`.
3. Gỡ dòng `@Ignore` (nếu có), chạy test `generateProductsJson`, rồi thêm lại `@Ignore`.

## Điểm cắm cho Coil 
Chỉ sửa file `ui/ImageLoading.kt`, không cần sửa Adapter hay Activity:
- `fun ImageView.loadImage(url: String)`: thay thân hàm bằng Coil (bất đồng bộ, cache RAM + disk, resize, timeout, hủy request). Dùng `ImagePlaceholders.loading` và `ImagePlaceholders.error` cho placeholder và ảnh lỗi.
- `fun clearImageCache(context: Context)`: xóa memory cache và disk cache.
- Hai nơi gọi `loadImage`: `ProductAdapter` dùng `thumbUrl` (ảnh nhỏ), `DetailActivity` dùng `imageUrl` (ảnh lớn).

## Quy ước chung (giữ cố định để so sánh trước/sau)
- Ảnh trong danh sách: khung cố định 96dp x 96dp (`item_image_size`), `centerCrop`.
- Ảnh trong chi tiết: cao 240dp (`detail_image_height`), `centerCrop`.
- Hai nhánh dùng chung `products.json`, giao diện và placeholder; chỉ khác tầng tải ảnh.
- Quyền INTERNET và `usesCleartextTraffic` đã bật để gọi server local `http://10.0.2.2:8000`
  (chỉ phục vụ thực nghiệm, bản thực tế nên dùng HTTPS).