/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Input IPs to test
 * A)135.46.63.10
 * B)135.46.57.14
 * C)135.46.52.2
 * D)192.53.40.7
 * E)192.53.56.7
 */
package ipproject;

import java.util.Scanner;


/**
 *
 * @author ahaflige
 */
public class IPProject {

    public static String newInput;
    public static int binaryInput;
    public static Scanner input = new Scanner(System.in);
    public static String binaryAddress = "";
    public static String[][] nics = new String[][] {
        {"135.46.56.0","Interface 1",},
        {"135.46.60.0","Interface 2"},
        {"192.53.40.0","Router 1"}
    };
    public static String[][] subnets = new String[][]{
        {"11111111","11111111","11111100","00000000"},
        {"11111111","11111111","11111110","00000000"}
    };
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        takeInput();
    }
    
    public static void takeInput(){
        route dialog = new route();
        dialog.setVisible(true);
    }

    public static String makeBinaryAddress(String address){
        //we want output in form xxxxxxxx.xxxxxxxx.xxxxxxxx.xxxxxxxx
        String binaryOctets = "";

        //split string into octets
        String[] octets = address.split("\\.");
        int numOctets = octets.length;
        int count = 0;

        for(String octet: octets){
            String octetBinary = "";
            int binary = Integer.parseInt(octet);
            octetBinary = Integer.toBinaryString(binary);
            
            //fill front of octet with x zeros if length is less than 8
            if (octetBinary.length() < 8){
                String spacer = "";
                for (int i = 0;8 - octetBinary.length() > i; i++){
                    spacer = spacer.concat("0");
                }
                octetBinary = spacer.concat(octetBinary);
            }
            
            binaryOctets = binaryOctets.concat(octetBinary);
            count ++;

            if(count < numOctets){
              binaryOctets = binaryOctets.concat(".");  
            }
        }
        
        //set octet to be used in compare IP function
        binaryAddress = binaryOctets;
        return binaryOctets;
    }
    
    public static String compareIP(){
        String ipNetworkAddress = "";
        String[] networkBinary = new String[4];
        int subnetChar;
        int ipChar;
        String octetResult = "";
        String[] ipOctets = binaryAddress.split("\\.");

        for(String[] subnet: subnets){
            //for each subnet octet perform a bitwise AND for both IP and Mask Octets and store results
            for(int x = 0; x < 4; x++){
                for (int i = 0; i < 8; i++){
                    subnetChar = subnet[x].charAt(i) - '0';
                    ipChar = ipOctets[x].charAt(i) - '0';
                    octetResult = octetResult.concat(String.valueOf(subnetChar * ipChar));
                }
                networkBinary[x] = octetResult;
                octetResult = "";
            }

            //convert {"xxxxxxxx","xxxxxxxx","xxxxxxxx","xxxxxxxx"} ---> "xxx.xxx.xxx.xxx"
            for(int z = 0; z < 4; z++){
                ipNetworkAddress = ipNetworkAddress.concat(String.valueOf(Integer.parseInt(networkBinary[z],2)));
                if(z < 3){
                    ipNetworkAddress = ipNetworkAddress.concat(".");
                }
            }

            //return nic if the network address of input IP matches any nic
            for (String[] nic: nics){
                if(nic[0].equals(ipNetworkAddress)){
                return nic[1];
                }
            }
        }
        //if nic not found then return default
        return "Default Router 2";
    }
}