# homework-book-management

#Api get list book (sắp xếp theo title)
```
GET: http://localhost:8080/books
```

<img alt="Alt text" height="200" src="/img/Screenshot from 2021-11-15 09-01-13.png?raw=true" title="get list book" width="100"/>

#Api search by title (tìm kiếm gần đúng)
```
GET: http://localhost:8080/books?search=string
```
<img alt="Alt text" height="200" src="/img/Screenshot from 2021-11-15 09-07-11.png?raw=true" width="100"/>

#Api search by amount
```
GET: http://localhost:8080/books?amount=0
```
<img alt="Alt text" height="200" src="/img/Screenshot from 2021-11-15 09-15-53.png?raw=true" width="100"/>

#Api buy book
```
POST: http://localhost:8080/books
```
<img alt="Alt text" height="200" src="/img/Screenshot from 2021-11-15 09-23-31.png?raw=true" width="100"/>

```jsons
RequestBody: {String bookId, Integer amount}
```

1.Nếu ko nhập amount thì mặc định là 1

2.Nếu số lượng sách trong kho không đủ thì ném ra lỗi 

# Yêu cầu khác

1. Sinh ra 1000 quyển sách bằng Faker
2. Hàm kiểm tra số lượng sách 1p chạy 1 lần, nếu nhỏ hơn hoặc bằng 1 thì + thêm 5
3. Annotation @Benchmark tính thời gian thực hiện 1 request

# Khó khăn

1. Chưa xử lý được phần event