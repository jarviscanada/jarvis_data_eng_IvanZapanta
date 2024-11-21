# Introduction
The Java Grep application is a Java version of the `grep` command in Linux. This program can be used to recursively search for a given Regex (regular expression) pattern. It identifies matching lines and allows users to specify an output file to save the results. Additionally, the application is containerized as a Docker image, which users can easily retrieve from DockerHub.
# Technologies Used
The development of this application involves technologies such as:
- Java
- Maven
- Java Regex APIs ( Pattern, Matcher) `java.util.regex`
- Java IO APIs (BufferedReader, BufferedWriter, File FileReader, FileWriter) `java.io`
- Docker
- Logging Frameworks (BasicConfigurator,Logger,LoggerFactory) `log4j` `slf4j`
- IntelliJ IDE (recommended)


# Quick Start
```bash
# Compile the maven project and package the program into a jar file
mvn clean compile project

#Run the Jar file with the three arguments [regex], [rootDirectory], [outputFile to complete the grep search
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp [regex] [rootDirectory] [outputFile]

#View the output file of the matched lines
cat [outputFile]
```
| Argument | Description                             |
|----------|:----------------------------------------|
| regex    | Regex pattern to be used for searching. |
| rootPath | Root directory to recursively search.   |
| outFile  | Output file to save the retrived data.  |

# Implementation
The app requires a process method that defines the operations that are taking place. 

The pseudocode of process() method is as follows:
```bash
matchedLines[]
for file in listFilesRecursively(rootDir)
    for line in readFile(file)
        if containsPattern(line)
            matchedLines.add(line)
writeToFile(matchedLines)
```

## Performance Issue
The app encounters performance problems when handling extremely large files due to its initial approach of storing every line in memory. However, by incorporating Java Stream and Lambda capabilities, as seen in the JavaGrepLambdaImp class, this memory concern is resolved. 

Java Streams enable functional programming like map-reduce transformations on collections without the need to retain data in memory, mitigating the previous memory-related issues.

# Test
All tests were conducted manually using various sample files of different sizes to compare results and enhance read and write efficiency. Additionally, JUnit was used to automate and simplify the testing process, ensuring the accuracy of the application's functionality.

# Deployment
The app was containerized into a Docker image and is available on DockerHub for convenient access and usage. 

To obtain the Docker image and use the application, please follow the provided steps:
```bash
# Pull the image from DockerHub
docker/pull ivanzapanta123/grep

# Run the docker container
docker run --rm -v `pwd` /data:/data -v `pwd` /log:/log ivanzapanta123/grep .[regex] /data /log/grep.out
```

# Improvement
- Create a user-friendly GUI for easier access, especially for non-technical users.
- Offer a library of common regular expressions for simplified search input.
- Provide multiple output options: display results, save to file, or send to other apps.