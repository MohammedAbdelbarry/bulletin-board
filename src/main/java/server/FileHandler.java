package server;

import common.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileHandler implements RemoteHandler {
    private ReadWriteLock rwLock;
    private AtomicInteger rNum;
    private AtomicInteger sSeq;
    private AtomicInteger rSeq;

    private Random random;
    private String fileData;
    private List<String> readLogs;
    private List<String> writeLogs;
    private static final int SLEEP_DURATION = 10000;

    
    public FileHandler() {
        rwLock = new ReentrantReadWriteLock(true);
        rNum = new AtomicInteger();
        sSeq = new AtomicInteger();
        rSeq = new AtomicInteger();
        random = new Random();

        readLogs = new Vector<>();
        writeLogs = new Vector<>();
        fileData = "-1";
    }

    public int getNumRequests() {
        return sSeq.get();
    }

    public void log() {
        System.out.println("sSeq\toVal\trID\trNum");
        for (String readLog : readLogs) {
            System.out.println(readLog);
        }
        System.out.println();
        System.out.println("sSeq\toVal\trID");
        for (String writeLog : writeLogs) {
            System.out.println(writeLog);
        }
    }

    public Response read(int rid) {
        int curRSeq = rSeq.incrementAndGet();
        rwLock.readLock().lock();
        int curRNum = rNum.incrementAndGet();
        try {
            Thread.sleep(random.nextInt(SLEEP_DURATION));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // TODO: read from file;
        String data = fileData;

        rwLock.readLock().unlock();
        rNum.decrementAndGet();
        int curSSeq = sSeq.incrementAndGet();

        // TODO: log
        readLogs.add(curSSeq + "\t" + data + "\t" + rid + "\t" + curRNum);

        return new Response(curSSeq, curRSeq, data);
    }

    public Response write(int wid, String data) {
        int curRSeq = rSeq.incrementAndGet();
        rwLock.writeLock().lock();
        try {
            Thread.sleep(random.nextInt(SLEEP_DURATION));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // TODO: write to file;
        fileData = data;

        rwLock.writeLock().unlock();
        int curSSeq = sSeq.incrementAndGet();

        // TODO: log
        writeLogs.add(curSSeq + "\t" + data + "\t" + wid + "\t");

        return new Response(curSSeq, curRSeq);
    }
}
