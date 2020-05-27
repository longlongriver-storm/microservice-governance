/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.algorithm.demo.dsf;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 *
 * @author lixin
 */
public class DAGDsfErgodicTest {

    public DAGDsfErgodicTest() {
    }

    /**
     * Test of main method, of class DAGDsfErgodic.
     */
    @Test
    public void testMain() {
        DAGDsfErgodic dagDsfErgoidc=new DAGDsfErgodic();
        dagDsfErgoidc.addLine("A", "B");
        dagDsfErgoidc.addLine("A", "C");
        dagDsfErgoidc.addLine("B", "D");
        dagDsfErgoidc.addLine("D", "E");
        dagDsfErgoidc.addLine("E", "B");
        List<String> reslut = dagDsfErgoidc.find();
        for (String string : reslut) {
            System.out.println(string);
        }
    }

}
