package ru.job4j.concurrent.threads;

import java.io.*;
import java.net.URL;

public class WgetSpeed implements Runnable {

    private static final class TimeRead {

        private int bytes;
        private int time;

        public TimeRead(int aBytes, int aTime) {
            bytes = aBytes;
            time = aTime;
        }
    }

    private final String url;
    private final int speed;
    private final String outFileName;

    public WgetSpeed(String aUrl, int aSpeed, String aOutFileName) {
        url = aUrl;
        speed = aSpeed;
        outFileName = aOutFileName;
    }

    private TimeRead timedRead(InputStream in, byte[] buffer) throws IOException {
        long startTime = System.currentTimeMillis();
        int bytes = in.read(buffer, 0, buffer.length);
        int time = (int) (System.currentTimeMillis() - startTime);
        return new TimeRead(bytes, time);
    }

    @Override
    public void run() {
        try (
                InputStream in = new URL(url).openStream();
                BufferedOutputStream out =
                        new BufferedOutputStream(new FileOutputStream(outFileName))
        ) {
            long readed = 0L;
            byte[] dataBuffer = new byte[1024 * speed];
            TimeRead t = timedRead(in, dataBuffer);
            while (t.bytes != -1) {
                readed += t.bytes;
                int deltaTime = 1000 * t.bytes / (speed * 1024) - t.time;
                System.out.print(String.format("\rЗагружено: %d Кб", readed / 1024));
                out.write(dataBuffer, 0, t.bytes);
                if (deltaTime > 0) {
                    Thread.sleep(deltaTime);
                }
                t = timedRead(in, dataBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new WgetSpeed(args[0], speed, args[2]));
        wget.start();
    }
}
