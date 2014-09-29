
package javaapplication3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emre
 */
public class JavaApplication3 {
    
Map<String, String> ipMacFunctionMap=null;
Map<String, String> ipMacCurrentMap = null;

     public void IP_MAC_function(){
        String ip = "192.168.1.";
        String pingResult = "";
        ipMacFunctionMap = new HashMap<String, String>();
        String pingCmd = "ping " + ip;
        try {
            Runtime r = Runtime.getRuntime();
            Process p ;
            for (int i = 1; i < 256; i++) {//192.168.1.1 - 192.168.1.255 adres aralığı pingleme işlemi yapılıyor.
                pingCmd = pingCmd.substring(0, 15);// parsing yapılıyor.
                pingCmd += String.valueOf(i);
                p= r.exec(pingCmd);
            }
            ip = "192.168.1.";
            String arpTableViewCommand ="arp -a";
            Process ps = r.exec(arpTableViewCommand);
            
            BufferedReader in = new BufferedReader(new
            InputStreamReader(ps.getInputStream()));
            String inputLine;
            FileWriter fw = new FileWriter(new File("C:\\Users\\Emre\\Desktop\\IpMac.txt"));
            PrintWriter pw = new PrintWriter(fw);
            while ((inputLine = in.readLine()) != null) {  
                inputLine = inputLine.trim();// string'in baş ve sonundaki boşlık karakterleri siliniyor.
                if (inputLine.startsWith(ip)) {
                    ipMacFunctionMap.put(inputLine.substring(0, 15), inputLine.substring(15, 40));
                    pw.println(inputLine);
                }
            }
            in.close();
            pw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
     public void IP_MAC_current(){
        String ip = "192.168.1.";
        String pingResult = "";
        ipMacCurrentMap = new HashMap<String, String>();
        String pingCmd = "ping " + ip;
        try {
            Runtime r = Runtime.getRuntime();
            Process p ;
            for (int i = 1; i < 256; i++) {//192.168.1.1 - 192.168.1.255 adres aralığı pingleme işlemi yapılıyor.
                pingCmd = pingCmd.substring(0, 15);// parsing yapılıyor.
                pingCmd += String.valueOf(i);
                p= r.exec(pingCmd);
            }
            ip = "192.168.1.";
            String arpTableViewCommand ="arp -a";
            Process ps = r.exec(arpTableViewCommand);
            
            BufferedReader in = new BufferedReader(new
            InputStreamReader(ps.getInputStream()));
            String inputLine;
            File file = new File("C:\\Users\\Emre\\Desktop\\IpMac.txt");
            FileWriter fw = new FileWriter(file,true);// Seçmiş olduğumuz dosyanın append modunu aktif ettim.
            PrintWriter pw = new PrintWriter(fw);
            while ((inputLine = in.readLine()) != null) {  
                inputLine = inputLine.trim();// string'in baş ve sonundaki boşlık karakterleri siliniyor.
                if (inputLine.startsWith(ip)) {
                    ipMacCurrentMap.put(inputLine.substring(0, 15), inputLine.substring(15, 40));
                    pw.println(inputLine);
                }
            }
            in.close();
            pw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        JavaApplication3 j3 = new JavaApplication3();
        Boolean kontrol = false;
        j3.IP_MAC_function();
        Scanner scan = new Scanner(System.in);
        System.out.println("Güncel IP-MAC eşleşmesi için 1 e basiniz.");
        int yanit = scan.nextInt();
        if (yanit == 1) {
            j3.IP_MAC_current();
            try {
                for (Map.Entry<String, String> oldIpMac : j3.ipMacFunctionMap.entrySet()) {
                    String oldIp = oldIpMac.getKey();
                    kontrol = false;
                    for (Map.Entry<String, String> newIpMac : j3.ipMacCurrentMap.entrySet()) {
                        if (oldIp.equals(newIpMac.getKey())) {
                            if (oldIpMac.getValue().equals(newIpMac.getValue())) {
                                System.out.println(oldIp+" mac eşlemesi değişiklik göstermemektedir.");
                            }else{
                                System.out.println(oldIp+" eski mac: "+oldIpMac.getValue()+"    yeni mac: "+newIpMac.getValue() );
                            }
                            kontrol=false;
                            break;
                        }else{
                            kontrol = true;
                        }
                }
                    if (kontrol== true) {
                        System.out.println(oldIp+" adresli ip artık ağ üzerinde değil.");
                    }
                }
            } catch (Exception ex) {
                System.out.println("Hata olustu.");
            }
        }else{
            System.out.println("Güncel eşleşme dosyaya eklenmedi.");
        }
        
    }
    
}
