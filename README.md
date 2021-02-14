## Banking-System-app
Banking-System

## INSTALLATION

1. Clone the repo in your own computer, or download a zip with the project.
2. Fill /src/main/resources/application.properties with your data (database, user and password).
3. A .sql file is provided if you need to create the necessary tables in your system.

## USAGE

1. There are three controllers (Admin, Savings and Account) with different URL.
2. You can add data to your database and check with Postman (or a similar API service) how the permission roles work (https://www.postman.com/downloads/).
3. There are three users:
  
  ##Admin
  Can do basically everything (checking all balances, add or remove money, create new users etc.).
  They can't do transferences, unless they have an account assigned.
  
  ##Account-Holder
  They can do transferences with other account holders or with third party users.
  They can also receive money from thir party users.
  They can check their own account balances.
  
  ##Third-Party
  They can only send or receive money (they don't have an account ownership)
  

Each account have different conditions (monthly fees, interest rates etc)

I encourage you to try the different functionalities and see if you can skip the fraud conditions.
  
  
Developed by Paul Barroso Gomez 
