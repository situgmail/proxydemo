package com.test.situ.proxy.proxydemo.demointerface;

import com.test.situ.proxy.proxydemo.framework.MyCustomTransaction;
import com.test.situ.proxy.proxydemo.framework.TransactionalService;

import java.io.File;

public interface Seekable {
    String seekTo(int position);

    /**
     * 我们创建的自定义注解，应用到我们的服务类里
     */
    @TransactionalService
    class PlayerService implements Playable, Seekable {

        @Override
        @MyCustomTransaction
        public String play(String song) {
            return this.play(new File(song));
        }

        @Override
        @MyCustomTransaction("file")
        public String play(File song) {
            return "Play song " + song.getName();
        }

        @Override
        @MyCustomTransaction("starting at")
        public String play(File song, int start) {
            return "Playing song " + song.getName() + " starting at " + start;
        }

        @Override
        @MyCustomTransaction("interval")
        public String play(File song, int start, int finish) {
            return "Playing song " + song.getName() + " starting at" + start + " till " + finish;
        }

        @Override
        public String seekTo(int position) {
            return "Seek to " + position;
        }
    }
}
