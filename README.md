# FrostMood ❄️🥤

Catálogo de frappés hecho con **Java 21 + Spring Boot 3 + Thymeleaf + PostgreSQL**.
Incluye pantalla principal y dos CRUD: **Categorías** y **Frappés** (cada frappé pertenece a una categoría).

## Variables de entorno
| Variable | Ejemplo |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/frostmood` |
| `DB_USER` | `postgres` |
| `DB_PASS` | `postgres` |
| `PORT` | `8080` (opcional) |

## Compilar y ejecutar
```bash
mvn clean package
java -jar target/frostmood.jar
```
Abrir http://localhost:8080

## Docker
```bash
docker build -t frostmood .
docker run -p 8080:8080 -e DB_URL=... -e DB_USER=... -e DB_PASS=... frostmood
```
