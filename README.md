# Dun Bradstreet

## Instructions
- Implement the below assignment and should be runnable without compilation errors / warnings
- Submit a production ready code with all what that means to you
- Any programming language can be used (preferably Core Java / MapReduce)
- Must use a design pattern
- Add necessary doc
- Add Junit test cases
- Maven / Gradle should be fine
- Assignment Code can be uploaded in GitHub / Gitlab
- Must have a readme doc that explains the steps to build and run

## How to's

### Testing
```bash
./gradlew clean test
```

### Running the application
```bash
./gradlew bootRun
```

### Executing load test
```bash
cd gatling-load-test
./gradlew clean gatlingRun
```

## Design decisions

> In order to keep the code as simple, I decided to not use many frameworks 
unless it was justified to address any of the requirements.

> The code has 100% coverage and the volume requirements (1M) was achieved, as you can see int he Gatling report.

> Most of the tests is API tests which covers all scenarios. 
> Initially I implemented few unit tests, but I removed them when they became duplicated. 

> The part of the code responsible by writing json in the file was implemented using Spring @Async
> I decided to make it asynchronous as it is maybe the main bottle neck this application can have

## Metrics

### Code Coverage

Code Coverage:
![code coverage][doc/code-coverage.png]

### Gatling Load Test
Gatling metrics: ![metrics-loadtest](doc/metrics-loadtest/index.html)
```
================================================================================
---- Global Information --------------------------------------------------------
> request count                                    1050000 (OK=1050000 KO=0    )
> min response time                                      0 (OK=0      KO=-     )
> max response time                                    487 (OK=487    KO=-     )
> mean response time                                     3 (OK=3      KO=-     )
> std deviation                                          6 (OK=6      KO=-     )
> response time 50th percentile                          2 (OK=2      KO=-     )
> response time 75th percentile                          2 (OK=2      KO=-     )
> response time 95th percentile                          7 (OK=7      KO=-     )
> response time 99th percentile                         26 (OK=26     KO=-     )
> mean requests/sec                                3763.441 (OK=3763.441 KO=-  )
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                       1050000 (100%)
> 800 ms < t < 1200 ms                                   0 (  0%)
> t > 1200 ms                                            0 (  0%)
> failed                                                 0 (  0%)
================================================================================
```


