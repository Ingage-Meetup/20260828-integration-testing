# Integration Testing - Part 2

## Manual Test

- If you haven't already, run the application and test the `/users/nearBy` API.
- You should get an exception even though your unit tests pass.
- **Why?**
- Because the stub in the unit tests doesn't accurately model the response from the `https://jsonplaceholder.typicode.com` API.

## Integration Testing

Your next task:

**Write integration tests again the `https://jsonplaceholder.typicode.com` API.**

- Bonus points if your integration test works in an offline mode.
- Use AI and other supporting resources as much as you like!
- _... For your information; yes, I _did_ write the code in a somewhat odd manner to tee-up this kata._ 

## Next

- When you're ready, you can go to the next part.
