Feature: Verify API response
  This feature verifies the API response status

  @PostAllProducts
  Scenario Outline: Post Request Verify API response code from URL
  Given User sends POST request to "<endpointKey>"
  When User checks the response code of post
  Then the response code of post should be <expectedResponseCode>

  Examples:
  | endpointKey                                       | expectedResponseCode |
  | https://automationexercise.com/api/productsList   | 200                  |