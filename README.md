# 🗳️ Online Poll & Voting Application

A full-stack **Online Poll & Voting Application** built with **Spring Boot, Angular, MySQL, Docker, Docker Compose, Nginx, Docker Hub, and AWS EC2**.

What started as a simple voting feature became a hands-on project for understanding how **frontend, backend, database, containers, networking, reverse proxies, image registries, and cloud deployment** work together in a production-style environment.

---

## 🚀 Features

* 🗳️ **Create Polls**

  * Create polls with multiple voting options.

* 👥 **Vote on Polls**

  * Users can select an option and submit their vote.

* 📊 **View Poll Results**

  * Display voting results for available polls.

* ⚛️ **Angular Frontend**

  * Provides the user interface for polls and voting.

* ☕ **Spring Boot REST API**

  * Handles poll, voting, and backend operations.

* 🗄️ **MySQL Database**

  * Stores polls, options, and voting data.

* 🐳 **Dockerized Services**

  * Frontend, backend, and database run as containers.

* 🔗 **Docker Compose**

  * Manages the multi-container application.

* 🔀 **Nginx Reverse Proxy**

  * Serves the Angular application and forwards `/api` requests to Spring Boot.

* 🚢 **Docker Hub**

  * Stores the application's Docker images.

* ☁️ **AWS EC2 Deployment**

  * Runs the containerized application in the cloud.

---

## 🛠️ Tech Stack

| Technology        | Purpose                       |
| ----------------- | ----------------------------- |
| ☕ Java            | Backend development           |
| 🌱 Spring Boot    | REST API and backend          |
| ⚛️ Angular        | Frontend                      |
| 🗄️ MySQL         | Database                      |
| 🐳 Docker         | Containerization              |
| 🧩 Docker Compose | Multi-container orchestration |
| 🔀 Nginx          | Web server & reverse proxy    |
| 🚢 Docker Hub     | Docker image registry         |
| ☁️ AWS EC2        | Cloud deployment              |
| 🔀 Git & GitHub   | Version control               |

---

## 🏗️ Application Architecture

The application uses Nginx as the entry point for both frontend and backend requests.

```text id="5gl2ax"
                    🌐 User
                       │
                       ▼
              ┌─────────────────┐
              │      Nginx      │
              │                 │
              │  Web Server +   │
              │ Reverse Proxy   │
              └───────┬─────────┘
                      │
             ┌────────┴─────────┐
             │                  │
             │                  │
        Frontend Request     /api/*
             │                  │
             ▼                  ▼
      ┌─────────────┐    ┌─────────────┐
      │   Angular   │    │ Spring Boot │
      │  Frontend   │    │  REST API   │
      └─────────────┘    └──────┬──────┘
                                │
                                │ JDBC
                                ▼
                         ┌─────────────┐
                         │    MySQL    │
                         │  Database   │
                         └─────────────┘
```

---

## 🔄 Request Flow

### Frontend Request

```text id="s4j0x7"
User
 ↓
Nginx
 ↓
Angular
 ↓
Display Application
```

### API Request

```text id="9l1qby"
Angular
 ↓
/api/*
 ↓
Nginx
 ↓
Spring Boot
 ↓
MySQL
 ↓
Spring Boot
 ↓
Nginx
 ↓
Angular
 ↓
User
```

---

## 🔀 Nginx Reverse Proxy

Nginx acts as the main entry point to the application.

It performs two important tasks:

### 1. Serve Angular

Nginx serves the compiled Angular application as static files.

```text id="3z8y6a"
Browser
   ↓
Nginx
   ↓
Angular Static Files
```

### 2. Reverse Proxy API Requests

Requests beginning with `/api/` are forwarded to the Spring Boot backend.

```text id="v7gq74"
Browser
   │
   │ /api/*
   ▼
 Nginx
   │
   │ proxy_pass
   ▼
Spring Boot :8080
```

This allows the frontend to communicate using relative URLs such as:

```text id="w8o4fq"
/api/polls
```

instead of hardcoding:

```text id="l0c6qm"
http://localhost:8080/api/polls
```

This becomes especially useful when deploying the application to AWS.

---

## 🐳 Docker Architecture

The application is separated into multiple containers:

