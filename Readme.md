# NutriMeal

## Overview
Mary has a small business, she makes frozen meals and deliver them to its customers. As she still has only a few customers, she mostly receives the orders by phone or text messages. But as the the number of customers increase, managing the orders, payments and delivery is starting to get out of hand.
I propose an app with two flavors, one that allows the customers to place orders and another that allows Mary to manage those orders effectively.

![Screenshot1](https://raw.githubusercontent.com/LBR2048/nutri-meal/master/images/main.png)

Videos

## Instructions
To be able to use this app you must have a TMDb API key and insert it in your `local.properties` file. The key must be placed inside double quotes.

    apiKey="<your_api_key>"

## Architecture
The app was created using the MVP (Model View Presenter) architecture, where each layer has it own responsibility. This allows for a more modular code that is easier to understand, modify and test.
The diagram below shows in a visual way how all the classes are connected. The first draft of the diagram was created before writing any piece of code and was updated to reflect its current state.

![Class diagram](https://raw.githubusercontent.com/LBR2048/nutri-meal/master/images/ClassDiagram.png)

## Features

### Customer flavor
* Browse the available meals
* Place an order
* Enter delivery details

![Customer use case](https://raw.githubusercontent.com/LBR2048/nutri-meal/master/images/CompanyUseCase.png)

### Company flavor
* Edit the available meals
* See pending orders
* See delivery details

![Company use case](https://raw.githubusercontent.com/LBR2048/nutri-meal/master/images/CustomerUseCase.png)

## Libraries used

### Firebase
Firebase Database was used to store all data related to the app
Firebase Storage was used to store images files
Firebase Identity was used to authenticate users

### Glide
Glide was used to show all the images. Picasso and Glide are the most used image download libraries available for Android. They take care of handling the connection, cache and other requirements.
