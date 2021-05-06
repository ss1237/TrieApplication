# Trie Application

Trie is a SpringBoot Application that exposes various REST endpoints to perform the following operations:

1\. Add keyword to trie

```
GET /add?value=abcd
```

The endpoint supports additions of one value at a time.  
Successful additions return: *abcd was added to the trie.*  
Duplicate entries are not stored.
Duplicate additions return: *abcd is already in the trie.*  
	
2\. Delete a keyword from trie

```
GET /delete?value=abcd
```

The endpoint supports deletions of one value at a time.  
Successful deletions return: *abcd was deleted from the trie.*  
Entries not found in the trie return: *abcd was not found in the trie.*

3\. Search for a keyword in trie

```
GET /search?value=abcd
```

The endpoint can search for one value at a time.  
Successful searches return: *abcd was found in the trie.*  
Unsuccessful searches return: *abcd was not found in the trie.*

4\. Return list of autocomplete suggestion based on an input prefix

```
GET /prefix?value=a
```

The endpoint can search for words in the trie that have a certain prefix.  
Successful searches return: *Words starting with a: [ab, abcd]*  
Unsuccessful searches return: *Words starting with a: []*  
The sets are always arranged in alphabetical order.

5\. Display the trie

```
GET /display
```

The endpoint displays all of the words in the trie.  
Successful display returns: *Contents of the trie: [abcd, bcd, cd]*  
An empty trie returns: *Contents of the trie: []*

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `value` | `string` | **Required**. The input string. It supports alphanuermic characters and certain special characters. Supported characters: !@$*()-_=;:'"><,.?/~  |

The Trie maintains the global state and supports multiple concurrent users. 

## Dependencies

- Java 1.8

- SpringBoot 2.1.8.RELEASE

- Maven 3.8.1

- JUnit

## Setup Apache Maven

1. Download the latest version of Maven and follow the steps given in [maven.apache.org](http://maven.apache.org/download.cgi).
2. Update the system environment variable "Path" to include the Maven installation bin directory.
3. Run the below command in your terminal to print the version of Maven that is installed.

```
mvn version
```

## Build the Application

1. Download the project from the GitHub repository.
2. Open the project in an IDE such as Eclipse.
3. Right click the pom.xml file and update Maven dependencies.
4. Open the terminal and navigate to the root of the project directory.
5. Then, run the below command.

```
mvn clean install
```

## Run the Application Locally

Use the same terminal from above to execute the following command.

	mvn spring-boot:run


## Test the Application

**Terminal (CLI)**

You can use any terminal to run the following commands locally after running the application as shown above.

1\. Add keyword to trie

	curl --location --request GET "http://localhost:8080/add?value=abcd"


2\. Delete a keyword from trie

	curl --location --request GET "http://localhost:8080/delete?value=abcd"


3\. Search for a keyword in trie

	curl --location --request GET "http://localhost:8080/search?value=abcd"


4\. Return list of autocomplete suggestion based on input prefix

	curl --location --request GET "http://localhost:8080/prefix?value=a"


5\. Display the trie

	curl --location --request GET "http://localhost:8080/display"


**Web Browser**

Use the following links to access the trie through any web browser.


1\. Add keyword to trie

	http://localhost:8080/add?value=abcd


2\. Delete a keyword from trie

	http://localhost:8080/delete?value=abcd


3\. Search for a keyword in trie

	http://localhost:8080/search?value=abcd


4\. Return list of autocomplete suggestion based on input prefix

	http://localhost:8080/prefix?value=a


5\. Display the trie

	http://localhost:8080/display



## Run Test Suite From Terminal

The test suite will automatically run when the application is being built. To run the tests separately, execute the following commands from the same directory of the terminal as above (root of the project). These will be automatic tests that check various operations for each class.

**TrieController**: All of the operations that the tree performs.

	mvn -Dtest=TrieControllerTest test

**TrieApplication**: Basic application launch.

	mvn -Dtest=TrieApplicationTest test



## Hosting the Application in AWS

1. Create an Amazon Web Services account.
2. Create a new environment using the service Elastic Beanstalk.
3. Detailed instructions on environment configuration settings can be found below [3].
4. Upload and deploy the jar file located in the target folder of your project to the environment.
5. The application will be accessible once the environment Health is Ok. 
6. To access your Trie Application, use the below commands with your environment endpoint.

## Reference Implementation

A sample environment has been created and is currently running the Trie Application. Multiple concurrent clients can use the application through the following curl commands.

1\. Add keyword to trie

	curl --location --request GET "http://sas-trie.us-east-1.elasticbeanstalk.com/add?value=abcd"


2\. Delete a keyword from trie

	curl --location --request GET "http://sas-trie.us-east-1.elasticbeanstalk.com/delete?value=abcd"


3\. Search for a keyword in trie

	curl --location --request GET "http://sas-trie.us-east-1.elasticbeanstalk.com/search?value=abcd"


4\. Return list of autocomplete suggestion based on input prefix

	curl --location --request GET "http://sas-trie.us-east-1.elasticbeanstalk.com/prefix?value=a"


5\. Display the trie

	curl --location --request GET "http://sas-trie.us-east-1.elasticbeanstalk.com/display"


## Troubleshooting Issues

1. If there are any missing dependencies when building the project, make sure to do Maven -> Update.
2. If your application is not healthy in AWS Elastic Beanstalk, check for the errors in the logs section. The security role should be configured as given below [3].
3. If the AWS environment throws a "502 gateway error" when you invoke the online server, go to Configuration -> Software. There should be an environment property named SERVER_PORT with value 5000.

## References

1. [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
2. [Unit Testing with Spring Boot and JUnit](https://www.springboottutorial.com/unit-testing-for-spring-boot-rest-services)
3. [Deployment with Elastic Beanstalk](https://aws.amazon.com/blogs/devops/deploying-a-spring-boot-application-on-aws-using-aws-elastic-beanstalk/)

## Author

Saathvik Selvan

## Revision

1.0