```text id="j8a7c1"
┌─────────────────────────────────────┐
│          Docker Compose             │
│                                     │
│  ┌─────────────┐                    │
│  │   Nginx     │                    │
│  │  Frontend   │                    │
│  └──────┬──────┘                    │
│         │                            │
│         ▼                            │
│  ┌─────────────┐      ┌──────────┐ │
│  │ Spring Boot │─────►│  MySQL   │ │
│  │   Backend   │      │ Database │ │
│  └─────────────┘      └──────────┘ │
│                                     │
└─────────────────────────────────────┘
```

Docker Compose creates a shared network that allows containers to communicate using their **service names**.

For example:

```text id="v4l5q8"
Spring Boot
     │
     │ mysql-db:3306
     ▼
   MySQL
```

The backend does not need to use `localhost` to communicate with the MySQL container.

---

## 🌐 Docker Networking

One of the important concepts learned in this project was Docker's internal networking.

Inside Docker Compose:

```text id="n6x3s2"
backend container
       │
       │
       ▼
mysql-db:3306
       │
       ▼
MySQL container
```

The service name:

```text id="a6v3dj"
mysql-db
```

acts as the hostname inside the Docker network.

Therefore, the Spring Boot application can connect to MySQL using the Docker service name rather than:

```text
localhost
```

This is important because `localhost` inside a container refers to **that same container**, not another container.

---

## 🗳️ Voting Flow

```text id="u5k3h7"
👤 User
   ↓
Select Poll
   ↓
Select Option
   ↓
🗳️ Click Vote
   ↓
⚛️ Angular
   ↓
📡 POST /api/...
   ↓
☕ Spring Boot
   ↓
🗄️ MySQL
   ↓
Vote Saved
   ↓
Spring Boot
   ↓
Angular
   ↓
📊 Updated Results
```

---

## 🔌 REST API

The Spring Boot backend exposes REST APIs for poll and voting operations.

### Poll APIs

```http id="h4y4p1"
GET /api/polls
```

Retrieve available polls.

```http id="x3k7p2"
GET /api/polls/{id}
```

Retrieve a specific poll.

```http id="f1z4x9"
POST /api/polls
```

Create a new poll.

### Voting API

```http id="c6r8w2"
POST /api/polls/{pollId}/vote
```

Submit a vote for a poll option.

### Example Request

```json id="m9k4t2"
{
  "optionId": 1
}
```

> Update the endpoints and request structure above according to your actual Spring Boot controllers.

---

## 📂 Project Structure

```text id="1m6k9x"
poll-voting-application/
│
├── backend/
│   │
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── example/
│   │   │   │           └── poll/
│   │   │   │               ├── controller/
│   │   │   │               ├── service/
│   │   │   │               ├── repository/
│   │   │   │               ├── model/
│   │   │   │               └── PollApplication.java
│   │   │   │
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   │
│   │   └── test/
│   │
│   ├── Dockerfile
│   └── pom.xml
│
├── frontend/
│   │
│   ├── src/
│   │   ├── app/
│   │   ├── components/
│   │   └── ...
│   │
│   ├── Dockerfile
│   ├── nginx/
│   │   └── default.conf
│   └── package.json
│
├── docker-compose.yml
├── screenshots/
│   ├── home.png
│   ├── create-poll.png
│   └── voting-results.png
│
└── README.md
```

> Update the structure to match your actual repository.

---

## 🐳 Docker Compose

The application uses Docker Compose to manage the different services.

```text id="c7k3m1"
docker-compose.yml

        │
        ├── frontend / nginx
        │
        ├── backend / Spring Boot
        │
        └── mysql-db / MySQL
```

### Start the Application

```bash id="m8q1f4"
docker compose up -d
```

### Check Running Containers

```bash id="t5n7z3"
docker compose ps
```

### View Logs

```bash id="j2v9c5"
docker compose logs -f
```

### Stop the Application

```bash id="k3x8p6"
docker compose down
```

---

## ☁️ AWS EC2 Deployment

The application is deployed on an **AWS EC2 Ubuntu instance** using Docker Compose.

The deployment architecture is:

```text id="e4s9k2"
                    ☁️ AWS EC2
                         │
                         ▼
                ┌─────────────────┐
                │ Docker Compose  │
                └────────┬────────┘
                         │
          ┌──────────────┼──────────────┐
          │              │              │
          ▼              ▼              ▼
       Nginx        Spring Boot       MySQL
       Container      Container      Container
          │              │              │
          └──────────────┴──────────────┘
                    Docker Network
```

The EC2 instance acts as the cloud host while Docker Compose manages the application containers.

