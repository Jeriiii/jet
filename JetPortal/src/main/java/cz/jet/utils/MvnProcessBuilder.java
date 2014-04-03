/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.utils;


import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
@Component
public class MvnProcessBuilder {

    public MvnProcessBuilder(){}

    public Process start(List<String> list) throws MvnProcessBuilderError, IOException {
        ProcessBuilder pb = new ProcessBuilder(list);
        try {
            return pb.start();
	} catch (IOException ex) {
            throw ex;
	}
    }
}