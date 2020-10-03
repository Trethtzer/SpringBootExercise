# Spring Boot Test Exercise

This repository contains a code exercise in which a model of an account service will be created according to REST guidelines. It should be able to create, access, find accounts and to be able to transfer money between them.

## Overview

An account seen by a REST client has the following details:

- Name: String
- Currency: Currency
- Balance: Money
- Treasury: Boolean

The requirements are the following:

- Treasury property can be set only at creation time.
- Only treasury accounts (Treasury property) can have a negative balance.
- Non treasury accounts should block transfers if there is not enough balance.

## Decisions

I used H2 embedded database to store the data, which is destroyed after the application terminates.

Currency will be stored as an integer. 
Ideally in a real database we would have a table which maps this integers with the currency table. In this table we could find Id, Name and ratio (to dollars, for exmaple). This way, if we want to convert from euro to won, we use dollar to euro ratio and dollar to won ratio.
For the sake of simplicity we will not convert money from different currencies when making transfers, since it is not the objective of this exercise.

We will have balance to be declared as float and let our embedded database to use the default type.
However, in a real database we would use decimal, as it is the safest pick if we are going to do a lot of operations with the money.

Although it is not strictly necessary to do the operations requested, I will also add a put and delete method in order to be consistent. 
In the put method I will simply ignore the treasury value if given. 
If the amount of money is minor to zero and treasury is false I will also ignore the value given. Another option could be to send bad request and update nothing.

If an account is attempted to be created with negative money and treasure = false, the creation will fail.

## Testing

You can download a postman collection I created in order to avoid spelling mistakes / manually create all of them. You should change the parameters though, in order to play and test the calls. There is 6 calls.

1. GET 1: Get operation to get all the accounts in the database.
2. GET 2: Get operation with id to find specific account.
3. POST 1: Post operation to create an account.
4. PUT: Put operation to update an account.
5. DELETE: Delete operation to delete an account.
6. POST 2: Post operation to make a transfer.