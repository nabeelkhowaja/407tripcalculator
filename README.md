<a name="readme-top"></a>

<h3 align="center">407 Trip Calculator</h3>

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
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#running-the-application-locally">Running the application locally</a></li>
        <li><a href="#running-the-unit-tests">Running the unit tests</a></li>
      </ul>
    </li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

The project is used to determine the cost of driving a vehicle between two specific points on the 407ETR. The application requires the names of the starting and ending location as the input from user, and subsequently utilize a JSON file to compute the distance between the two points and the associated cost.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


### Built With

* [![Java]][Java-url]
* [![Spring]][Spring-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

For bulding and running the application, you need:

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3](https://maven.apache.org)
- Any Integrated Development Environment ( [IntelliJ](https://www.jetbrains.com/idea/download/#section=mac), [Eclipse](https://www.eclipse.org/downloads/), [Netbeans](https://netbeans.apache.org/download/nb124/nb124.html) etc)

### Running the application locally

1. Clone the repo
   ```sh
   git clone https://github.com/nabeelkhowaja/407tripcalculator.git
   ```
2. Open the 407tripcalculator package in your IDE. This package has 2 more packages, namely cost-calculator-service and distance-calculator-service. 
3. Execute the main method in the CostCalculatorServiceApplication and DistanceCalculatorServiceApplication class from your IDE.

   In IntelliJ IDE, click on the play button left to the main method and select 'Run CostCalculatorServiceApplication.main()' and 'Run DistanceCalculatorServiceApplication.main()' option in the respective classes: 

   <img src="/images/cost-main.png"  width="500" height="400">
   
   <img src="/images/distance-main.png"  width="500" height="300">


   Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:
   ```shell
   mvn spring-boot:run
   ```
4. After both of the applications are running, open any browser and type:
   ```shell
   http://localhost:8010/?startLocation={start_location_name}&endLocation={end_location_name}
   ```
   Here, {start_location_name} and {end_location_name} will be replaced with actual location names. Example:
   
   <img src="/images/browser-execution.png"  width="500" height="150">

<p align="right">(<a href="#readme-top">back to top</a>)</p>


### Running the unit tests

-   In IntelliJ IDE, open CostCalculatorControllerTest and DistanceCalculatorControllerTest class file, and click on the play button next to the class name:

    <img src="/images/cost-unit-test.png"  width="500" height="500">
    
    <img src="/images/distance-unit-test.png"  width="600" height="500">


<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- LINKS & IMAGES -->

[Java]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://www.java.com/en/
[Spring]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io/
[cost-main]: images/cost-main.png
