Number     |                                Checkname                                | Status |
|:--------:|:-----------------------------------------------------------------------:|:------:|
|    1     |         For Id from 1 to 10 test-order-controller returns SC_OK         | passed |
|    2     |  For Id not from 1 to 10 test-order-controller returns SC_BAD_REQUEST   | passed |
|    3     |               Correct creation of an order returns SC_OK                | passed |
|    4     | Creation of an order without a header returns SC_UNSUPPORTED_MEDIA_TYPE | passed |
|    5     |           For Id from 1 to 10 response contains status : OPEN           | passed |

This is the test of POST - /test-orders and GET - /test-orders/{id}
Checking of the status codes, GET response

Used junit-jupiter-engine 5.9.2, rest-assured 4.1.2


PET Store GET method order by id:

Number     |                 Checkname                  | Status |
|:--------:|:------------------------------------------:|:------:|
|    1     |      Existing order Id  returns SC_OK      | passed |
|    2     |  Placed order id returns status "Placed"   | passed |
|    3     | Non-existing order id returns SC_NOT_FOUND | passed |
|    4     |   Completed orders return completed=true   | passed |



| Number |                                      Checkname                                      | Status |
|:------:|:-----------------------------------------------------------------------------------:|:------:|
|   1    |                 It's possible to login with correct login/password                  | passed |
|   2    |        Sign in button is active then fields login and password are filled in        | passed |
|   3    |                        Login field has the grey mask "login"                        | passed |
|   4    |                     Password field has the grey mask "password"                     | passed |
|   5    |                       Symbols in password field has the mask                        | passed |
|   6    |      If the login field has less than 2 symbols there is a notification below       | passed |
|   7    |       If the password field has less than 8 symbols there is a notification below   | passed |
|   8    |       Incorrect credentials login causes the "Incorrect credentials" message        | passed |


| Number |        Element        |                            Xpath                             |
|:------:|:---------------------:|:------------------------------------------------------------:|
|   1    |      Login field      |             //input[@data-name="username-input"]             |
|   2    |    Password field     |             //input[@data-name="password-input"]             |
|   3    |     Signin button     |             //button[@data-name="signIn-button"]             |
|   4    |   Span input error    |          //span[@data-name="username-input-error"]           |
|   5    | Incorrect credentials |         //div[@data-name="authorizationError-popup"]         |
|   6    |  Popup close button   | //button[@data-name="authorizationError-popup-close-button"] |
