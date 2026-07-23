# UrbanHive

UrbanHive is a starter Java Servlet + JSP + JDBC web application for apartment or housing society management.

## Included modules

- Login and role-based session handling
- Resident bill viewing
- Complaint ticket creation and tracking
- Notice posting
- Visitor entry logs for guards

## Project layout

- `src/com/urbanhive/...`: Java source files
- `webapp/`: JSP pages, assets, and `WEB-INF/web.xml`
- `schema.sql`: MySQL schema and seed data

## How to run

1. Create a Dynamic Web Project in Eclipse or IntelliJ with Tomcat.
2. Copy this folder into the project.
3. Add the MySQL JDBC driver JAR.
4. Update DB credentials in `src/com/urbanhive/util/Db.java`.
5. Run `schema.sql` in MySQL.
6. Start Tomcat and open `http://localhost:8080/UrbanHive/login`.

## Demo accounts

- Admin: `admin@urbanhive.local` / `admin123`
- Resident: `resident@urbanhive.local` / `resident123`
- Guard: `guard@urbanhive.local` / `guard123`
