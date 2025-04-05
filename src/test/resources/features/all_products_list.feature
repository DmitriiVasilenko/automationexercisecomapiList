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



  Scenario Outline: the file scheme
    Given the API endpoint is "<endpointKey>"
    When I send a GET request to the API
    Then the scheme should contain "<fieldName>"


    Examples:
      | endpointKey            | fieldName                           |
      | api.endpoint           | products                            |
      | api.endpoint           | products.id                         |
      | api.endpoint           | products.name                       |
      | api.endpoint           | products.price                      |
      | api.endpoint           | products.brand                      |
      | api.endpoint           | products.category                   |
      | api.endpoint           | products.category.category          |
      | api.endpoint           | products.category.usertype          |
      | api.endpoint           | products.category.usertype.usertype |

