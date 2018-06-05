/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab.exceptions;

/**
 *
 * @author Wojtek
 */
public class ItemValueOverMaxException extends Exception{
    public ItemValueOverMaxException(String s) {
         super(s);  
    }
    
}
