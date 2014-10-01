We are using AspectJ for handling exceptions, logging and transactions. So far as AspectJ is a static weaving that means any changes in aspects requires to re-compile our project.

We can run app using 
    command line:
        mvn clean package exec:java
Or
    IDEA we have to open Maven Projects panel and make the same actions what we do using command line.