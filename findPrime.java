import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.io.*;

public class findPrime {

    // define prime number trackers
    public final int maxVal = 100000000;
    public AtomicInteger primeCounter = new AtomicInteger(0);
    public AtomicInteger numDistributer = new AtomicInteger(2);
    public AtomicLong primeSum = new AtomicLong(0);
    public AtomicIntegerArray isPrime = new AtomicIntegerArray(maxVal+1);

    public List<findPrimeThread> threads;

    public static void main(String[] args) {
        
        findPrime program = new findPrime();
        program.threads = new ArrayList<>();

        // create 8 threads
        for(int i=0; i<8; i++){
            findPrimeThread th = new findPrimeThread(program);
            program.threads.add(th);
        }
        // start time
        long startTime = System.currentTimeMillis();

        // start the program
        for(findPrimeThread th: program.threads){
            th.start();
        }

        // wait for all threads to end
        for(findPrimeThread th: program.threads){
            try{
                th.join();
            }
            catch(Exception e){
                System.out.println(e);
            }
        }

        // end time
        long endTime = System.currentTimeMillis();
        long exeTime = endTime - startTime;

        File f = new File("primes.txt");
        try {
            f.createNewFile();
            FileWriter fileWriter = new FileWriter("primes.txt");

            fileWriter.write(exeTime + "ms " + program.primeCounter.get() + " " + program.primeSum.get() + "\n");
            
            int cnt = 0;
            for(int i=program.maxVal; i>0; i--){
                if(program.isPrime.get(i)!=0){
                    fileWriter.write(i + " ");
                    cnt++;
                }
                if(cnt==10) break;
            }

            fileWriter.close();
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    


}

class findPrimeThread extends Thread {
    private findPrime program;
    
    public findPrimeThread(findPrime program){
        this.program = program;
    }

    @Override
    public void run(){
        
        while(program.numDistributer.get() <= program.maxVal){
            // take current number, increment numDistributer
            int curNum = program.numDistributer.getAndIncrement();
            // System.out.println("processing " + curNum);

            if(curNum <= program.maxVal){
                // check the primality
                boolean res = true;
                if(curNum <= 1){
                    res = false;
                }
                else if(curNum <= 3){
                    res = true;
                }
                else if(curNum % 2 == 0 || curNum % 3 == 0){
                    res = false;
                }
                else{
                    for(int i = 5; i * i <= curNum; i = i + 6){
                        if (curNum % i == 0 || curNum % (i + 2) == 0){
                            res = false;
                        }
                    }
                }                

                // if it is a prime
                if(res){
                    program.primeCounter.incrementAndGet();
                    program.primeSum.addAndGet(curNum);
                    program.isPrime.incrementAndGet(curNum);
                }
                
            }
        }
    }
}