[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://opensource.org/licenses/gpl-3.0)


# EM Inventory Management Software

## Table of contents
1. [Description](#description)
2. [Installation](#installation)
3. [Usage](#usage)
4. [License](#license)
5. [Credits](#credits)
6. [Tests](#tests)
7. [Questions](#questions)
8. [More](#more)

<h2 id="description"> Project Description </h2>
Inventory Management System is a tool that allows the businesses to tracj, organize and oversee all of their inventory, as well as their inventory-related processes; it automates critical tasks, and reduces the chance of human errors. 
The EM Inventory Management Software will allow you crate a new account, and safely sign into the system. Once the user succesfylly signs in, then it has access to the database, and it is able to perform and take advantage of the following tasks: [Usage](#usage) 


## Installation 
Integrated Development Environment (IDE) : IntelliJ IDEA
Latest Long Term Support (LTS) Version of JDK : Java 17
Java FX SDK
Scene Builder
MySQL Workbench
MYSQL JDBC Driver


## Usage 
The EM Inventory Management application provides accesibility to the users so they can manage the data. When someone intents to use the software for the first time, it will be presened with the landing page that has to options: sign in or sign up. 
If the user does not have an account yet, it will be able to fill up a questions form, and create its account.
If the user already has an account, then it will be requested to sign in. Once the user inputs its username and password, then the software validates and if the username name, and password matches, then acces is allowed.
The software counts with a side menu that provides a good user experience with easy navigation between pages.
The software consists of a few pages: 

1. Homepage.
* View of two tables (Parts Table and Products Table) that include the most updated data.
* Parts Table: allows the user to see data, search parts by ID or Name using key release on each table, search parts by Inventory or Price using on click on each table, add new parts, modify existent parts, and delete parts. 
* Products Table: allows the user to see data, search products by ID or Name using key release on each table, search products by Inventory or Price using on click on each table,  add new products, modify existent products, and delete products.
* The user won't be able to continue to modify or delete a part, or modify or delete a product if a row is not selected first.
* The user will need to confirm deletion action in order to delete a part or a product. 
* The user won't be able to delete a product if the product has associated parts.
* If a product has associated parts, then the user will hace to modify the product by removing the associated parts in order to delete the product. 
* The user must select a product or a part in order to navigate to the modify product, or modify part sections.

2. Add Part
* When adding a new part, the user will be requested to input a different name if the desired name is already part of our database.
* A part category must be selected, and all the fields must be filled in order to save a new part.
* The max must be always larger than min, and the inventory level must be equal or more than min and equal or less than max.
* If the user attempts to navigate to other pages before saving the new user, an alert saying that its data hasn't been saved will be prompt, and it must confirm or cancel in order to continue. 
* The user must click save, and confirm the alert in order to save its new part into the EM Inventory Management Database System. Once the save action is confirmed, the user es redirected to the homepage and the new part is visible on the parts tableview.

3. Add Product
* When adding a new product, the user will be requested to input a different name if the desired name is already part of our database.
* The max must be always larger than min, and the inventory level must be equal or more than min and equal or less than max.

* If the user attempts to navigate to other pages before saving the new product, an alert saying that its data hasn't been saved will be prompt, and it must confirm or cancel in order to continue. 
* The user must click save, and confirm the alert in order to save its new product into the EM Inventory Management Database System. Once the save action is confirmed, the user es redirected to the homepage and the new product is visible on the parts tableview.

4. Modify Part
* When modifying a part, the user will be requested to input a different name if the new desired name is already part of our database and it is already associated with another id.

* If the user attempts to navigate to other pages while modifying an existent part, an alert saying that its changes hasn't been saved will be prompt, and it must confirm or cancel in order to continue. 
* The user must click save, and confirm the alert in order to save its updated part into the EM Inventory Management Database System. Once the save action is confirmed, the user es redirected to the homepage and the updated part is visible on the parts tableview.

5. Modify Product
* When modifying a product, the user will be requested to input a different name if the new desired name is already part of our database and it is already associated with another id.

* If the user attempts to navigate to other pages while modifying an existent product, an alert saying that its changes hasn't been saved will be prompt, and it must confirm or cancel in order to continue. 
* The user must click save, and confirm the alert in order to save its updated product into the EM Inventory Management Database System. Once the save action is confirmed, the user es redirected to the homepage and the updated product is visible on the products tableview.



* The following images show the web application's appearance and functionality:

![Landing page](./Assets/landingpage.PNG)
![Loggin](./Assets/loggin.PNG)
![Signup](./Assets/signup.PNG)
![Full post view](./Assets/singlepost.PNG)
![My posts](./Assets/updelcreate.PNG)
![Create post](./Assets/createpost.PNG)
![Edit post](./Assets/updelete.PNG)

## License 
This application is covered under the GNU GPLv3.0 License.

## Credits 
Evelyn G Maldonado.

## Tests 
No tests.

## Questions 
If you have any questions, please contact me to the information listed below.

* Email: evelyn.gmaldonado@gmail.com
* GitHub: [EvelynGMaldonado](https://github.com/EvelynGMaldonado)

## More

* Link to the GitHub Repository:
[Tech Blog](https://github.com/EvelynGMaldonado/tech_blog_MVC)

* Url to the deployed application:
[Tech Blog](https://techblog-egm.herokuapp.com/)
