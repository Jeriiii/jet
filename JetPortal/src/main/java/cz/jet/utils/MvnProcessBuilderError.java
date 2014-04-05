/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.utils;

import org.springframework.stereotype.Component;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
//@Component
public class MvnProcessBuilderError extends Error {

	String errorMessage;

	public MvnProcessBuilderError(String message) {
		this.errorMessage = message;
	}
}