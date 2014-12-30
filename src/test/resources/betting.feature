Feature: Betting Features


Scenario: Successful bet on free account

Given I am a user with a free account
When I try to place a bet of 5 euros
Then I should be told the bet was successfully placed


