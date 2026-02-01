# ZeraBuilder

ZeraBuilder is a lightweight, dialect-agnostic SQL builder and DSL for Java. It provides a fluent, type-safe API to construct SQL queries, execute them, and map results to Java objects. Designed for **SQLite** and **MySQL** (PostgreSQL support planned), ZeraBuilder simplifies repetitive CRUD operations while keeping the flexibility for custom business logic.

---

## **Current Version**

**v0.4** ✅

- SQL Builders: Select, Insert, Update, Delete, Create, Alter
- Dialect support: SQLite, MySQL
- Fluent DSL for CRUD & DDL
- Parameterized queries (`?`) with AND/OR conditions
- Prepared statement execution via `ZeraExecutor`
- Connection abstraction via `ZeraConnection`
- Unit and integration tests, stress-tested

---

## **Installation**

Add the JAR to your project, or use Maven/Gradle once published.

```xml
<!-- Maven -->
<dependency>
    <groupId>com.chemicaldev</groupId>
    <artifactId>zerabuilder</artifactId>
    <version>0.4</version>
</dependency>
```

## **Getting Started**

### **1. Initialize Builder**
#### Currently supports `SQLite` and `MySQL` only.
```java
import com.chemicaldev.zerabuilder.main.SQLDialect;

ZeraBuilder builder = new ZeraBuilder(SQLDialect.SQLITE);
ZeraBuilder builder = new ZeraBuilder(SQLDialect.MYSQL);
ZeraBuilder builder = new ZeraBuilder(SQLDialect.POSTRESQL);
```

---

### **2. Create a Table**

```java
CreateDSL createUserTable = builder.makeTable("users")
    .column("id INTEGER")
    .column("email TEXT")
    .column("password TEXT")
    .column("age INTEGER")
    .primaryKey("id")
    .unique("email");

try (ZeraConnection conn = new ZeraConnection("jdbc:sqlite:test.db", "", "")) {
    ZeraExecutor exec = new ZeraExecutor(conn);
    exec.update(createUserTable);
}
```

---

### **3. Insert Records**

```java
InsertDSL insertUser = builder.insertTo("users")
    .columns("id", "email", "password", "age");

insertUser.values(0, "user1@test.com", "user1password", 21);
insertUser.values(1, "user2@test.com", "user2password", 21);

try (ZeraConnection conn = new ZeraConnection("jdbc:sqlite:test.db", "", "")) {
    ZeraExecutor exec = new ZeraExecutor(conn);
    exec.update(insertUser);
}
```

---

### **4. Select Records**

```java
SelectDSL selectAll = builder.list("*").from("users");

try (ZeraConnection conn = new ZeraConnection("jdbc:sqlite:test.db", "", "")) {
    ZeraExecutor exec = new ZeraExecutor(conn);
    List<User> users = exec.query(selectAll, rs -> {
        String email = rs.getString("email");
        String password = rs.getString("password");
        int age = rs.getInt("age");
        return new User(email, password, age);
    });

    users.forEach(u -> System.out.println(u.email() + " " + u.password() + " " + u.age()));
}

record User(String email, String password, int age) {}
```

---

### **5. Update Records**

```java
UpdateDSL updateUser = builder.update("users")
    .set("password", "newPassword123")
    .where(Conditions.eq("email", "user1@test.com"));

try (ZeraConnection conn = new ZeraConnection("jdbc:sqlite:test.db", "", "")) {
    ZeraExecutor exec = new ZeraExecutor(conn);
    exec.update(updateUser);
}
```

---

### **6. Delete Records**

```java
DeleteDSL deleteUser = builder.deleteFrom("users")
    .where(Conditions.eq("email", "user1@test.com"));

try (ZeraConnection conn = new ZeraConnection("jdbc:sqlite:test.db", "", "")) {
    ZeraExecutor exec = new ZeraExecutor(conn);
    exec.update(deleteUser);
}
```

---

### **Feature Roadmap**

**Current version:** v0.4

| Version | Scope                                                           |
|---------|-----------------------------------------------------------------|
| v0.4 ✅ | Core DSL + Executor + SQLite/MySQL support                      |
| v0.5 ⬜ | Transactions, batch updates, DSL ergonomics + Generic Datatypes |
| v0.6 ⬜ | Indexes, migrations, conditional queries                        |
| v1.0 ⬜ | ORM-lite full-featured, production-ready                        |

## **Version v0.5 – Immediate Next Release 🟡**

**Focus:** Transactions, batch operations, better DSL ergonomics, and generic datatypes.

| Category | Feature | Status | Notes |
|----------|--------|--------|------|
| Connection | Connection pooling | ⬜ | HikariCP / DBCP |
| Executor | Transactions (`begin` / `commit` / `rollback`) | ⬜ | DSL-friendly |
| Executor | Batch execution (`batchUpdate`) | ⬜ | Multi-row inserts/updates |
| DSL | Named parameters (`:id`) | ⬜ | Map internally to indices |
| DSL | Fluent join conditions | ⬜ | `.join().on()` chainable |
| DSL | Pagination helpers | ⬜ | `.limit(n).offset(m)` |
| Schema | Generic datatypes | ⬜ | e.g., `.column("name", DataType.STRING)`, `.column("id", DataType.INT)` — automatically mapped to dialect-specific types |
| Schema | Nullable & default column values | ⬜ | `.nullable()`, `.default(value)` |
| Schema | Computed / generated columns | ⬜ | e.g., `created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP` |
| Testing | Extended stress tests | ⬜ | Higher volume inserts, query performance |

### Better datatype assignement
 Instead of typing the specific dialect, you just assign a datatype
```java
builder.makeTable("users")
.column("id", DataType.INT)
.column("email", DataType.STRING)
.column("created_at", DataType.TIMESTAMP)
.primaryKey("id");
```