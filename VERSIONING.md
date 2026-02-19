# **ZeraBuilder Roadmap**

## **Version v0.4 – Current Release ✅**

**Core Features Implemented:**

| Category | Feature | Status | Notes |
|----------|--------|--------|------|
| SQL Builders | Select, Insert, Update, Delete, Create, Alter | ✅ | Dialect support: MySQL, SQLite |
| DSL Layer | Fluent API for CRUD & DDL | ✅ | Method chaining |
| DSL Layer | Parameterized queries (`?`) | ✅ | AND / OR / WHERE parentheses |
| Executor | ZeraExecutor: `update` / `query` | ✅ | Prepared statements, parameter binding |
| Connection | ZeraConnection wrapper | ✅ | JDBC DriverManager abstraction |
| Testing | Unit + Integration + Stress | ✅ | SQLite + MySQL tested |

---

## **Version v0.5 – Immediate Next Release 🟡**

**Focus:** Transactions, batch operations, better DSL ergonomics, and generic datatypes.

| Category | Feature | Status | Notes |
|----------|--------|--------|------|
| Connection | Connection pooling | ⬜ | HikariCP / DBCP |
| Executor | Transactions (`begin` / `commit` / `rollback`) | ✅ | DSL-friendly |
| Executor | Batch execution (`batchUpdate`) | ⬜ | Multi-row inserts/updates |
| DSL | Named parameters (`:id`) | ⬜ | Map internally to indices |
| DSL | Fluent join conditions | ⬜ | `.join().on()` chainable |
| DSL | Pagination helpers | ⬜ | `.limit(n).offset(m)` |
| Schema | Generic datatypes | ✅ | e.g., `.column("name", DataType.STRING)`, `.column("id", DataType.INT)` — automatically mapped to dialect-specific types |
| Schema | Nullable & default column values | ⬜ | `.nullable()`, `.default(value)` |
| Schema | Computed / generated columns | ✅ | e.g., `created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP` |
| Testing | Extended stress tests | ⬜ | Higher volume inserts, query performance |

---

## **Version v0.6 – Advanced Features 🟢**

**Focus:** Indexes, schema migrations, cross-dialect support.

| Category | Feature | Status | Notes |
|----------|--------|--------|------|
| Schema | Indexes | ⬜ | `.index(column1, column2)`, `.uniqueIndex()` |
| Schema | Drop table / column | ⬜ | Safety checks, `IF EXISTS` |
| Schema | Migrations / versioning | ⬜ | DSL-based migration files |
| DSL | Conditional queries | ⬜ | `.whereIf(condition, ...)` |
| DSL | Soft delete support | ⬜ | `.softDelete(column="deleted_at")` |
| Executor | Logging / debug mode | ⬜ | Print SQL + parameters |
| Executor | ResultSet mapping helpers | ⬜ | Reflection-based object mapping |

---

## **Version v1.0 – Production Ready ⭐**

**Focus:** Fully-featured ORM-lite, robust & stable

| Category | Feature | Status | Notes |
|----------|--------|--------|------|
| DSL | Reflection-based full mapping | ⬜ | Map ResultSet to POJOs / records |
| Executor | Fluent transactions | ⬜ | Nested transactions, rollback on exceptions |
| Executor | Concurrent queries | ⬜ | Thread-safe execution |
| Executor | Advanced batch operations | ⬜ | Bulk inserts / updates with auto-commit optimizations |
| Testing | Full CI/CD pipelines | ⬜ | Run MySQL + SQLite stress tests |
| Schema | Versioned migrations | ⬜ | Track and apply schema changes |
| Logging | Configurable logging | ⬜ | SLF4J or other backends |

---

## ✅ **Versioning Summary**

| Version | Scope |
|---------|-------|
| v0.4 | Core DSL + Executor + SQLite/MySQL support |
| v0.5 | Transactions, batch updates, ergonomics |
| v0.6 | Indexes, migrations, conditional queries |
| v1.0 | ORM-lite full-featured, production-ready |
