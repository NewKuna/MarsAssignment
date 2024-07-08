# Example Project #

## How to start the project

build docker image
```
docker build -t mars-assignment:dev .
```

start container
```
docker-compose up -d
```
if you're running on local you can go test a services at http://localhost:8080/swagger-ui/index.html#

* Note: Currently I don't have file server to store image and I didn't use elasticsearch to store file so for now function that are relate to saving or using file are unavailable