---

## 🚢 Docker Hub

Docker images are built and pushed to Docker Hub.

```text id="x4q9v1"
Source Code
     ↓
Docker Build
     ↓
Docker Image
     ↓
Docker Hub
     ↓
AWS EC2
     ↓
docker pull
     ↓
Running Container
```

This allows the same container image to be deployed consistently across environments.

---

## ⚙️ Setup & Installation

### Prerequisites

Install:

* Java 17+
* Maven
* Node.js
* npm
* Docker
* Docker Compose
* Git

---

### 1. Clone Repository

```bash id="k5m7d3"
git clone https://github.com/<your-username>/<your-repository>.git

cd poll-voting-application
```

---

### 2. Configure Backend

Configure your database settings for the Docker environment.

For example:

```properties id="d7h2m5"
spring.datasource.url=jdbc:mysql://mysql-db:3306/pollapp
spring.datasource.username=root
spring.datasource.password=root
```

> Use environment variables or Docker secrets for production credentials instead of hardcoding passwords.

---

### 3. Start with Docker Compose

```bash id="f4k8r2"
docker compose up -d --build
```

Check the containers:

```bash id="q7m3x9"
docker compose ps
```

---

### 4. Access the Application

Open the EC2 public IP or your configured domain:

```text id="p5w2c8"
http://<EC2-PUBLIC-IP>
```

Nginx handles the incoming request and routes it to the appropriate service.

---

## 🖼️ Screenshots

### 🏠 Poll Application

![Poll Application](screenshots/home.png)

Main poll application interface.

---

### 📝 Create Poll

![Create Poll](screenshots/create-poll.png)

Create a new poll with multiple options.

---

### 🗳️ Voting Results

![Voting Results](screenshots/voting-results.png)

View the results after users submit their votes.

> Add your actual screenshots to the `screenshots/` directory and update the filenames if required.

---

## 🧠 Key Learning

The most valuable part of this project was discovering how many engineering concepts are involved behind a simple **Vote** button.

A single request can involve:

```text id="r3m8x1"
👤 User
   ↓
⚛️ Angular
   ↓
🔀 Nginx
   ↓
☕ Spring Boot
   ↓
🗄️ MySQL
   ↓
🐳 Docker Network
   ↓
☁️ AWS EC2
```

This project helped me understand how different parts of a modern application fit together.

---

## 📚 What I Learned

Through this project, I explored:

* Building REST APIs with Spring Boot.
* Connecting Angular with Spring Boot.
* Working with MySQL.
* Containerizing applications using Docker.
* Managing multiple containers using Docker Compose.
* Understanding Docker container networking.
* Using Docker service names for inter-container communication.
* Configuring Nginx as a web server.
* Configuring Nginx as a reverse proxy.
* Using relative `/api` URLs from the frontend.
* Building and managing Docker images.
* Pushing images to Docker Hub.
* Deploying containers to AWS EC2.
* Troubleshooting networking and container communication issues.

---

## 🔮 Future Improvements

Possible improvements include:

* 🔐 Spring Security authentication
* 👤 User registration and login
* 🛡️ Role-based authorization
* 📊 Advanced voting analytics
* 📈 Real-time voting results
* ⏱️ Poll expiration
* 🖼️ Poll image support
* 🔍 Search and filtering
* 👨‍💼 Admin dashboard
* ⚙️ Jenkins CI/CD
* 🔒 HTTPS with SSL/TLS
* 📊 Monitoring with Grafana
* ☸️ Kubernetes deployment
* 🔐 DevSecOps integration

---

## 🎯 Project Goal

The goal of this project was to understand how a full-stack application can move from **local development to a containerized cloud deployment**.

The complete architecture is:

**⚛️ Angular → 🔀 Nginx → ☕ Spring Boot → 🗄️ MySQL**

and the deployment workflow is:

**💻 Code → 🐳 Docker → 🚢 Docker Hub → ☁️ AWS EC2**

What looked like a simple **Vote** button turned into a practical lesson in **full-stack development, networking, containerization, reverse proxying, and cloud deployment.** 🗳️🚀

---

## 👨‍💻 Author

**Nilesh Kudale**

Java | Spring Boot | Angular | Docker | AWS | DevOps

---

## ⭐ Support

If you find this project useful or interesting, consider giving the repository a ⭐.

**Still learning. Still building. Still deploying. 🚀**
