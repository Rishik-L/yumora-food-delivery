# Yumora – Food Delivery (Spring Boot 3 / Java 17)

Run: `mvn spring-boot:run` -> open http://localhost:8080 for the Yumora web app (API under /api, H2 console: /h2-console)

| Method | Path | Purpose |
|---|---|---|
| GET | /api/restaurants?cuisine=Indian | List / filter restaurants (with menus) |
| GET | /api/restaurants/{id} | Restaurant + menu |
| POST | /api/restaurants | Create restaurant |
| POST | /api/restaurants/{id}/menu | Add menu item |
| POST | /api/orders | Place order |
| GET | /api/orders/{id} | Order details |
| GET | /api/orders?customer=Asha | Customer order history |
| PATCH | /api/orders/{id}/status | Advance / cancel order |

Status flow: PLACED > ACCEPTED > PREPARING > OUT_FOR_DELIVERY > DELIVERED (cancel allowed from PLACED/ACCEPTED).

## Try it
```
curl -X POST localhost:8080/api/orders -H 'Content-Type: application/json' -d '{
  "customerName":"Asha","deliveryAddress":"22 Indiranagar","restaurantId":1,
  "items":[{"menuItemId":1,"quantity":2},{"menuItemId":2,"quantity":3}]}'

curl -X PATCH localhost:8080/api/orders/1/status -H 'Content-Type: application/json' -d '{"status":"ACCEPTED"}'
```
