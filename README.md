# Hotel Management (Admin Panel) 🏨

## :bowtie: Introduction :bowtie:

The **Hotel Management** is a web application designed for comprehensive management of reservations, rooms, employees, and reviews within a hotel setting. It is built on a stack featuring Spring Boot and a dynamic HTML/JavaScript (Chart.js) frontend, providing stable API endpoints for standard CRUD operations and statistical reporting.

## :person_fencing: Key Features:
### 1. Reservation Management (Bookings) 📇
- _**CRUD Operations:**_ support for creating, reading, updating, and deleting bookings
- linking bookings to specific **_rooms_, _registered clients_, _assigned employees_**
- ability to **_book multiple rooms on one booking_**
- ability to either choose an **_existing client_** or **_add new client_** directly in adding new booking form
- _**total price**_ of the newly added booking _**is counted right on spot**_ (the total price of booking includes choosen meal plan and room)
- _**booking time is set automatically**_ based on the device's local date and time
- the system does not allow you to add/edit a booking for a given room if the room is already occupied on that date or if the date is incorrect (e.g. check in date is after check out date)

- _**filtering by:**_
  - Client (name surname/surname name)
  - Meal plan
  - Room (number (id))
  - Status
  - Check-in/Check-out date
### 2. Resource Management (Rooms & Meals) 🏘️ :plate_with_cutlery:
🏘️ <ins>**ROOMS:**<ins>
- definition of various room types (SINGLE, DOUBLE, SUITE) and status tracking.
- filtering by:
  - Room features
  - Room type
  - Price (asc/desc)
  - Availability

:plate_with_cutlery: <ins>**MEALS:**</ins>
- management of available meal plan options and pricing for each dish (breakfast/lunch/dinner)
- filtering by:
  - Meal plan
  - Price
### 3. Reporting and Statistics 📈
This feature relies on JPQL queries and Chart.js visualizations (using async/await for clean data fetching).
- **Statistical charts:**
  - Employee Percentage by Job title (Pie chart).
  - Room Popularity (Bar chart, detailing bookings per specific room ID(NR)).
  - Monthly Booking Volume (Line chart).
- **Data filtering:**
  - Searching for employees by salary (greater than/less than a specified value).

## 📂 Architectural Structure: 
The project follows a standard layered architecture with a minor simplification in the reporting layer:
- **Controller (API layer):** handles HTTP requests and returns JSON responses.
- **Service (business logic layer):** contains the core logic for transactional operations (e.g. CRUD).
- **Repository (data access layer):** uses Spring Data JPA for database communication.

**Note:** For simple statistical queries (Reports), the Controller calls the Repository directly, bypassing the Service layer. This decision minimizes boilerplate code as no business validation or complex manipulation is required.

## 👩🏼‍💻 Tech Stack:
**Backend:** Java 21+, Maven

**Framework:** Spring Boot

**Database:** H2 Database

**Persistence:** Spring Data JPA (JPQL, Query methods)

**Frontend:** "HTML5, CSS (Bootstrap 5)"

**Visualization:** JavaScript (Chart.js), dynamic visualization of data and statistics.

## 🛠️ Possible Future Extensions:
- more reports, statistics of various types
- _**employee module**_ - management of employee records, CRUD operations
- _**client's perspective of the website**_ -> full hotel website, where reviews on the admin site would be pulled from 'real' client submissions rather than only displayed as a sample view

## 🚀 How to Run the Project:
1. Clone the repository:
   
   ```git clone https://github.com/yola512/HotelManagement.git```
3. Navigate to the directory:

   ```cd HotelManagement```
5. Run the project:

   ```mvn spring-boot:run```
   
The application runs on port 8080 by default. The homepage can be accessed at: http://localhost:8080/home

## 📑 Sample Data Disclaimer:
If you wish to use sample data, run the project with the with the ```SampleDataLoader``` class uncommented. Once the .db file has been created and populated, it is reccomended to comment out the entire ```SampleDataLoader``` class before running the project again. This ensures that any changes you make on page are not overwritten :)

## 👩🏼‍⚖️ Image Disclaimer:
All background pictures used in the project come from ```pexels.com``` which offers free stock photos and videos.
