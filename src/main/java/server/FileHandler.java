package server;

import common.Response;

import java.util.Random;
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
    
    public FileHandler() {
        rwLock = new ReentrantReadWriteLock();
        rNum = new AtomicInteger();
        sSeq = new AtomicInteger();
        rSeq = new AtomicInteger();
        random = new Random();
        fileData = "";
    }

    public Response read(int rid) {
        int curRSeq = rSeq.incrementAndGet();
        rwLock.readLock().lock();
        int curRNum = rNum.incrementAndGet();
        try {
            Thread.sleep(random.nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // TODO: read from file;
        String data = fileData;

        rwLock.readLock().unlock();
        rNum.decrementAndGet();
        int curSSeq = sSeq.incrementAndGet();

        // TODO: log
        System.out.println(curSSeq + "\t" + data + "\t" + rid + "\t" + curRNum);

        return new Response(curSSeq, curRSeq, data);
    }

    public Response write(int wid, String data) {
        int curRSeq = rSeq.incrementAndGet();
        rwLock.writeLock().lock();
        try {
            Thread.sleep(random.nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // TODO: write to file;
        fileData = data;

        rwLock.writeLock().unlock();
        int curSSeq = sSeq.incrementAndGet();

        // TODO: log
        System.out.println(curSSeq + "\t" + data + "\t" + wid + "\t");

        return new Response(curSSeq, curRSeq);
    }
}
