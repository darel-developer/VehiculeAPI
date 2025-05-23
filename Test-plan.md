# 🧪 Plan de test pour l'API VehicleAPI

## 1. 🎯 Objectif du test

Ce plan de test vise à valider le bon fonctionnement et la sécurité de l’API `vehicleapi`. Les objectifs sont :

* Vérifier les fonctionnalités principales : enregistrement, authentification, gestion des utilisateurs et des véhicules.
* S’assurer que l’authentification via JWT fonctionne correctement.
* Tester les restrictions d’accès aux ressources protégées.
* Vérifier la stabilité et la robustesse des services.

---

## 2. 🧱 Structure des cas de test

| ID   | Nom du test                           | Type de test     | Objectif                                    | Entrée                                   | Résultat attendu                     |
| ---- | ------------------------------------- | ---------------- | ------------------------------------------- | ---------------------------------------- | ------------------------------------ |
| TC01 | Enregistrement utilisateur            | Fonctionnel      | Ajouter un nouvel utilisateur               | JSON (name, password) à `/auth/register` | 200 OK avec l’utilisateur enregistré |
| TC02 | Connexion avec identifiants valides   | Authentification | Vérifier l’obtention du token JWT           | JSON à `/auth/login`                     | 200 OK avec un token JWT             |
| TC03 | Connexion avec mot de passe incorrect | Authentification | Refuser la connexion                        | JSON invalide                            | 401 Unauthorized ou 403 Forbidden    |
| TC04 | Accès API sans token                  | Sécurité         | Rejeter les accès sans authentification     | `GET /api/users` sans Authorization      | 403 Forbidden                        |
| TC05 | Accès API avec token valide           | Sécurité         | Autoriser avec un JWT valide                | `GET /api/users` avec token              | 200 OK avec liste                    |
| TC06 | Token expiré ou falsifié              | Sécurité         | Rejeter un token invalide                   | Token JWT expiré                         | 401 Unauthorized                     |
| TC07 | Ajout de véhicule                     | Fonctionnel      | Enregistrer un véhicule                     | `POST /api/vehicles`                     | 201 Created ou 200 OK                |
| TC08 | Lecture des véhicules                 | Fonctionnel      | Lister les véhicules                        | `GET /api/vehicles`                      | Liste des véhicules                  |
| TC09 | Suppression utilisateur               | Fonctionnel      | Supprimer un utilisateur                    | `DELETE /api/users/{id}`                 | 204 No Content                       |
| TC10 | Expiration du token JWT               | JWT              | Valider que le token expire après une heure | Appel après délai                        | 401 Unauthorized                     |

---

## 3. 🧪 Stratégie de test

* 📦 **Unitaire** : Tests JUnit avec Mockito pour `UserService`, `VehicleService`, `JwtUtil`
* 🌐 **Intégration** : Tests MockMvc pour `AuthController`, `VehicleController`
* 📫 **Manuel** : Tests Postman pour les flux utilisateur classiques
* 📈 **Couverture** : JaCoCo utilisé pour mesurer la couverture des tests

---

## 4. 📂 Fichiers de test associés

| Fichier de test              | Cible                                                |
| ---------------------------- | ---------------------------------------------------- |
| `UserServiceTest.java`       | Enregistrement, suppression, recherche d’utilisateur |
| `VehicleServiceTest.java`    | CRUD sur véhicules                                   |
| `JwtUtilTest.java`           | Génération et validation de JWT                      |
| `AuthControllerTest.java`    | Connexion et enregistrement via API                  |
| `VehicleControllerTest.java` | Gestion des véhicules via l’API                      |

---

## 5. 📌 Résumé

Ce plan de test fournit un cadre clair pour assurer la fiabilité et la sécurité de l’API `vehicleapi`. Il servira de base pour le suivi des tests fonctionnels et de sécurité.
