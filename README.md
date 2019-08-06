# bearings-info
Information about bearings and seals in different units of soviet heavy motorcycles

Features:
- provides bearings and seals info
- add/remove/edit items

App run:
- run back: from bearings-info/backend: mvn spring-boot:run
- run front: from bearings-info/frontend: 1. npm install 2. npm run serve -- --port 8091

Run dockerized:
- in app props: comment local mysql db: jdbc:mysql://localhost:3306/motorcycle_info 
- in app props: uncomment docker db service: jdbc:mysql://db:3306/motorcycle_info
- stop your local nginx if running (to release port 80)
- from bearings-info: docker-compose up

App: http://localhost/  
API documentation: 
http://localhost:8090/bearings-info/api/swagger-ui.html

Users & passwords:  
db: mysql -u demo -p demo  
app: admin: username=admin&password=admin, default user: username=user&password=user

Project protection:
https://docs.google.com/document/d/16tis1_LvQdWmRWXWd_qR710tbKsTSLjuKyg3dECgEzY/edit?usp=sharing