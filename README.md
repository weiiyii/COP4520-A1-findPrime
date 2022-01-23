# COP4520-A1-findPrime

## Installation

1. Navigate to the folder where findPrime.java is located
2. Compile the program by running `javac findPrime.java`
3. Run the program by running `java findPrime`

## Output

A file named primes.text

## Proof of Correctness

The program uses Java atomic variables such as AtomicInteger and AtomicIntegerArray to prevent multiple threads from modifying/overwriting a variable at the same time thus resulting inconsistency of the value. In order for 8 threads to work concurrently, each thread will fetch one number at a time from an atomic integer number distributer and check its primality.

## Experimental Evaluation & Efficiency

My initial approach was to check the primality of a number n by iterating through all numbers from 2 to n/2. The program took too long to process numbers from 1 to 100000000. However after running the program on a smaller number range (1 to 100), I believe I'm implementing the threads in an appropriate way.

Efficiency: > 20 min

Then I started focusing on an optimized way to check primality. And I found trial division which is a simplest primality test. After implementing it, the execution time of the program improved.

Efficiency: ~ 122431 ms
