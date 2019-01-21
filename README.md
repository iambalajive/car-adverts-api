# car-adverts-api
car-adverts-api




# Create Advertisement HTTP method - PUT

http://localhost:8000/advertisement

For new Cars
{
    "title": "apple",
    "fuelType": "PETROL",
    "price": 39,
    "condition": "NEW"
}

For old cars

{
    "title": "apple",
    "fuelType": "DIESEL",
    "price": 39,
    "condition": "USED",
    "mileage" :24,
    "firstReg" :"30/11/2011"
}


# Update Advertisement Http method - POST

http://localhost:8000/advertisement/e331f42e-10d3-4626-9713-077237d5542a

{
   "id":"e331f42e-10d3-4626-9713-077237d5542a,
    "title": "apple",
    "fuelType": "DIESEL",
    "price": 39,
    "condition": "USED",
    "mileage" :24,
    "firstReg" :"30/11/2011"
}


# GET Advertisement Http method - GET

http://localhost:8000/advertisement/e331f42e-10d3-4626-9713-077237d5542a


# List Advertisement Http method - GET

http://localhost:8000/advertisement?sortKey=firstReg&sortOrder=desc

valid sortkeys are
id,title,price,mileage,firstReg,fuelType

valid sortOrder desc,asc

# Delete Advertisement Http method - DELETE

http://localhost:8000/advertisement/e331f42e-10d3-4626-9713-077237d5542a
