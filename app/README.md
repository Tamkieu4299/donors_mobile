Here's the updated **Readme.txt** with instructions for the two backend configurations:

---

### **Readme.txt**

**Student ID**: s3922087  
**Student Name**: Kieu Cong Tam

---

#### **Backend Setup Instructions**

The app supports two backend configurations:
1. **Local Backend (Development Mode)**
2. **VPS Backend (Production Mode)**

---

#### **1. Local Backend Setup**
To run the backend locally:
1. **Unzip the backend source code.**
2. Navigate to the backend directory in your terminal.
3. Run the following command to start the backend in development mode:
   ```bash
   docker compose --profile dev up --build -d
   ```
4. **Update the Base URL in the App**:  
   Open `app/src/main/java/com/example/as2_blood_donation/api/ApiClient.java` and set:
   ```java
   private static final String BASE_URL = "http://10.0.2.2:8002/";
   ```

---

#### **2. VPS Backend Setup**
To connect the app to the deployed backend on a VPS:
1. No local backend setup is required.
2. **Update the Base URL in the App**:  
   Open `app/src/main/java/com/example/as2_blood_donation/api/ApiClient.java` and set:
   ```java
   private static final String BASE_URL = "http://103.199.18.35:8002/";
   ```

---

#### **Switching Between Environments**
- To run the app in **local development mode**, ensure the backend is running locally, and set the Base URL to `http://10.0.2.2:8002/`.
- To run the app using the **VPS backend**, simply set the Base URL to `http://103.199.18.35:8002/`.

Ensure the correct backend configuration is used based on your environment.

--- 

