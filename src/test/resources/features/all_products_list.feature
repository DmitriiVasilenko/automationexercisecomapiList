Feature: Verify API response
  This feature verifies the API response status and checks the structure of the returned data.

  Scenario Outline: Verify API response code for different scenarios
    Given the API endpoint is "<endpointKey>"
    When I send a GET request to the API
    Then the response code should be <expectedResponseCode>

    Examples:
      | endpointKey    | expectedResponseCode |
      | test.endpoint  | 200                  |
      | api.endpoint   | 200                  |


  Scenario Outline: Verify the content type of the page
    Given the API endpoint is "<endpointKey>"
    When I send a GET request to the API
    Then content-type should be "<contentType>"

    Examples:
      | endpointKey       | contentType |
      | api.endpoint      | text/html; charset=utf-8   |



#  Scenario Outline: the file structure
#    Given the API endpoint is "<endpointKey>"
#    When I send a GET request to the API
#    Then the structure of the file should be as below
#
#    Examples:
#
#      | key          | type   | description                 |
#      | responseCode | number | HTTP response code          |
#      | products     | array  | An array of product objects |
#
#    And the structure of each product should be as below
#
#    Examples:
#
#      | key      | type   | description                           |
#      | id       | number | Unique identifier for the product     |
#      | name     | string | Name of the product                   |
#      | price    | string | Price in the format of "Rs. <amount>" |
#      | brand    | string | Brand name                            |
#      | category | object | Nested category information           |

