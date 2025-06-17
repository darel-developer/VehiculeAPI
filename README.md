
# 🚗 Vehicle API – Projet Propelize

API de gestion des utilisateurs et véhicules avec des fonctionnalités d'authentification basée sur **JWT** développée en **Java (Spring Boot)** avec une base de données **MySQL**.
Suivez ce guide pour explorer les endpoints disponibles et tester l'API avec Postman ou tout autre client HTTP..

## ✅ Fonctionnalités

- Créer un véhicule
- Lire tous les véhicules
- Lire un véhicule par ID
- Modifier un véhicule
- Supprimer un véhicule
- Données initiales insérées automatiquement via `data.sql`
- créer un utiliateur
- Authentifier un utilisateur

---

## 🛠️ Technologies

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL 8
- Docker & Docker Compose
- Postman (pour tester l’API)

---

## 🏗️ Structure du projet

src/
├── controller/ # Contrôleur REST (VehicleController)\
├── model/ # Modèle de données (Vehicle)\
├── repository/ # Accès aux données (VehicleRepository)\
├── service/ # Logique métier (VehicleService)\
├── resources/\
│ ├── application.properties # Config DB\
│ └── data.sql # Données initiales


---

## ⚙️ Lancement en local 
0. Avec docker 
```docker-compose up --build
1. Crée la base de données :

```sql
Executer la commande docker-compose up -d; 
```
2. Configure src/main/resources/application.properties 
```
spring.datasource.url=jdbc:mysql://localhost:3306/vehicle_db
spring.datasource.username=root
spring.datasource.password=
```
3.Compile et exécute :
```
./mvnw spring-boot:run
```
L’API sera disponible à : http://localhost:8080/

 Exemple des endpoints à tester sur postman

3.1 Authenfication \
         Inscription
```
 URL :/auth/register
 Méthode :POST
 Description : Crée un nouvel utilisateur dans le système.
 Corps de la requête (JSON) :
  {
    "name": "username",
    "password": "password"
  }

 Réponse attendue :
  {
    "id": 1,
    "name": "username",
    "password": "encoded_password"
  }
 
 Statut :200 OK pour une inscription réussie
```
Connexion 
```
 URL :/auth/login
 Méthode :POST
 Description : Authentifie un utilisateur et retourne un token JWT pour accéder aux endpoints protégés.
Corps de la requête (JSON) :
    {
        "name": "username",
        "password": "password"
    }
 
 Réponse attendue :
    {
        "token": "jwt-token"
    }
 
 Statut :200 OK pour une authentification réussie
```
3.2 Gestion du token JWT dans Postman\
Pour utiliser les endpoints protégés, vous devez sauvegarder le token JWT obtenu via
dans Postman.\
Étape 1 : Ajouter le script pour sauvegarder le token 
-  Ouvrez la requête POST {{baseUrl}}/auth/login dans la collection VehicleAPI.
-  Accédez à l'onglet Scripts (anciennement appelé Tests dans certaines versions de Postman)
-  Collez le script suivant pour sauvegarder le token dans une variable de collection :

```
 try {
 pm.test("Vérification de la réponse API", () => {
 pm.expect(pm.response.code).to.equal(200);
 const jsonData = pm.response.json();
 pm.expect(jsonData).to.have.property("token").and.to.be.a("string").and.to.not.be.empty;
 pm.collectionVariables.set("jwtToken", jsonData.token);
 console.log("Token enregistré dans la collection VehicleAPI :", jsonData.token);
 });
 } catch (error) {
 console.error("Erreur lors de l'exécution du script :", error.message);
 pm.collectionVariables.set("request_failed", true);
 }
```
-   Envoyez la requête
   POST {{baseUrl}}/auth/login
   . Le token sera sauvegardé dans la variable
   collection VehicleAPI.\
    Étape 2 : Configurer l'en-tête Authorization
-  Pour chaque requête protégée (par exemple,
   GET {{baseUrl}}/api/users
   ou
   {{baseUrl}}/api/vehicles
   ), ouvrez la requête dans Postman.
-  Accédez à l'onglet Authorization.
-  Dans le menu déroulant Type, sélectionnez Bearer Token
-  Dans le champ Token, entrez
   {{jwtToken}}
- Postman utilisera automatiquement la variable
  POST
  jwtToken
  de la
  jwtToken
  de la collection VehicleAPI pour remplir l'en-tête
  Authorization: Bearer {{jwtToken}}
  .`\

Étape 3 : Vérifier la variable de collection
-  Cliquez droit sur la collection VehicleAPI > Edit > onglet Variables
- Vérifiez que la variable
  jwtToken
  a une valeur dans la colonne Current Value après avoir exécuté la requête
  /auth/login
- Si la variable est vide, consultez la console Postman (View > Show Postman Console) pour voir les messages
  d'erreur ou de log.

