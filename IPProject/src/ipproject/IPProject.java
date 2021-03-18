/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipproject;

import java.util.Scanner;


/**
 *
 * @author ahaflige
 */
public class IPProject {

    public static Scanner input = new Scanner(System.in);
    public static String newInput;
    public static int binaryInput;
    public static String testAddress = "255.57.194.10";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //newInput = 255;
        //System.out.println(newInput);
        //binaryInput = Integer.toBinaryString(newInput);
        takeInput();
        //System.out.println("Test address: " + testAddress);
        //System.out.println("Simple Binary Address: " + simpleBinaryAddress(testAddress));
        //System.out.println("Binary Address: " + makeBinaryAddress(testAddress));
    }
    
    public static void takeInput(){
        route dialog = new route();
        dialog.setVisible(true);
    }
    
    // NOT WORKING YET
    public static int simpleBinaryAddress(String address){
        String[] octets = address.split("\\.");
        String binaryOctets = "";
        int simpleBinary = 0;
        for(String octet: octets){
            int binary = Integer.parseInt(octet);
            binaryOctets = binaryOctets.concat(Integer.toBinaryString(binary));
        }
        simpleBinary = Integer.parseInt(binaryOctets);
        return simpleBinary;
    }
    
    public static String makeBinaryAddress(String address){
        String[] octets = address.split("\\.");
        String binaryOctets = "";
        int numOctets = octets.length;
        int count = 0;
        for(String octet: octets){
            int binary = Integer.parseInt(octet);
            binaryOctets = binaryOctets.concat(Integer.toBinaryString(binary));
            count = count+1;
            if(count < numOctets){
              binaryOctets = binaryOctets.concat(".");  
            }
        }
        
        return binaryOctets;
    }
}
