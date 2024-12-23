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

#### **How to Run the Backend**
1. Ensure Docker and Docker Compose are installed on your system.
2. Navigate to the backend directory.
3. Run the following command to start the backend services:
   ```bash
   docker compose --profile dev up
   ```

4. The backend will be accessible at `http://localhost:8002/api/v1/docs`.

---