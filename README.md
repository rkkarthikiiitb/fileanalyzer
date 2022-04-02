# PDF Analyser Service

A simple java springboot console app that accepts command line arguments as a pdf file
, which is then parsed by the app to extract meaningful information on the console.

## Installation

Use gradlew wrapper to build the project
```bash
./gradlew clean build
```

## Running the app via Intellij IDEA

Place the pdf file in the project root and resources folder. Create a gradle run configuration, leave the tasks entry blank, and copy and paste below command in the "Arguments" section.

```bash
bootRun --args="LINCOLN_CONTRACT.pdf" 
```

## Running the app via Terminal

```bash
bootRun --args="LINCOLN_CONTRACT.pdf"
```

## Further Improvements
- Marrying the extracted filled data with form fields to articulate as key value pairs, ex (CONTRACT OWNER - John Doe,...)
- Provision a database, model entities to store critical document information in the database
- Creating an API to query data/metadata via spring data jpa
- Adding cucumber BDD tests to test the app as a blackbox
- Extend jacoco to cover branches and lines for 95% code coverage
- Add checkstyle and sonar qube static code analysis
- docker compose

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
