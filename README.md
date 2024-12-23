---

### Readme.txt

**Student ID**: s3922087
**Student Name**: Kieu Cong Tam

---

#### **Functionality of the App**
1. **User Registration and Login**:
    - Users can register with their details (name, email, password, phone number, role, and blood type).
    - Secure login functionality with validation.

2. **Site Management**:
    - View a list of blood donation sites.
    - Filter sites by name, city, street, number of donors, approved donors, and blood collected.

3. **Site Details**:
    - View detailed information about a specific site, including its address, donors, and total blood collected.
    - Option to volunteer for a specific site.

4. **Map Integration**:
    - Display current location and donation site locations using Google Maps.
    - Search for donation sites near the user's location.
    - View the route from the current location to a selected donation site.

5. **Backend Integration**:
    - A backend service provides API endpoints for user registration, login, site filtering, and data fetching.

6. **Responsive UI**:
    - User-friendly and modern UI design.
    - Adjustable filters with expandable and collapsible sections.

---

#### **Technology Used**
1. **Frontend**:
    - **Java (Android)**: Main language for developing the Android application.
    - **XML**: Used for UI design and layout creation.
    - **Google Maps API**: Displays maps, routes, and markers for donation sites.
    - **Retrofit**: Manages API requests and responses.
    - **Material Design Components**: Ensures the app has a modern and responsive UI.

2. **Backend**:
    - **FastAPI**: Provides a fast and lightweight backend API with endpoints for user and site management.
    - **SQLAlchemy**: ORM for database operations, ensuring efficient data handling.
    - **PostgreSQL**: Relational database for storing user and site data.
    - **Docker & Docker Compose**:
        - Used for containerization of the backend.
        - The command `docker compose --profile dev up` initializes the backend with all its dependencies.

3. **Tools**:
    - **Android Studio**: IDE for app development.
    - **Postman**: For testing API endpoints during development.
    - **Git**: Version control for tracking changes.

---

#### **Drawbacks**
1. **No Role-Based Access Control**:
    - All users have the same level of access regardless of their role.

2. **No Notifications**:
    - The app does not support push notifications for updates or alerts.

3. **Simple UI Design**:
    - The user interface is minimalistic and lacks advanced interactive elements.

4. **UI Errors**:
    - Some UI elements may behave unexpectedly or display errors under certain conditions.

---

#### **Backend Setup Instructions**

The app supports two backend configurations:
1. **Local Backend (Development Mode)**
2. **VPS Backend (Production Mode)**

---

#### **1. Local Backend Setup**
To run the backend locally:
1. **Unzip the backend source code.**
2. Install docker https://docs.docker.com/desktop/setup/install/windows-install/
3. Navigate to the backend directory in your terminal.
4. Run the following command to start the backend in development mode:
   ```bash
   docker compose --profile dev up --build -d
   ```
5. **Update the Base URL in the App**:  
   Open `app/src/main/java/com/example/as2_blood_donation/api/ApiClient.java` and set:
   ```
   private static final String BASE_URL = "http://10.0.2.2:8002/";
   ```

---

#### **2. VPS Backend Setup**
To connect the app to the deployed backend on a VPS:
1. No local backend setup is required.
2. **Update the Base URL in the App**:  
   Open `app/src/main/java/com/example/as2_blood_donation/api/ApiClient.java` and set:
   ```
   private static final String BASE_URL = "http://103.199.18.35:8002/";
   ```

---