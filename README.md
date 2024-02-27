# Charging Station Validation Service
 - In order to launch the application you need to take the following steps:
   - raise the postgres container from the docker.compose file:
 
   `docker-compose up postgres`;

    - connect postgres data base using parameters in application.yaml;

- In order to make sure that everything works correctly, go to the tests and check if all tests pass;

- After you run the application, you can test it in postman at this address ` http://localhost:8080/validate ` using the Post method;

- In order to select the language in which you want to receive an answer, add Header "Accept-Language" with one of the values of your choice (ua, fr, de, en);

To test via postman, paste the below json line into the request body:


```
{
    "id": "86b9b1bb-8614-4915-b496-517bbc351739",
    "title": "Station Title",
    "description": "Station Description",
    "address": "123 Main St, City, Country",
    "geoCoordinates": "46.430095, 30.696315",
    "isPublic": true,
    "connectors": [
        {
        "id": "86b9b1bb-8664-4915-b496-517bbc351739",
        "type": "CCS",
        "maxPowerKw": 50
        },
        {
        "id": "86b9b1bb-8614-4915-b496-517bbc351788",
        "type": "CHAdeMO",
        "maxPowerKw": 30
        }
    ]
}
```

   

