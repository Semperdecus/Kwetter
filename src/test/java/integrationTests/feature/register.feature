Feature: User registration
As a user I want to register an account to access all features in kwetter

  Scenario: Create an account
    Given I want to create an account
    When I submit my account information
    Then The status code is 200
