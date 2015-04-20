# scalac_r

#### solution for 7×7 board with 2 Kings, 2 Queens, 2 Bishops and 1 Knight
3 063 828 of unique configurations - calculated in 56s

##### for printing out all solutions 

from `sbt` run comand

`run --N 7 --M 7 -f K=2,Q=2,B=2,N=1`


##### for calculating number of unique configurations

from `sbt` run comand

`run --N 7 --M 7 --countOnly -f K=2,Q=2,B=2,N=1`


#### Program help 
```
example call for printing solutions:
 [sbt] run --N 4 --M 4 -f Q=2,N=1
 [cli] java -jar <jar_file.jar> --N 4 --M 4 -f Q=2,N=1
 
example call for counting solutions:
 [sbt] run --N 7 --M 7 -c -f K=2,Q=2,B=2,N=1
 [cli] java -jar <jar_file.jar> --N 7 --M 7 -c -f K=2,Q=2,B=2,N=1

Usage: chess [options]

  --M <value>
        M is height of the board
  --N <value>
        N is width of the board
  -c | --countOnly
        instead of printing all results prints only number of results, 
        and amount of time it takes to calculate results
  -f <figure_name>=<count> | --figures <figure_name>=<count>
        Figure name followed by count, single figure can occur only once 
        example: -f K=1,Q=2...
        available figure names[N, Q, B, K, R]
        N - Knight
        Q - Queen
        B - Bishop
        K - King
        R - Rook

  --help
        prints this usage text

```
