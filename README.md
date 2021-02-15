## Banking-System-app
Banking-System backend

## INSTALLATION

1. Clone the repo in your own computer, or download a zip with the project.
2. Fill /src/main/resources/application.properties with your data (database, user and password).
3. A .sql file is provided if you need to create the necessary tables in your system.
4. Run the program with < mvn spring-boot:run >
5. An admin user is provided to allow modifcactions on the database (username: admin | password: 1234)

## USAGE

1. There are three controllers (Admin, Savings and Account) with different URL.
2. You can add data to your database and check with Postman (or a similar API service) how the permission roles work (https://www.postman.com/downloads/).
3. There are three users:
  
  ## Admin
  Can do basically everything (checking all balances, add or remove money, create new users etc.).
  They can't do transferences, unless they have an account assigned.
  
  ## Account-Holder
  They can do transferences with other account holders or with third party users.
  They can also receive money from thir party users.
  They can check their own account balances.
  
  ## Third-Party
  They can only send or receive money (they don't have an account ownership). In order to do so they need to provide a hash key in the URL.
 
## URL

| PETITION | ROUTE | USER ROLE | DESCRIPTION
| ------------- | ------------- | ------------- |
| GET  | /admin/account-balance/all/{id}  | ADMIN | Shows all balances for all accounts
| GET  | /admin/info  | ADMIN | Shows info for all admins
| GET  | /admin/savings  | ADMIN | Shows all balances for all savings accounts
| GET  | /admin/savings/check-balance/{id}  | ADMIN | Shows info for one specific savings account
| GET  | /admin/account-balance/all/{id}  | ADMIN | Shows all balances from all accounts
| POST  | /admin/savings/{id} | ADMIN | Creates a new savings account
| POST  | /admin/checkings/{id}  | ADMIN | Creates a new checking account. If the accoun holder is less than 25 yo; a student checking will be created
| POST  | /admin/credit-card/{id}  | ADMIN | Creates a new credit card
| POST  | /new/admin/{id}  | ADMIN | Creates a new admin user
| POST  | /new/account-holder/{id}  | ADMIN | Creates a new account-holder user
| POST  | /new/third-party/{id}  | ADMIN |Creates a new third-party user
| PUT  | /admin/account/increment/{id}/{amount}  | ADMIN | Increments the balance of a given account
| PUT  | /admin/account/decrement/{id}/{amount}  | ADMIN | Decrements the balance of a given account
| POST  | /new-transference/ | ACCOUNT HOLDER | Makes a transfer between two account holders
| POST  | /new-transference/to/{name}/{hashKey} | ACCOUNT HOLDER & THIRD PARTY | Makes a transfer TO a third party
| POST  | /new-transference/from/{name}/{hashKey} | ACCOUNT HOLDER & THIRD PARTY | Makes a transfer FROM a third party
| GET  | /savings/check-balance/{id} | ACCOUNT HOLDER | Shows the balance of the account


Each account have different conditions (monthly fees, interest rates etc)

I encourage you to try the different functionalities and see if you can skip the fraud conditions.
  
  
Developed by Paul Barroso Gomez for Iron Hack Santander BootCamp