4.  Gestion des utilisateurs \
4.1 Créer un utilisateur
```
URL :/api/users 
Méthode : POST
 Description : Crée un nouvel utilisateur.
 Corps de la requête (JSON) :
     {
     "name": "John Doe",
     "password": "password123"
     }
 Statut : 201 Created pour une création réussie.
 Authentification : Requiert un token JWT dans l'en-tête Authorization
```
4.2 Récupérer tous les utilisateurs
```
 URL : /api/users 
 Méthode : GET
 Description : Récupère la liste de tous les utilisateurs.
 Réponse attendue : Liste JSON des utilisateurs.
 Statut : 200 OK
 Authentification : Requiert un token JWT.
```
4.3 Récupérer un utilisateur à partir de l'ID
```
  URL :/api/users/{id}
 Méthode : PUT
 Description : Met à jour les informations d'un utilisateur existant.
 Corps de la requête (JSON) :
 {
    "name": "Jane Doe",
    "password": "newpassword123"
 }
 Statut : 200 OK
 Authentification : Requiert un token JWT
```
4.4 Mettre à jour un utilisateur
```
 URL :/api/users/{id}
 Méthode : GET
 Description : Récupère les détails d'un utilisateur spécifique par son ID.
 Exemple : /api/users/1
 Réponse attendue : Objet JSON représentant l'utilisateur.
 Statut : 200 OK
 Authentification : Requiert un token JWT.
```
4.5  Supprimer un utilisateur
```
 URL :/api/users/{id}
 Méthode :DELETE
 Description : Supprime un utilisateur par son ID.
 Exemple : /api/users/1
 Statut : 204 No Content pour une suppression réussie.
 Authentification : Requiert un token JWT.
```
5.  Gestion des véhicules\
5.1 Créer un véhicule
```
  URL :/api/vehicles 
  Méthode : POST
 Description : Crée un nouveau véhicule dans le système.
 Corps de la requête (JSON) :
     {
         "brand": "Toyota",
         "model": "Corolla",
         "type": "Car",
         "plateNumber": "ABC123",
         "year": 2020,
         "price": 15000
     }
 Statut : 201 Created
 Authentification : Requiert un token JWT.
```
5.2 Récupérer tous les véhicules
```
 URL :/api/vehicles 
 Méthode : GET
 Description : Récupère la liste de tous les véhicules.
 Réponse attendue : Liste JSON des véhicules.
 Statut : 200 OK
 Authentification : Requiert un token JWT.
```
5.3 Récupérer un véhicule par ID
```
 URL :/api/vehicles/{id}
 Méthode : GET
 Description : Récupère les détails d'un véhicule spécifique par son ID.
 Exemple : /api/vehicles/1
 Réponse attendue : Objet JSON représentant le véhicule.
 Statut : 200 OK
 Authentification : Requiert un token JWT
```
5.4 Récupérer des véhicules par prix
```
 URL :/api/vehicles/search/price/{price}
 Méthode :GET
 Description : Récupère les véhicules ayant un prix spécifique.
 Exemple :/api/vehicles/search/price/15000
 Réponse attendue : Liste JSON des véhicules correspondants.
 Statut :200 OK
 Authentification : Requiert un token JWT.
```
5.5 Récupérer des véhicules par année
```
 URL :/api/vehicles/search/year/{year}
 Méthode : GET
 Description : Récupère les véhicules d'une année spécifique.
 Exemple : /api/vehicles/search/year/2020
 Réponse attendue : Liste JSON des véhicules correspondants.
 Statut : 200 OK
 Authentification : Requiert un token JWT
```
5.6 Mettre à jour un véhicule

```
 URL :/api/vehicles/{id}
 Méthode : PUT
 Description : Met à jour les informations d'un véhicule existant.
 Corps de la requête (JSON) :
     {
         "brand": "Toyota",
         "model": "Yaris",
         "type": "Car",
         "plateNumber": "XYZ789",
         "year": 2021,
         "price": 16000
     }
 Statut : 200 OK
 Authentification : Requiert un token JWT.
```
5.7  Supprimer un véhicule
```
  URL : 
/api/vehicles/{id}
 Méthode : 
DELETE
 Description : Supprime un véhicule par son ID.
 Exemple : 
/api/vehicles/1
 Statut : 
204 No Content
 🔒
 Authentification : Requiert un token JWT
```
#👨‍💻 Auteur
Développé dans le cadre du TP00 - TP01 – Software Testing - API Construction pour le cours ICT 304.
![CI Propelize](https://github.com/TON_UTILISATEUR/TON_REPO/actions/workflows/ci.yml/badge.svg)


Université de Yaoundé I
Département d’Informatique – Faculté des Sciences
Encadré par Dr KIMBI 


# VehiculeAPI
test d'une api de véhicule 



