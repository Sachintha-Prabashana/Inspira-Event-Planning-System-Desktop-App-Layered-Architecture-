# Inspira — Event Planning System (JavaFX)

<!-- Badges (optional) -->
![Java](https://img.shields.io/badge/Java-21-blue)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36)
![MySQL](https://img.shields.io/badge/DB-MySQL-4479A1)

A JavaFX + MySQL desktop application for managing event planning operations (customers, bookings, events, suppliers/items, services, employees, payments) with authentication flows, email/OTP verification, and JasperReports reporting.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Clone](#clone)
  - [Prerequisites](#prerequisites)
  - [Database Setup (MySQL)](#database-setup-mysql)
  - [Configure DB Connection](#configure-db-connection)
  - [Run](#run)
- [Reports (JasperReports)](#reports-jasperreports)
- [Project Structure](#project-structure)
- [Screens (FXML)](#screens-fxml)
- [Troubleshooting](#troubleshooting)
- [License](#license)

## Features

- **Authentication & account flows**
  - Login
  - Registration (multi-step)
  - Forgot password + OTP verification + email sending
  - User profile update
- **Core modules (CRUD screens)**
  - Customers, Bookings, Events
  - Suppliers, Items, Services
  - Employees, Payments
- **Dashboard navigation**
- **Reporting (JasperReports)**
  - Customer report
  - Booking-Service report
  - Event-Supplier report

## Tech Stack

- **Java**: 21
- **UI**: JavaFX (Controls + FXML)
- **UI Components**: JFoenix
- **Database**: MySQL
- **Reports**: JasperReports
- **Mail**: Javax Mail
- **Build**: Maven (JavaFX Maven Plugin)
- **Utilities**: Lombok

## Getting Started

### Clone

Replace the URL below with your actual GitHub repository URL:

```bash
git clone https://github.com/<your-org-or-username>/<your-repo>.git
cd <your-repo>
```

### Prerequisites

- JDK **21**
- MySQL Server
- Maven (or use `mvnw` / `mvnw.cmd`)

### Database Setup (MySQL)

1. Start MySQL.
2. Run the schema script:
   - `src/main/resources/Schema/Schema.sql`

This creates the database **`Inspira`** and tables including:

- `Customer`, `Booking`, `Event`, `Payment`, `Employee`
- `Supplier`, `Item`, `EventSupplier`
- `Service`, `BookingService`
- `User`

> Note: Foreign keys use `ON UPDATE CASCADE ON DELETE CASCADE` in multiple tables.

### Configure DB Connection

Update credentials/URL in:

- `src/main/java/edu/ijse/inspira1stsemesterproject/db/DBConnection.java`

### Run

From the project root:

- Using Maven:
  - `mvn clean javafx:run`
- Using Maven Wrapper (Windows):
  - `mvnw.cmd clean javafx:run`

#### Main class note

The JavaFX plugin in `pom.xml` references a `HelloApplication` main class, but the project contains `AppInitializer.java` (loads `/view/LoginPage.fxml`).

If startup fails due to a missing main class, update `pom.xml` to use:

- `edu.ijse.inspira1stsemesterproject/edu.ijse.inspira1stsemesterproject.AppInitializer`

## Reports (JasperReports)

Report templates:

- `src/main/resources/reports/CustomerReport.jrxml`
- `src/main/resources/reports/BookingServiceReport.jrxml`
- `src/main/resources/reports/EventSupplier.jrxml`

## Project Structure

- `src/main/java/edu/ijse/inspira1stsemesterproject/`
  - `AppInitializer.java` — JavaFX entry point
  - `controller/` — UI controllers
  - `dao/` — DAO layer (factory + SQL util + CRUD abstractions)
  - `db/` — DB connection helper
  - `dto/`, `entity/`, `bo/` — domain & business logic
  - `view/tdm/` — table models for JavaFX tables
- `src/main/resources/`
  - `view/` — FXML screens
  - `style/Style.css` — styling
  - `image/` — assets/icons
  - `reports/` — Jasper templates
  - `Schema/Schema.sql` — DB schema

## Screens (FXML)

Located in `src/main/resources/view/`:

- `LogInPage.fxml`
- `FirstRegisterPage.fxml`, `SecondRegisterPage.fxml`
- `ForgotPasswordForm.fxml`, `OtpForm.fxml`, `OtpVerifiedForm.fxml`, `SendMailView.fxml`
- `DashBoard.fxml`, `DashBoardIntroPage.fxml`
- `CustomerForm.fxml`, `BookingForm.fxml`, `EventForm.fxml`
- `SupplierForm.fxml`, `ItemForm.fxml`, `ServiceForm.fxml`
- `EmployeeForm.fxml`, `PaymentForm.fxml`
- `UserUpdateForm.fxml`

## Troubleshooting

- **DB connection errors**: verify credentials in `DBConnection.java` and ensure `Schema.sql` was executed.
- **FXML resource not found**: confirm files exist under `src/main/resources/view/`.
- **Main class mismatch**: change the JavaFX Maven Plugin `mainClass` to `AppInitializer` (see above).

## License

Add a license if you plan to distribute this project.

