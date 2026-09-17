# SocialAPP

A location-aware social app monorepo: a Spring Boot REST API backed by PostgreSQL/PostGIS and an Expo (React Native) mobile client. Users register, create geo-tagged posts under a category tree, browse a ranked feed, and chat one-to-one.

## Repository layout

| Path      | Contents                                                              |
| --------- | --------------------------------------------------------------------- |
| `backend` | Spring Boot 4 REST API (Java 17, Maven), PostGIS, Flyway, JWT auth    |
| `mobile`  | Expo Router app (React Native 0.86, React 19, TypeScript, NativeWind) |
| `web`     | Placeholder — empty                                                   |
| `docs`    | Placeholder — empty                                                   |

## Architecture

```
mobile (Expo)  ──fetch──>  backend /api  ──JPA──>  PostgreSQL + PostGIS
                                │
                                ├── SeaweedFS (S3 API) — image storage
                                └── Google Geocoding API — address -> coordinates
```

The mobile client keeps all server communication in React context providers
(`mobile/context/`), one per domain: `AuthContext`, `UserContext`, `PostContext`,
`CategoryContext`, `ChatContext`. The API host is a single constant in
`mobile/constants/api.ts`.

The backend is organised by feature package under
`backend/src/main/java/socialapp/backend/`:

- `authentication` — register/login/logout, password encoding
- `security` — JWT filter, `SecurityProperties`, Spring Security config, `UserPrincipal`
- `users` — user profiles plus an admin-only CRUD controller
- `posts` — post creation and retrieval, DTO mapping
- `categories` — main/sub category tree
- `chats` — chats and messages
- `feed` — `FeedRanker` / `FeedRankingService` feed ordering
- `location` — `Location` entity and Google Maps geocoding lookup
- `storage` — S3-compatible object storage (SeaweedFS)

## Prerequisites

- JDK 17
- Docker (for PostGIS + SeaweedFS via `backend/compose.yml`)
- Node.js with npm, and the Expo tooling (`npx expo`)
- A Google Maps (Geocoding) API key

## Getting started

### 1. Backend

Create `backend/.env` from the template and fill in real values:

```bash
cd backend
cp env-example.txt .env
```

`env-example.txt` documents every variable the app reads: Postgres connection,
SeaweedFS S3 credentials, the bootstrap admin account, JWT settings
(`JWT_NAME`, `JWT_SECRET_KEY`, `TOKEN_VALIDITY_SECONDS`, `ALLOWED_ORIGINS`), and
the Google Maps key.

Start the dependencies and the API together:

```bash
make dev          # opens Docker, `docker compose up -d`, sources .env, runs the app
```

Or run the pieces yourself:

```bash
docker compose up -d                      # postgis on $DB_HOST_PORT, seaweedfs on 8333
set -a && source .env && set +a
./mvnw spring-boot:run                    # API on :8080 under context path /api
```

Other targets: `make run` (app only, no Docker), `make clean` (remove `target/`).

The API is served under the `/api` context path, e.g.
`http://localhost:8080/api/auth/login`. Schema is managed by Flyway
(`src/main/resources/db/migration`) with Hibernate `ddl-auto: update` also
enabled. Spring Actuator and springdoc OpenAPI UI are on the classpath.

### 2. Mobile

Point the client at your machine's LAN address so a physical device can reach
the API — edit `API_BASE` in `mobile/constants/api.ts`:

```ts
export const API_BASE = "http://192.168.8.223:8080";
```

Then:

```bash
cd mobile
npm install
npm start           # or: make dev  (expo start -c)
```

Platform shortcuts: `npm run ios`, `npm run android`, `npm run web`.
Lint with `npm run lint`.

Screens live in `mobile/app/` using Expo Router file-based routing:
`(auth)` for login, `(tabs)` for feed/map/chats/profile, plus `chat/`,
`post/[postId]` and `createPost`.

## Tests

```bash
cd backend
./mvnw test
```

Backend tests cover the post, user, category and auth controllers and services,
running against an in-memory H2 database.

## API reference

All routes are relative to `/api`. Authentication is a JWT carried in a cookie
named by `JWT_NAME`.

### Auth

| Method | Path             | Purpose                  |
| ------ | ---------------- | ------------------------ |
| GET    | `/auth`          | Check current session    |
| POST   | `/auth/login`    | Log in, issue JWT        |
| GET    | `/auth/logout`   | Clear the session        |
| POST   | `/auth/register` | Create an account        |

### Users

| Method | Path                        | Purpose                     |
| ------ | --------------------------- | --------------------------- |
| GET    | `/users/me`                 | Current user's profile      |
| GET    | `/admin/users`              | List users (admin)          |
| GET    | `/admin/users/{id}`         | Get user by id (admin)      |
| GET    | `/admin/users/email/{email}`| Get user by email (admin)   |
| PUT    | `/admin/users/{id}`         | Update user (admin)         |
| DELETE | `/admin/users/{id}`         | Delete user (admin)         |

### Posts

| Method | Path                 | Purpose                        |
| ------ | -------------------- | ------------------------------ |
| GET    | `/posts`             | Current user's own posts       |
| POST   | `/posts`             | Create a post                  |
| POST   | `/posts/feed`        | Ranked feed for the user       |
| GET    | `/posts/{id}`        | Get a single post              |
| DELETE | `/admin/posts/{id}`  | Delete a post (admin)          |

### Categories

| Method | Path                       | Purpose                    |
| ------ | -------------------------- | -------------------------- |
| GET    | `/categories`              | All categories             |
| GET    | `/categories/{id}`         | Category by id             |
| GET    | `/categories/name/{name}`  | Category by name           |
| GET    | `/categories/main`         | Top-level categories       |
| GET    | `/categories/sub/{id}`     | Sub-categories of a parent |

### Chats

| Method | Path              | Purpose                      |
| ------ | ----------------- | ---------------------------- |
| GET    | `/chats`          | All chats for the user       |
| POST   | `/chats`          | Create a chat                |
| POST   | `/chats/message`  | Send a message               |

## Conventions

- Java: camelCase variables, guard-clause early returns, if-else over ternaries.
- TSX: arrow function components, `async`/`await` over `.then`, camelCase variables.
- Feature-package layout on the backend; DTOs and mappers per domain
  (`PostMapper`, `UserMapper`, `ChatMapper`) keep entities off the wire.
- Domain exceptions live in a per-feature `exceptions` package.

## Known gaps

- `web/` and `docs/` are empty placeholders.
- `mobile/context/AuthContext.tsx` still posts registration to a literal
  `YOUR_API_ENDPOINT/auth/register` placeholder instead of `API_BASE`.
- `API_BASE` is a hardcoded LAN address rather than an Expo env variable, so it
  must be edited per development machine.
