# 🔐 Rapport de sécurité – API VehicleAPI

## 1. 🎯 Objectif de la sécurité

Ce rapport vise à expliquer la stratégie de sécurité mise en œuvre dans l’API `vehicleapi`, en particulier l’authentification, l’autorisation, et la protection des endpoints sensibles.

---

## 2. 🔑 Authentification

L’authentification permet d’identifier un utilisateur à l’aide de ses identifiants :

* Endpoint utilisé : `POST /auth/login`
* Données envoyées : JSON avec `name` et `password`
* Vérification des identifiants dans la base de données
* Si les identifiants sont corrects, un **token JWT** est généré

Le token est ensuite renvoyé au client, qui devra le fournir pour chaque requête sécurisée via l’en-tête :

```http
Authorization: Bearer <token>
```

---

## 3. 🛡️ Autorisation

L’autorisation contrôle l’accès aux ressources selon le rôle ou l’authentification :

* Toutes les routes `/auth/**` sont **publiques** (`permitAll()`)
* Les routes `/api/users`, `/api/vehicles`, etc. sont **protégées** (`authenticated()`)
* Le filtre `JwtFilter` intercepte chaque requête et valide le token avant d’autoriser l’accès

---

## 4. 🔄 Gestion des sessions

L’API est **stateless** (sans session serveur). L’état de l’utilisateur est contenu dans le JWT.
Configuration :

```java
.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
```

---

## 5. 🧩 Composants de sécurité

| Composant            | Rôle                                                               |
| -------------------- | ------------------------------------------------------------------ |
| `JwtUtil`            | Génère et vérifie les tokens JWT                                   |
| `JwtFilter`          | Intercepte les requêtes HTTP et valide le JWT                      |
| `SecurityConfig`     | Configure les règles de sécurité de l’application                  |
| `UserDetailsService` | Charge les utilisateurs lors de l’authentification Spring Security |

---

## 6. 🧪 Tests de sécurité réalisés

* Connexion avec identifiants corrects → 200 OK + token JWT
* Connexion avec mauvais mot de passe → 401 Unauthorized
* Accès à `/api/users` sans token → 403 Forbidden
* Accès avec token invalide → 401 Unauthorized
* Accès avec token expiré → 401 Unauthorized
* Accès avec token valide → 200 OK

---

## 7. ✅ Résumé

L’API utilise JWT avec Spring Security pour garantir un **accès sécurisé, contrôlé, et stateless**. Toutes les routes critiques sont protégées, et la logique d’authentification est testée via des tests unitaires et Postman. Ce système est robuste, mais devra être renforcé en production avec :

* Rotation des clés secrètes
* Stockage sécurisé des tokens côté client
* Gestion des rôles et permissions plus fine
