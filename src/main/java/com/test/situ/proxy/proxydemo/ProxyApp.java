package com.test.situ.proxy.proxydemo;

import com.test.situ.proxy.proxydemo.demointerface.Playable;
import com.test.situ.proxy.proxydemo.demointerface.Seekable;
import com.test.situ.proxy.proxydemo.framework.ProxyFactory;

import java.io.File;

public class ProxyApp {

    public static void main(String[] args) {
        System.out.println(ProxyApp.class.getPackage());
        ProxyFactory proxyFactory = new ProxyFactory(ProxyApp.class.getPackage());

        File file = new File("SongPath");
        Playable player = proxyFactory.getBean(Playable.class);
        System.out.println("business.play(file.getPath())");
        System.out.println(player.play(file.getPath()));


        System.out.println();
        System.out.println("business.play(*************)");
        System.out.println(player.play("*****************"));


        System.out.println();
        System.out.println("business.play(file)");
        System.out.println(player.play(file));

        System.out.println();
        System.out.println("business.play(file, 10)");
        System.out.println(player.play(file, 10));

        System.out.println();
        System.out.println("business.play(file, 10, 15)");
        System.out.println(player.play(file, 10, 15));


        Seekable seekablePlayer = proxyFactory.getBean(Seekable.class);
        System.out.println();
        System.out.println("seekablePlayer.seekTo(20)");
        System.out.println(seekablePlayer.seekTo(20));


    }
}
