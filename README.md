# car-adverts-api
car-adverts-api


Requires java 8 and sbt


# Building the project and running
install SBT and java
sbt dist

# Running the app

a) With Docker
   execute build script inside bin/build.sh to create a docker image
   docker run -p 8000:8000 -p 8001:8001 car-adverts-api

b) Without docker
   Run sbt dist from project root
   Run this command -> unzip target/universal/car-adverts-api-0.1.zip
   Run this command ./car-adverts-api-0.1/bin/car-adverts-api server config/config.yml


# Database
The App is build on top of postgress .
You don have to install postgres. I have a postgres instance running on AWS . Please feel free to use it .
The require schema is already created using flyway . You dont have to do anything
the details of the postgres can be seen in the config.yml

Below are sample requests

# Create Advertisement Http method - PUT

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

valid sortkeys are  (id,title,price,mileage,firstReg,fuelType)

valid sortOrder are (desc,asc)

# Delete Advertisement Http method - DELETE

http://localhost:8000/advertisement/e331f42e-10d3-4626-9713-077237d5542a
