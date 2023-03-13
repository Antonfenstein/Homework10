Number     |                              Checkname                               | Status |
|:--------:|:--------------------------------------------------------------------:|:------:|
|    1     |       For Id from 1 to 10 test-order-controller returns SC 200       | passed |
|    2     | For Id not from 1 to 10 test-order-controller returns SC_BAD_REQUEST | passed |
|    3     |             Correct creation of an order returns SC 200              | passed |
|    4     |         Creation of an order without a header returns SC 415         | passed |
|    5     |         For Id from 1 to 10 response contains status : OPEN          | passed |

This is the test of POST - /test-orders and GET - /test-orders/{id}
Checking of the status codes, GET response

Used junit-jupiter-engine 5.9.2, rest-assured 4.1.2


PET Store GET method order by id:

Number     |                      Checkname                      | Status |
|:--------:|:---------------------------------------------------:|:------:|
|    1     |          Existing order Id  returns SC 200          | passed |
|    2     |       Placed order id returns status "Placed"       | passed |
|    3     |     Non-existing order id returns SC_NOT_FOUND      | passed |
|    4     |       Completed orders return completed=true        | passed |
