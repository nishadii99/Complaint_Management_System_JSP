# Complaint Management System (CMS)

This is a Web-Based Complaint Management System developed as part of the **IJSE GDSE Advanced API Development Individual Assignment - June 2025**. The application allows employees to submit complaints and admins to manage them. This system follows the **MVC architecture** and uses **JSP** for the view layer, **Servlets** for controllers, and **DAO classes** for database interactions.

---

## 📚 Technologies Used

- **Frontend:** JSP, HTML, CSS, JavaScript *(only for form validation)*
- **Backend:** Jakarta EE (Servlets)
- **Database:** MySQL
- **Connection Pooling:** Apache Commons DBCP
- **Web Server:** Apache Tomcat
- **Architecture:** MVC (Model-View-Controller)

---

## 📁 Project Structure

```
Complaint-Management-System/
├── src/
│   ├── lk/ijse/controller/     -> Servlets
│   ├── lk/ijse/dao/            -> DAO classes
│   ├── lk/ijse/model/          -> JavaBeans (POJOs)
│
├── web/
│   ├── jsp/                    -> JSP Views (login.jsp, employeedashboard.jsp, admindashboard.jsp)
│   ├── css/                    -> CSS stylesheets
│   ├── js/                     -> JavaScript for form validation
│
├── db/
│   └── schema.sql              -> MySQL schema dump file
│
├── README.md
```

---



3.**Configure DBCP in `context.xml`**
   Inside `META-INF/context.xml`, add:

   ```xml
   <Resource name="jdbc/cms"
             auth="Container"
             type="javax.sql.DataSource"
             maxTotal="100"
             maxIdle="30"
             maxWaitMillis="10000"
             username="root"
             password="Ijse@1234"
             driverClassName="com.mysql.cj.jdbc.Driver"
             url="jdbc:mysql://localhost:3306/cms"/>
   ```

4.**Deploy on Tomcat**
    - Use Apache Tomcat 10 or compatible
    

5.**Run the App**

   ```
   http://localhost:8080/Complaint-Management-System/
   ```

---

## 👥 System Features

### 👩‍💼 Employee
- Login with form
- Submit a new complaint
- View own complaints
- Edit/Delete complaints (only if status is not resolved)

### 👨‍💼 Admin
- View all complaints
- Update status and remarks
- Delete any complaint

✅ All actions performed using **HTML form GET/POST**  
🚫 No AJAX, Fetch, or XHR used

---



## 🗄️ Database Schema

SQL Dump File: `[Complaint_Management_System_DB.sql.`





## 👨‍💻 Developer Information

- **Name:** Nishadi Kavindya
- **Batch:** IJSE GDSE 72
- **Branch:** Galle

---

## 📌 Assignment Rules Followed

- ❌ No use of AJAX/fetch/XHR
- ✅ Strictly MVC architecture
- ✅ Only HTTP form-based GET/POST used
- ✅ MySQL + DBCP for backend integration

---
