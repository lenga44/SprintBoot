Feature: Example API Tests

Background:
  * url 'http://localhost:8080'

Scenario: Test GET request
  Given path '/api/hello'
  When method get
  Then status 200
  And match response == { message: 'Hello World' }

Scenario: Test POST request
  Given path '/api/echo'
  And request { text: 'Hello Karate' }
  When method post
  Then status 200
  And match response == { text: 'Hello Karate' }

Scenario: Test PUT request
  Given path '/api/update/1'
  And request { name: 'Updated Name' }
  When method put
  Then status 200
  And match response == { id: 1, name: 'Updated Name' }

Scenario: Test DELETE request
  Given path '/api/delete/1'
  When method delete
  Then status 200
  And match response == { message: 'Deleted successfully' }
