Feature: Verify API response
  This feature verifies the API response status and checks the structure of the returned data.

  Scenario Outline: Verify API response code from URL
    Given User navigates to "<endpointKey>"
    When User checks the response code
    Then the response code should be <expectedResponseCode>

    Examples:
      | endpointKey                                       | expectedResponseCode |
      | https://automationexercise.com/api/productsList   | 200                  |


  Scenario Outline: Verify the content type of the page
    Given User navigates to "<endpointKey>"
    When User checks the contentType
    Then content-type should be "<contentType>"

    Examples:
      | endpointKey                                           | contentType               |
      | https://automationexercise.com/api/productsList       | text/html; charset=utf-8  |



  Scenario Outline: the file scheme
    Given User navigates to "<endpointKey>"
    When User checks markup
    Then markup should contain "<fieldName>"


    Examples:
      | endpointKey                                               | fieldName                           |
      | https://automationexercise.com/api/productsList           | products                            |
      | https://automationexercise.com/api/productsList           | products.id                         |
      | https://automationexercise.com/api/productsList           | products.name                       |
      | https://automationexercise.com/api/productsList           | products.price                      |
      | https://automationexercise.com/api/productsList           | products.brand                      |
      | https://automationexercise.com/api/productsList           | products.category                   |
      | https://automationexercise.com/api/productsList           | products.category.category          |
      | https://automationexercise.com/api/productsList           | products.category.usertype          |
      | https://automationexercise.com/api/productsList           | products.category.usertype.usertype |

