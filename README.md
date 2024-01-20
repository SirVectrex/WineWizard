# WineWizard

![Flaschi die Flasche](src/main/resources/static/images/flaschi_die_flasche.png)

WineWizard is a comprehensive wine management application developed using Java, SQL, Spring Boot, and Maven. It allows users to browse through a collection of wines, rate them based on taste, design, and price, and manage their own winery.

## Features

- **Wine Browsing**: Browse through our extensive wine collection and find the best wine for you. The list is ordered by the best tasting wine according to our community.
- **Wine Rating**: Rate wines based on their taste, design, and price.
- **Winery Management**: Manage your own winery, add wines to your collection, and see how they are rated by the community.

## Setup

To set up the WineWizard application, follow the steps below:

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Run the command `mvn clean install` to build the project.
4. After the build is successful, run the command `java -jar target/wine-wizard-0.0.1-SNAPSHOT.jar` to start the application.
5. The application will be accessible at `http://localhost:8080`.

## Localization

The application supports multiple languages. The language can be changed by modifying the `messages_en.properties` file located in the `src/main/resources` directory. Here is an example of how to change the language to German:

```ini
Home = Zuhause
Wine_Wizard = Wein Zauberer
Winery = Weingut
Welcome_to_the_Wine_Cellar_desc = Willkommen im Weinkeller. Durchsuchen Sie unsere Weinsammlung und finden Sie den besten Wein für Sie. Die Liste ist nach dem besten Wein unserer Gemeinschaft geordnet.
``` 

## Background

This project is part of a university course at the University of Applied Sciences Regensbrug. The goal of the project was to develop a comprehensive application using Java, SQL, Spring Boot, and Maven. The application should be able to read and write data from a database, and should be able to handle user input. The application should also be able to handle multiple users at the same time.

