# NutriMeal

## Overview
NutriMeal is an Android food delivery app that has two flavors.<br/>
The customer flavor allows the user to browse the meal catalog,
add them to a cart and then place an order.<br/>
The company flavor allows the food shop to organize the menu and to
receive orders from customers.<br/><br/>
![Meals screenshot](https://raw.githubusercontent.com/LBR2048/nutri-meal/master/images/customer-meals.png)
![Edit meal screenshot](https://raw.githubusercontent.com/LBR2048/nutri-meal/master/images/company-edit-meal.png)

## Use cases

### Customer flavor
* Browse the available meals
* Place an order
* Enter delivery details

![Company use case](https://raw.githubusercontent.com/LBR2048/nutri-meal/master/images/CustomerUseCase.png)

### Company flavor
* Edit the available meals
* See pending orders
* See delivery details

![Customer use case](https://raw.githubusercontent.com/LBR2048/nutri-meal/master/images/CompanyUseCase.png)

# Videos
These are some videos that show how the app works.<br/>
1. [Add widgets video](https://vimeo.com/279857148)
2. [Login video](https://vimeo.com/279857175)
3. [Add new meal video](https://vimeo.com/279857206)
4. [Place orders video](https://vimeo.com/279857240)

## Instructions

### Installation
Install both flavors of the app using Android Studio.<br/>
For a better experience, install each flavor on a different device, so
you can see both interacting in real time.<br/>
For an even better taste of the app capabilities, install the company
flavor on one device and the customer flavor on two devices, but
log with different customer accounts in each of them.

### Logging in
After installing the app flavors, create one user for the company flavor
and at least one user for the customer flavor, but creating at least two
customers is recommended.

You may use these sample users or create your own, by selecting
"Create account" from the app main screen

Company flavor<br/>
owner@mail.com<br/>
123456

Customer flavor (1st user)<br/>
customer1@mail.com<br/>
123456

Customer flavor (2nd user)<br/>
customer2@mail.com<br/>
123456


## Architecture

### MVP classes
The app was created using the MVP (Model View Presenter) architecture,
where each layer has it own responsibility. This allows for a more
modular code that is easier to understand, modify and test.
The diagram below shows in a visual way how all the classes are
connected. The first draft of the diagram was created before writing any
piece of code and was updated to reflect its current state.

![Class diagram](https://raw.githubusercontent.com/LBR2048/nutri-meal/master/images/ClassDiagram.png)

### Data structure
All data was structured as NoSQL trees. Below is an example of how the
the data was organized for the first screen shown on this page.

* meals
    * $meal1
        * name
        * description
        * imagePath
        * unitPrice
        * available


## Libraries used

### Firebase
Firebase Database was used to store all data related to the app<br/>
Firebase Storage was used to store images files<br/>
Firebase Identity was used to authenticate users

### Glide
Glide was used to show all the images. Picasso and Glide are the most
used image download libraries available for Android. They take care of
handling the connection, cache and other requirements.

## Todo
This app is functional, but it can be improved. Here is a todo list for
future updates.

### Both flavors

#### RecyclerView
- [ ] When returning to the app from the background the images briefly
appear in the wrong recycler view items

#### Menu
- [ ] Show logout button only on main screen

#### Login screen
- [ ] Reduce image when keyboard is displayed so that both edit texts
are shown
- [ ] Block all screen while loading
- [ ] App crashes if login or password field are empty
- [ ] Hide keyboard after pressing Login button

### Customer flavor

#### Add meal to cart dialog
- [ ] Improve add meal dialog design
- [ ] Whenever an item is clicked, its quantity is increased by one
- [ ] Cannot delete item

#### Menu
- [ ] Show Orders button on cart screen

#### Orders screen
- [ ] Filter orders by delivery status, date
- [ ] Show order items

### Company flavor

#### Edit meal screen
- [ ] Edit text starts to blink on edit-meal screen when device rotates
- [ ] After editing text, you have to leave it or the edit is not saved
when exiting the screen
- [ ] When clicking an edit-text, the cursor goes to the beginning
- [ ] Change title to “Add new Meal”
- [ ] Add confirmation dialog before deleting meal of snackbar allowing
 to undo last action
- [ ] Add fragment instead of replacing it to allow faster orders screen
 showing
- [ ] Save scroll position

#### Orders screen
- [ ] Add fragment instead of replacing it to allow faster orders screen
 showing
- [ ] Save scroll position
- [ ] Show user name and address instead of user ID
- [ ] Filter orders by delivery status, user, date