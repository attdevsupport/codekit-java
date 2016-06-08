# AT&T Codekit

## Description

The AT&T Java Codekit allows you to interact with AT&T's APIs.

## Requirements

- Java JRE 1.6+
- Java JDK 1.6+
- Maven 

## Usage

The Codekit uses maven for managing the build process; therefore, the
documentation at [maven's website](https://maven.apache.org/) can be used for
understanding the Codekit's project structure.

All of following commands must be executed from the codekit directory.
#### Maven Project

If your project is a maven project, you can use maven to take care of linking
the jar file. First install the Codekit by running the following command from
the codekit directory:

    mvn clean install

Then add the following to your pom.xml's dependency section (replace version
with the latest version):

    <dependency>
        <groupId>com.att.api</groupId>
        <artifactId>codekit</artifactId>
        <version>1.0</version>
    </dependency>

#### Non-maven Project

To use the Codekit, it must first be compiled. This can be done via command
line by running (from the codekit directory):

    mvn clean package

After the command is executed, a jar file (e.g. codekit-1.0.jar) will be
generated in the 'target' folder. The jar may then be used by linking it in
your project.

## Example Code

Example code is located in the examples folder. Each example is a stand-alone
application that uses Maven for the build process. Each example must be
modified to include values, such as an application key, by editing the source
file. The source file contains comments that describe which parts need to be
modified.

For example, to run the sms example:  
Switch the working directory to examples/sms (e.g. `cd examples/sms`). 
Modify the src/main/java/com/att/example/App.java file.  
Run maven to generate the jar (e.g. `mvn clean package`).  
Run the compiled jar (e.g. `java -jar targets/sms-1.0-SNAPSHOT.jar`).  

## Test Code

Test code can be found in the 'codekit/src/test' folder. The folder contains both
unit and integration tests. To run the tests, the follow command may be used
(from the codekit directory):
    mvn clean verify

## Documentation

The Codekit contains inline documentation, which can be generated using the
`mvn` command. To generate documentation, run (from the codekit directory):

    mvn javadoc:javadoc

After the command is executed, the documentation will be in the
'codekit/target/apidocs' folder and can be accessed using a web browser.

## Coding Standards

The Codekit follows [Sun's coding conventions](http://www.oracle.com/technetwork/java/codeconv-138413.html).
