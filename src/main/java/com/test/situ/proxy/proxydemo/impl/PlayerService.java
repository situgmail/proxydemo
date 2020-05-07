package com.test.situ.proxy.proxydemo.impl;

import com.test.situ.proxy.proxydemo.demointerface.Playable;
import com.test.situ.proxy.proxydemo.demointerface.Seekable;
import com.test.situ.proxy.proxydemo.framework.MyCustomTransaction;
import com.test.situ.proxy.proxydemo.framework.TransactionalService;

import java.io.File;

@TransactionalService
public class PlayerService implements Playable, Seekable {
    @Override
    @MyCustomTransaction
    public String play(String filePath) {
        return this.play(new File(filePath));
    }

    @Override
    @MyCustomTransaction("file")
    public String play(File song) {
        return "Playing song " + song.getName();
    }

    @Override
    @MyCustomTransaction("starting at")
    public String play(File song, int start) {
        return "Playing song " + song.getName() + " starting at " + start;
    }

    @Override
    @MyCustomTransaction("interval")
    public String play(File song, int start, int finish) {
        return "Playing song " + song.getName() + " starting at " + start + " till " + finish;
    }

    @Override
    public String seekTo(int position) {
        return "Seek to " + position;
    }
}
