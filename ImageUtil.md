- **Ví dụ trong `BookPanel.java`**:

  - Hiển thị ảnh trong bảng.

  ```java
  bookTable.getColumnModel().getColumn(1).setCellRenderer(ImageUtils.getImageRenderer());
  ImageIcon coverIcon = ImageUtils.loadImage("/book_covers/" + book.getCover(), 80, 105);
  tableModel.addRow(new Object[]{..., coverIcon, ...});
  ```

- **Ví dụ trong `BookPanel.java`**:

  - Hiển thị ảnh trong label.

  ```java
  ImageIcon coverIcon = ImageUtils.loadImage("/book_covers/" + book.getCover(), 180, 240);
  previewLabel.setIcon(coverIcon);
  ```

- **Ví dụ trong `BookPanel.java`**:
  - Lưu ảnh vào thư mục resource.
  ```java
  File sourceFile = new File(coverFieldStr); // coverFieldStr là đường dẫn tệp ảnh
  String storedCoverPath = ImageUtils.saveImage(sourceFile);
  ```

### Lưu ý khi sử dụng

- ImageUtils.loadImage() đường dẫn tệp tính từ "src/main/resources"
  - Ví dụ `ImageUtils.loadImage("/book_covers/a.jpg", 100, 100);` thì đường dẫn tệp là "src/main/resources/book_covers/a.jpg"
- ImageUtils.saveImage() lưu ảnh vào thư mục "src/main/resources/book_covers"
  - Ví dụ `ImageUtils.saveImage(sourceFile);` thì ảnh sẽ được lưu vào "src/main/resources/book_covers"
