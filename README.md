# Notice-app
## 📄Short description
___Notice app___ - it is a java web application that use SpringBoot framework and JS (DHTMLX 8).
___
### 📑Full Description📑
Application can create user with request /register or login by /login.
Authorised user with "USER" role can create, read, edit, like, unlike notice.
Not authorised user can create, read notices.
Authorised user with "ADMIN" role can create new users (username/password), 
delete users, create, read, edit notices.

__!WARNING! -> for login as admin (username - admin/password - admin)__
___
### 🛠Available endpoints🛠
+ GET: /register - all - **create new user -><br /> [interface img](img/registerForm.jpg)**
+ GET: /login - all - **login**
+ POST/GET: /api/notices - all - **for create/read notices if you are not authorised**
+ GET: /notices - user/admin - **for get grid with all notices with ability to like/unlike, edit, add notice/s<br />**
[notices grid](img/notices.jpg)
+ GET: /users - admin - **for get grid with all users with ability to create, delete, edit (username only)<br />**
users grid -> [users grid](img/users.jpg)<br />
edit user -> [edit form](img/editUser.jpg)
___
_For add new notice or User press ADD NEW NOTICE/USER and fill all html fields, and press save_
___
### 🛠Underhood endpoints🛠
+ GET: /notices/like/{id} - user - **for like notice** <br />
+ GET: /notices/unlike/{id} - user - **for unlike notice** <br />
+ POST: /notices/edit - user/admin - **for edit notice**<br />
+ POST: /users/delete/{id} - admin - **for delete user**
+ POST: /users/edit - admin - **for edit user's username**
___
+ ### Technologies used
| Technology  | Version |
|-------------|--------|
| JDK         | 17     |
| Spring boot | 2.7.8  |
| Maven       | 4.0.0  |
| MongoDb     | latest |
___
### 💻🛠Local set up tutorial💻🛠
1. Download project
2. Upload dependencies
3. Create in MongoDB "notice_db" <br />
or set another db in application.properties in "spring.data.mongodb.database" field<br />
   <br />
__!WARNING!<br />
The Requested port is "27017" (default), you can change it in
   application.properties in "spring.data.mongodb.port" field
   <br /><br />__
4. Run NoticeAppApplication class;
5. You can use Postman with application/json Content-type if you want to request manually
___