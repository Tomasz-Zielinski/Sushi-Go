#  Sushi-Go :sushi:

>Coursework sushi system project which consists of client and server graphical user interfaces to interact with each other. Communication layer is done using java implementation of ServerSocket. Server side is able to monitor and modify orders, dishes, ingredients, suppliers, staff, drones, users, postcodes. Staff and Drone instances run in separate threads. System info can be saved and then loaded after closing application. Client is able to register, login, order a dish, view prices checkout basket. Every change is sent to the server with proper response.

##  How to run

Open folder where all the files are and compile class files by typing

```sh

javac *.java

```

Then run server using

```sh

java ServerApplication

```
Now run as many client instances as you would like by typing

```sh

java ClientApplication

```


##  Requirements

- Java 9

##  Features

- Graphical User Interface

- Socket communication

- Stock Management system

- Multithreaded Staff and Drone instances

- Save / Load configuration file

- Login / Register user system