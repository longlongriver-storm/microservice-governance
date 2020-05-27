/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.algorithm.demo.ast;

import org.junit.jupiter.api.Test;

/**
 *
 * @author lixin
 */
public class ParseOneJavaFileTest {
    
    public ParseOneJavaFileTest() {
    }

    /**
     * Test of main method, of class ParseOneJavaFile.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = {"D:\\Develop\\Dubbo\\dubbo\\src\\com\\alibaba\\dubbo\\remoting\\telnet\\codec\\TelnetCodec.java"};
        ParseOneJavaFile.main(args);
    }
    
}
