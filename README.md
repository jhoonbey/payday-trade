#Payday Trade app

The application contains following microservices.
- customer-service,
- account-service,
- order-service,
- stock-service,
- notification-service,
- gateway-service

<br/>

Request samples to test application:

<br/>
Gateway url is :

[http://localhost:8181](http://localhost:8181 "http://localhost:8181")<br/>
</br>


### Create customer
[http://localhost:8181/api/customer/customers](http://localhost:8181/api/customer/customers "http://localhost:8181/api/customer/customers")
```{
	"name":"Jeyhun",
	"surname":"Rahimov",
	"email":"jhoonbey2@gmail.com",
	"password":"123456",
	"passwordAgain":"123456",
	"username":"jeyhunr"
}
```

<br/><br/>

### Create Account

[http://localhost:8181/api/account/accounts](http://localhost:8181/api/account/accounts "http://localhost:8181/api/account/accounts")

```json
{
	"customerId":1
}
```

>Account numbers are generated randomly (find generateNumericString in source)

<br/>

### Deposit amount to balance

[http://localhost:8181/api/account/accounts/07645095387998727321/deposit](http://localhost:8181/api/account/accounts/07645095387998727321/deposit "http://localhost:8181/api/account/accounts/07645095387998727321/deposit")

```json
{
	"amount": 50000
}
```

<br/>

### Get account cash balance

[http://localhost:8181/api/account/accounts/07645095387998727321/cash-balance](http://localhost:8181/api/account/accounts/07645095387998727321/cash-balance "http://localhost:8181/api/account/accounts/07645095387998727321/cash-balance")

Response:

```json
{
    "data": {
        "balance": 2000.00
    }
}
```
<br/><br/>


### Create Order

[http://localhost:8181/api/order/orders](http://localhost:8181/api/order/orders "http://localhost:8181/api/order/orders")

```json
{
    "userId":1,
    "stockId":1,
    "accountNumber":"07645095387998727321",
    "quantity":1,
    "targetPrice":3,
    "orderType":"BUY"
}
```

>Change "BUY" to "SELL" to sell order

<br/>