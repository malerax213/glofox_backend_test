<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>


<!-- ABOUT THE PROJECT -->
## About The Project

This project is a backend technical test for Glofox. It consists of the creation of an API that can correctly manage classes and bookings.

It is implemented in Java, using H2 as database and using Spring as the framework application.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


### Built With

This section should list any major frameworks/libraries used in the project

* Java
* Spring
* H2
* Hibernate
* Lombok

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

In order to execute this project, you have to:

1. Have a machine with Intellij, Java and Spring installed (as it was the used IDE for this development).
2. Import the project with maven
3. Execute the Main Application (the port is 8080) with the run button

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- USAGE EXAMPLES -->
## Usage

Postman has been used in order to test the requests. One example for the save class request would be:

{
        "name": "Pilates",
        "start_date": "2022-08-09",
        "end_date": "2022-08-11",
        "capacity": 15
}

One example for the booking request would be:

{
    "name": "Alex",
    "date": "2022-08-11",
    "aClass": {
        "name": "Pilates",
        "start_date": "2022-08-09",
        "end_date": "2022-08-11",
        "capacity": 15
    }
}

The database console can be checked here: http://localhost:8080/h2-console

The setup for the H2 console is:

- Driver Class: org.h2.Driver

- JDBC URL: jdbc:h2:mem:dcbapp

- User name: sa

- Password: sa

There are also some tests in the GeneralController file, in the ws.controller package (make sure you run them in order,
because the first tests will create some classes that the next ones will assume won't exist)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- CONTACT -->
## Contact

Mihai Alexandru Martinas - alexandrumartinas33@gmail.com

<p align="right">(<a href="#readme-top">back to top</a>)</p>
