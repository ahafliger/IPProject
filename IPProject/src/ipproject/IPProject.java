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

    public static String newInput;
    public static int binaryInput;
    public static Scanner input = new Scanner(System.in);
    public static String binaryAddress = "";
    public static String[][] nics = new String[][] {
        {"135.46.56.0","Interface 0"},
        {"135.46.60.0","Interface 1"},
        {"192.53.40.0","Router 1"},
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
            String octetBinary = "";
            int binary = Integer.parseInt(octet);
            octetBinary = Integer.toBinaryString(binary);
            
            //fill front with x zeros if length is less than 8
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
        String ipNetworkAddress = ""; //form of "xxx.xxx.xxx.xxx"
        //did it the lazy way and hardcoded this.
        String[] subnet22 = new String[]{"11111111","11111111","11111100","00000000"};
        String[] subnet23 = new String[]{"11111111","11111111","11111110","00000000"};
        String[][] subnets = new String[][]{subnet22,subnet23};
        String[] networkBinary = new String[]{"","","",""};
        char subnetChar;
        char ipChar;
        String outcome = "";
        //first convert ip to network address by bitwise AND
        String[] ipOctets = binaryAddress.split("\\.");
        //for each subnet
        for(String[] subnet: subnets){
            //do an operation that multiplies subnet digit [i] * ipdigit[i] and store into networkBinary as string
            //for each subnet octet
            for(int x = 0; x < 4; x++){
                //perform a bitwise and for both IP and Mask Octets
                for (int i = 0; i < 8; i++){
                    subnetChar = subnet[x].charAt(i);
                    int subnetDigit = subnetChar - '0';
                    ipChar = ipOctets[x].charAt(i);
                    int ipDigit = ipChar - '0';
                    outcome = outcome.concat(String.valueOf(ipDigit * subnetDigit));
                }
                networkBinary[x] = outcome;
                outcome = "";
            }
            //now that we have an array of binary numbers in networkBinary we need to convert 
            //back to a single string of IPV4 format ex xxx.xxx.xxx.xxx
            //for each octet in network binary[] we need to convert back to IPV4 format XXX and combine in one string
            for(int z = 0; z < 4; z++){
                ipNetworkAddress = ipNetworkAddress.concat(String.valueOf(Integer.parseInt(networkBinary[z],2)));
                if(z < 3){
                    ipNetworkAddress = ipNetworkAddress.concat(".");
                }
            }
            //then return if the network address matches any nics
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
