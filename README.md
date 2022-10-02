# SecuriThings

Automation test for website: http://automationpractice.com/index.php

## How to
- clone this repo

- In details.json (inside JSONFiles directory) change the sections (file has already test inputs).
- Special notes for some of the sections:

  - At least 2 items are required for the automation to work
  - You must register at least one phone number!
  
```json
    
    "signNewsletters" : true or false
    "recieveSpecialOffers" : true or false
    "email" : a valid email (@something.something)
    "gender" : male or female
    "company" : NOT REQUIRED
    "address" : Street address, P.O. Box, Company name, etc.
    "isUSA" : if you live in the USA write "true" else "false"
    "postalCode" : 00000 format
    "secondAddress" : NOT REQUIRED
    "additionalInfo" : NOT REQUIRED
    
    "day" : number between 1-31,
    "month" : number between 1-12,
    "year" : 4 digits year
```
 - ```order``` in details.json file is an array of json objects. you can add items to purchase using this format:
```json
  {
    "name" : item name,
    "quantity" : amount you want from the same item,
    "color" : item color,
    "size": choose size between S M and L (small, medium or large)
  }  
```
  - ```payment``` can be ```check``` or ```bank```
