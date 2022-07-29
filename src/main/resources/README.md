# ATM

## Running the aplication

Run example

```
java -jar atm.jar
```
## GET
`http://localhost:8080/atm/login`

**Request Body**

```
{
    "userName": "string",
    "password": "string"
}
```

**Response**

OK

## GET
`http://localhost:8080/atm/balance`

**Response**

```
{
    "userName": "string",
    "balance": double
}
```