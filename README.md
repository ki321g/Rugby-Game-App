# Rugby Score: Mobile Applications Assignment #2
###### Mobile Application Development , HDip in Computer Science
This repository contains my submission for the Mobile Applications Assignment 2 which was to the update the Full Stack 2 labs Movie Application.

__Name:__ Kieron Garvey

__Video Demo:__ 


## FUNCTIONALITY
### Google APIs Used

This project leverages the following Google APIs to enhance its functionality:

**1. Google Play Services**

Google Play services provides the foundation for integrating various Google APIs, including Firebase Authentication and Cloud Firestore. It ensures seamless communication between the app and Google services.

**2. Firebase Authentication**

[Firebase Authentication](https://firebase.google.com/docs/auth) was used to provide secure user authentication within the app. Users can sign in with their email and password, ensuring data privacy and personalized experiences. This API simplifies user management and streamlines the login process.

**3. Firebase Cloud Firestore**

[Firebase Cloud Firestore](https://firebase.google.com/docs/firestore) is employed as the backend database for storing and managing game data. This NoSQL database allows for real-time data synchronization, enabling features like live score updates and efficient data retrieval.

**4. Firebase Storage**

[Firebase Storage](https://firebase.google.com/docs/storage) is used to store and retrieve users profile. This service provides secure and reliable storage, allowing me to use there profile image within the application.

## UML & Class Diagrams

[![Rugby Score UML Class Diagram][rugby_score_uml]](readme/rugby_score_uml.png)

## APPROACH ADOPTED
In this Assignment, I moved away from the Traditional Approach used in Assignment one to Jetpack Compose, which offers a more modern and declarative way to build UIs. I liked using Jetpack compose when i finally started updating the live previews. I also noticed on my Android Device that sometimes there was instant updates, leading to faster development. I couldnt figure out how or why this was happening but it happened a good few times. 

## GIT APPROACH ADOPTED
In this Assignment I wanted to use a structured branching strategy that supports clean, maintainable code development.

### Core Branches
- `main`: Production-ready release branch
- `dev`: Development  branch
- `refactor/gamecard`: Game card component improvements
- `refactor/details`: Detail view enhancements
- `refactor/model`: Data model refinements
- `refactor/gameviewmodel`: ViewModel architecture updates
- `refactor/cleanup`: Code cleanup and optimization
- `refactor/remove`: Legacy code removal 
- `refactor/update`: General updates and improvements

### Benefits
- Clean separation of updates
- Systematic refactoring approach
- Clear feature isolation
- Maintainable development workflow



## PERSONAL STATEMENT
I struggled for a while with this assignment going from the traditional Mobile Development of the previous assignment to Jetpack Compose took me a while to get through. 

I tried to start the assignment early by converting **Lab A13 DonationX-V5** into my own application with its own name. I spent several hours numerous times trying to convert this but just couldn't get my head around it so i started building my own app from LAB#1  When i got to the same LAB as above rather than spend hours figuring out what was changed i attempted to convert the lab to my own again after an hour i asked CODY AI to suggest what i had left to convert it and I managed to do it. 

This isn't my best work and i didn't hit many of the Excellent level band items this time as i ran out of time in the end but im happy with what i have submitted.

## AI DECLARATION
I wanted to make it clear that I did use AI during this project, I used AI to help me with:
+ __Troubleshooting__: I would copy errors from Failed Builds and paste them into GEMINI to try figure out what i needed to change if it wasn't clear. It was very useful most of the time but sometimes lead to other issues.
+ __Building Boiler Plates__: I used CODY in Visual Studio to give me ideas on how to build some of the Composable's. What it gave me back was used as the starting point to building some Composable's.
+ __Inspiration__: I was lost a few times trying to figure out what to do and just asked for AI for its opinion. Telling it what i was doing and what i like it came back with a few nice helpful suggestions to get me going. I used Cody to find [PlantUML](https://plantuml.com/) & [Umbrello - The UML Modeller](https://uml.sourceforge.io/) to help me build the UML Class Diagrams


<!-- MARKDOWN LINKS & IMAGES -->
[rugby_score_uml]: readme/rugby_score_uml.png