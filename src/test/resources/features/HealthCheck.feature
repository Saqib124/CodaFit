Feature: CodaFit Rest API Automation

  @Regression @HealthCheck
  Scenario Outline: Validate success response json schema
    Given User trigger test API for "<EndPoint>"
    Then  User validate the API status code is "<code>"
    And   Validate the json response with json schema save at "<path>"
    Examples:
      | EndPoint     | code  | path               |
      | candidateAPI | 200   | successJsonSchema  |