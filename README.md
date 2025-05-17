
# 🚗 Vehicle API – Projet Propelize

API de gestion de véhicules développée en **Java (Spring Boot)** avec une base de données **MySQL**².

## ✅ Fonctionnalités

- Créer un véhicule
- Lire tous les véhicules
- Lire un véhicule par ID
- Modifier un véhicule
- Supprimer un véhicule
- Données initiales insérées automatiquement via `data.sql`

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
├── controller/ # Contrôleur REST (VehicleController)
├── model/ # Modèle de données (Vehicle)
├── repository/ # Accès aux données (VehicleRepository)
├── service/ # Logique métier (VehicleService)
├── resources/
│ ├── application.properties # Config DB
│ └── data.sql # Données initiales


---

## ⚙️ Lancement en local 

1. Crée la base de données :

```sql
CREATE DATABASE vehicle_db; 
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
L’API sera disponible à : http://localhost:8080/api/vehicles

 Exemple de requêtes Postman
```
GET http:localhost:8080/api/vehicles → Tous les véhicules

GET http:localhost:8080/api/vehicles/1 → Véhicule avec ID = 1

GET http://localhost:8080/api/vehicules/search/price/50000 → Véhicule avec pour prix = 50000 

POST /api/vehicles → Créer un véhicule

PUT  http://localhost:8080/api/vehicles/1 → Modifier le véhicule avec l'id 1

DELETE http://localhost:8080/api/vehicles/1 → Supprimmer le véhicule avec l'id 1

json
Copier
Modifier
{
  "brand": "Renault",
  "model": "Clio",
  "type": "car",
  "plateNumber": "AA123BB",
  "year": 2020,
  "price": 50000
}
```
#👨‍💻 Auteur
Développé dans le cadre du TP00 – Software Testing - API Construction pour le cours ICT 304.

Université de Yaoundé I
Département d’Informatique – Faculté des Sciences
Encadré par Dr KIMBI 


# VehiculeAPI
test d'une api de véhicule 

