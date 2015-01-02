Feature: Betting Features



Scenario Outline: Verify that free users can only place low-risk bets

Given I am a user with a free account
When I try to place a "<risk-level>" bet of 5 euros
Then I should be "<expected-result>" to bet

Examples:

|risk-level	|	expected-result	|
|Low		|	Allowed			|
|Medium		|	Not Allowed		|
|High		|	Not Allowed 	|
