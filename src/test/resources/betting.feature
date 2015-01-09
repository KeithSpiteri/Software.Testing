Feature: Betting Features

Scenario: Successful Registration

Given I am a user trying to register
When I register providing correct information
Then I should be told that the registration was successful

Scenario Outline: Incorrect registration data

Given I am a user trying to register
When I fill in a form with correct data
And I change the "<fieldname>" field to have incorrect input
Then I should be told that the data in "<fieldname>" is incorrect

Examples:

|fieldname|
|pass|
|name|
|surname|
|date|
|CCN|
|expiry|
|CVV|


Scenario: Successful bet on free account

Given I am a user with a free account
When I try to place a bet of 5 euros
Then I should be told the bet was successfully placed

Scenario: Verify maximum bets for free accounts

Given I am a user with a free account
When I try to place a bet of 5 euros
Then I should be told the bet was successfully placed
When I try to place a bet of 5 euros
Then I should be told the bet was successfully placed
When I try to place a bet of 5 euros
Then I should be told the bet was successfully placed
When I try to place a bet of 5 euros
Then I should be told that I have reached the maximum number of bets

Scenario: Verify maximum cumulative bet for premium accounts

Given I am a user with a premium account
When I try to place a bet of 5000 euros
Then I should be told the bet was successfully placed
When I try to place a bet of 1 euros
Then I should be told that I have reached the maximum cumulative betting amount

Scenario: Verify access restrictions for guest users

Given I am a user who has not yet logged on
When I try to access the betting screen
Then I should be refused access

Scenario Outline: Verify that free users can only place low-risk bets

Given I am a user with a free account
When I try to place a "<risk-level>" bet of 5 euros
Then I should be "<expected-result>" to bet

Examples:

|risk-level	|	expected-result	|
|Low		|	Allowed			|
|Medium		|	Not Allowed		|
|High		|	Not Allowed 	|
