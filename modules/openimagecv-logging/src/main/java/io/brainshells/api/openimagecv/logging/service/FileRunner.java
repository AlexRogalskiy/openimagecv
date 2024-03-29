package io.brainshells.api.openimagecv.logging.service;

import io.brainshells.api.openimagecv.logging.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FileRunner implements Runnable, Closeable {

    private final Queue<String> logQueue = new ConcurrentLinkedQueue<>();
    private final String logName;
    private final String logDir;
    private final long maxSize;
    private BufferedWriter out;
    private File file;
    private String lastWriteDate;
    private volatile boolean isRunning;

    public FileRunner(String logName, String logDir, long maxSize) {
        this.logName = logName;
        this.logDir = logDir;
        this.maxSize = maxSize;

        file = new File(logDir, logName + ".log");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
            out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file, true),
                    StandardCharsets.UTF_8));
            isRunning = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (isRunning && !logQueue.isEmpty()) {
                this.write();
            } else {
                LogUtils.sleep(100);
            }
        }
    }

    private void split() {
        if (file.length() < maxSize) {
            return;
        }

        String newFileName =
            logDir + "/" + logName + "_" + LogUtils.getDate() + "_" + LogUtils
                .getTime() + ".log";
        File newFile = new File(newFileName);
        boolean flag = file.renameTo(newFile);
        if (!flag) {
            System.err.println("backup [" + newFile.getName() + "] fail.");
        } else {
            file = new File(logDir, logName + ".log");
            try {
                file.createNewFile();
                out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file, true),
                        StandardCharsets.UTF_8));
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void archive() {
        String newFileName =
            logDir + "/" + logName + "_" + this.lastWriteDate + ".log";
        File newFile = new File(newFileName);
        boolean flag = file.renameTo(newFile);
        if (flag) {
            file = new File(logDir, logName + ".log");
            try {
                file.createNewFile();
                out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file, true),
                        StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void write() {
        try {
            if (logQueue.isEmpty()) {
                return;
            }
            this.split();

            if (null != lastWriteDate && !LogUtils.getNormalDate()
                .equals(lastWriteDate)) {
                this.archive();
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                String msg = logQueue.poll();
                if (null == msg) {
                    break;
                }
                stringBuilder.append(msg);
            }
            out.write(stringBuilder.toString());
            out.flush();
            lastWriteDate = LogUtils.getNormalDate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void close() {
        try {
            isRunning = false;
            while (!logQueue.isEmpty()) {
                this.write();
            }
            if (out != null) {
                out.flush();
                out.close();
            }
        } catch (Throwable e) {
            e.printStackTrace(System.out);
        }
    }

    void addToQueue(StringBuffer buf) {
        synchronized (this) {
            String logMsg =
                buf.toString().replaceAll("\u001B\\[\\d+m", "") + "\r\n";
            logQueue.add(logMsg);
        }
    }

}
