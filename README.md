# Loan Payback Planner  [![Build Status](https://travis-ci.org/rezaep/loan-payback-planner.svg?branch=master)](https://travis-ci.org/rezaep/loan-payback-planner) [![Coverage Status](https://coveralls.io/repos/github/rezaep/loan-payback-planner/badge.svg?branch=master)](https://coveralls.io/github/rezaep/loan-payback-planner?branch=master) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/0bc7a31b45a649e2ae14f82d2fe7e1b0)](https://www.codacy.com/manual/rezaep/loan-payback-planner?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rezaep/loan-payback-planner&amp;utm_campaign=Badge_Grade)
A simple project based on Java 8, Spring Boot and JUnit.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to run the project 

```
Java Development Kit (JDK 8 or newer)
Maven
Docker (if you want to run the application using the Docker image)
```

### Installing

Clone the repository

```
git clone https://github.com/rezaep/loan-payback-planner
```

Use Maven build tool to compile and build the project.

```
mvn clean compile
```

### Running the tests

#### Unit tests

To run unit tests use the following command.

```
mvn test
```

#### Integration tests

To run integration tests use the following command.

```
mvn verify
```

#### End-To-End tests

To run end-to-end (e2e) tests run following command.

```
mvn verify
```

#### All tests together

To run all tests together run following command.
                          
```
mvn test verify
```

### Running the application

#### Run using Java

To run the application using Java, run following command.

```
java -jar target/loan-planner-1.0.0.jar
```

#### Run using Maven and Spring Boot plugin

To run the application using Spring boot maven-plugin, run following command.
                                                        
```
mvn spring-boot:run
```

#### Run using Docker

To run the application using Docker, run following command.
                                                        
```
docker run image:tag (e.g. rezaep/loan-payback-planner:latest)
```

#### How to generate payback plan

To generate payback plan using a REST client (e.g. cURL) send a POST request to the following URL.

```
URL: http://localhost:8080/loan/plan/generate

Body:
{
  "loanAmount": "5000.00",
  "interestRate": "5.00",
  "duration": 24,
  "startDate": "2018-01-01T00:00:01Z"
}
```

cURL request:

```
curl -X POST \
  http://localhost:8080/loan/plan/generate \
  -H 'Content-Type: application/json' \
  -d '{"loanAmount":"5000.00","interestRate":"5.00","duration":24,"startDate":"2020-01-01T00:00:01Z"}'
```

wget request:

```
wget --quiet \
  --method POST \
  --header 'Content-Type: application/json' \
  --body-data '{"loanAmount":"5000.00","interestRate":"5.00","duration":24,"startDate":"2020-01-01T00:00:01Z"}' \
  --output-document \
  - http://localhost:8080/loan/plan/generate
```

sample JSON response:

```
[
    {
        "date": "2020-01-01T00:00:01Z",
        "initialOutstandingPrincipal": 5000.00,
        "borrowerPaymentAmount": 219.36,
        "interest": 20.83,
        "principal": 198.53,
        "remainingOutstandingPrincipal": 4801.47
    },
    {
        "date": "2020-02-01T00:00:01Z",
        "initialOutstandingPrincipal": 4801.47,
        "borrowerPaymentAmount": 219.36,
        "interest": 20.01,
        "principal": 199.35,
        "remainingOutstandingPrincipal": 4602.12
    },
    ...,
    {
        "date": "2021-12-01T00:00:01Z",
        "initialOutstandingPrincipal": 218.37,
        "borrowerPaymentAmount": 219.28,
        "interest": 0.91,
        "principal": 218.37,
        "remainingOutstandingPrincipal": 0.00
    }
]
```

## Deployment

To package the Jar file inside a Docker image use following commands.

```
mvn clean compile test verify
docker build -t image:tag . (e.g. rezaep/loan-payback-planner:latest)
```

## Authors

* **Reza Ebrahimpour** - [Github](https://github.com/rezaep)

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details