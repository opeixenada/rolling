# Time series calculations in a rolling window

The program performs analysis of price ratios. It accepts a path to a file containing time 
series on a local file system as a command-line argument and prints out results of analyses to 
the standard output. 

The length of the rolling time window is 60 seconds.

### Launch

```
sbt "run <path to input file>"
```

### Tests

```
sbt test
```
