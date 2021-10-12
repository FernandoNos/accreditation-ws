# User Accreditation by Income

The software here provided has the intent to validate an accreditation request. It was not necessary to apply actual accreditation rules, which means that the final result was mocked.

# Stack
The following stack was used:

* Java 11;
* WebFlux 2.5.5;
* JUnit 4.13.1
* Gradle 7.0;
* Linux.

# Design

As a first step, the provided API specification was used to base the development of the application:

`POST /user/accreditation`

**Request**
```
{
   "user_id":"g8NlYJnk7zK9BlB1J2Ebjs0AkhCTpE1V",
   "payload":{
      "accreditation_type":"BY_INCOME",
      "documents":[
         {
            "name":"2018.pdf",
            "mime_type":"application/pdf",
            "content":"ICAiQC8qIjogWyJzcmMvKiJdCiAgICB9CiAgfQp9Cg=="
         },
         {
            "name":"2019.jpg",
            "mime_type":"image/jpeg",
            "content":"91cy1wcm9taXNlICJeMi4wLjUiCiAgICB0b3Bvc29ydCAiXjIuMC4yIgo="
         }
      ]
   }
}
```
**Response**
```
{
   "success":true,
   "accredited":true
}
```

Based on this, and the description of the challenge, a few assumptions were made: 
* In cases where the structure of the message was invalid, a Bad Request HTTP code would be returned;
* If the structure of the message was valid, but its content was not (e.g. the list of documents do not respect the last two years tax return criteria), then both the `success` and the `accredited` field would be `false`;
* No message with the actual problems present in the message were necessary;

# Structure of the project

```
src
├── main
│   ├── java
│   │   └── io
│   │       └── test
│   │           └── accreditation
│   │               ├── adapters
│   │               ├── controllers
│   │               │   ├── dto
│   │               ├── enums
│   │               ├── models
│   │               ├── services
│   │               └── usecases
│   │                   └── accreditation
│   │                       └── byincome
│   │                           ├── handler
│   │                           └── validators
│   └── resources
```
* adapters : responsible for translating the incoming DTOs into the internal representation, as well as mapping the internal models to DTOS;
* controllers: responsible for receiving the requests, and returning the responses, from/to the clients;
* services : responsible for engaging the correct use cases. In this case, there's only one use case, which is the `accreditation by income`, but if there were more scenarios, this layer would be responsible for engaging the correct handlers for each accreditation type;
* usecases.accreditation.byincome.handler: responsible for validating and submitting the request;
* usecases.accreditation.byincome.validators: has all the validation logic, and is used by the handler to decouple the validation logic, from the step where the request is submitted.

The intent of the structure above was to decouple each layer of the solution, so that future changes and have a minor impact on the whole project. 

# Running
In order to run the project, download the repository, place your terminal at the root of the project and run `./gradlew bootRun`.

# Next steps
The following would be the possible future steps for the project here presented: 
1. Review the validation for the date of the documents, and check whether there's a format that should be expected within the files;
1. As of now, the application expects that the documents in the request be in chronological order (e.g. the first would be the 2020.pdf file, followed by 2019.pdf,...). As a possible evolution of the problem, this limitation could be removed, the documents could be in any order;
1. A swagger could be integrated with the application, so that possible clients have an easier time integrating with it;
1. As of now, the validation of the file name is in a regex, within the `application.properties` file. In the future, this could be stored in a configuration server, which would make it easier to update the regex, without having to redeploy the app;


Thanks for the opportunity.


