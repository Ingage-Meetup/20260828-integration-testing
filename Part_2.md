# Integration Testing - Part 2

## Runtime Exception

- If you run the application and test the `/nearBy` API, you should get an exception even though your unit tests pass.
- **Why?**
- Because the stub in the unit tests doesn't accurately model the response from the downstream API, `https://jsonplaceholder.typicode.com`.

## Integration Testing

1. Write integration tests again the downstream API `https://jsonplaceholder.typicode.com`
2. Bonus points if your integration test works in an offline mode. (Record/Reply functionality.... I advise wiremock, but I'm open to other ideas!!)

## Notes

- Use AI and other supporting resources as much as you like!
- _... For your information; yes, I _did_ write the code in a somewhat odd manner to tee-up this kata._ 

## Next

- When you're ready, you can go to the next part.
