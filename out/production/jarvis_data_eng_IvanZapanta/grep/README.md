# Introduction
The Java Grep application is a Java version of the **grep** command found in Linux's bash terminal. This program can be used to search for a given regular expression pattern within a root directory, and it operates recursively. It identifies matching lines and allows users to specify an output file to save the results. Additionally, the application is packed as a Docker image, which users can easily retrieve from DockerHub for their own usage. The development of this application involves technologies such as Docker, Java, Lambda and Stream APIs, Maven, and IntelliJ.

# Quick Start
```bash
# Compile the maven project and package the program into a jar file
mvn clean compile project

#Run the Jar file with the three arguments [regex], [rootDirectory], [outputFile to complete the grep search
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp [regex] [rootDirectory] [outputFile]

#View the output file of the matched lines
cat [outputFile]
```
| Argument | Description                            |
|----------|:---------------------------------------|
| regex    | Regex pattern to be used for searching |
| rootPath | Root directory to recursively search   |
| outFile  | Output file to save the retrived data  |

# Implemenation
The Java Grep application requires a process method that defines the operations that are taking place. The pseudocode of process() method is as follows:
```bash
matchedLines[]
for file in listFilesRecursively(rootDir)
    for line in readFile(file)
        if containsPattern(line)
            matchedLines.add(line)
writeToFile(matchedLines)
```

## Performance Issue
The Java Grep Application encounters performance problems when handling extremely large files due to its initial approach of storing every line in memory. However, by incorporating Java Stream and Lambda capabilities, as seen in the JavaGrepLambdaImp class, this memory concern is resolved. Java Streams enable functional programming like map-reduce transformations on collections without the need to retain data in memory, mitigating the previous memory-related issues.

# Test
All tests were conducted manually using various sample files of different sizes to compare results and enhance read and write efficiency. Additionally, JUnit was used to automate and simplify the testing process, ensuring the accuracy of the application's functionality.

# Deployment
The Java Grep application was packaged into a Docker image and made available on DockerHub for convenient access and usage. To obtain the Docker image and use the application, please follow the provided steps:
```bash
# Pull the image from DockerHub
docker/pull ivanzapanta123/grep

# Run the docker container
docker run --rm -v `pwd` /data:/data -v `pwd` /log:/log ivanzapanta123/grep .[regex] /data /log/grep.out
```

# Improvement
- Develop a user-friendly graphical user interface (GUI) for the application to make it more accessible to users who are not comfortable with the command line.
- Allow users to select or input regular expressions from a library of commonly used patterns to simplify the search process.
- Provide various output options, such as displaying results in the console, saving them to a file, or sending them to other applications for further processing.