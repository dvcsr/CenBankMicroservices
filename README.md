## About v1.2.1
What I've demonstrated in v1.2.1 :
- Implement custom validation
- Implement testing with Junit for customerDto Validation:
  - lifecycle Methods Implementation: @BeforeEach, @AfterEach, @BeforeAll, @AfterAll
- Implement JUnit extensions:
  - extensions: DurationExtension class, LoggingExtension class,  for test lifecycle duration measurement and logging, use with @ExtendWith
- Assertion:
  - assertTrue(), assertFalse(), assertEquals(), assertAll(), assertTimeout(), assertThrows(), assertNotNull(), assertNull()