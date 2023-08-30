package controller;

import javax.swing.*;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RedesController {
    public RedesController() {
        super();
    }

    public void IPIdentify() {
        String OS = IdentifyOS();
        if (OS.contains("Windows")) {
            try {
                String process = "ipconfig";
                Process p = Runtime.getRuntime().exec(process); //Chama um processo do SO
                InputStream fluxo = p.getInputStream(); //Recebe o fluxo de bits do processo
                InputStreamReader reader = new InputStreamReader(fluxo); //Ler e traduz o fluxo de dados do app para uma string
                BufferedReader buffer = new BufferedReader(reader); //Adiciona essa info num buffer

                StringBuilder ip = new StringBuilder();

                String line = buffer.readLine();
                while (line != null) {

                    if (line.contains("Adap")) {
                        ip.append(line);
                        ip.append(" IPv4: ");
                    } else if (line.contains("IPv4")) {
                        String[] IPv4 = line.split(":");
                        ip.append(IPv4[1]);
                        ip.append("\n");
                    }
                    line = buffer.readLine();
                }
                JOptionPane.showMessageDialog(null, "Windows - Ipconfig\n" + ip);
                buffer.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (OS.contains("Linux")) {
            //Linux Mint
            try {
                String process = "ifconfig";
                Process p = Runtime.getRuntime().exec(process); //Chama um processo do SO
                InputStream fluxo = p.getInputStream(); //Recebe o fluxo de bits do processo
                InputStreamReader reader = new InputStreamReader(fluxo); //Ler e traduz o fluxo de dados do app para uma string
                BufferedReader buffer = new BufferedReader(reader); //Adiciona essa info num buffer

                StringBuilder ip = new StringBuilder();

                String line = buffer.readLine();
                while (line != null) {

                    if (line.contains("UP")) {
                        ip.append("Adaptador: ").append(line);
                        ip.append(" IPv4: ");
                    } else if (line.contains("inet")) {
                        String[] IPv4 = line.split("");
                        ip.append(IPv4[1]);
                        ip.append("\n");
                    }
                    line = buffer.readLine();
                }

                JOptionPane.showMessageDialog(null, "Linux - IfConfig\n" + ip);
                buffer.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Ping() {
        String process;
        String OS = IdentifyOS();
        StringBuilder ping = new StringBuilder();

        try {
            if (OS.contains("Windows")) {
                process = "ping -4 -n 10 www.google.com.br";
                Process IPPing = Runtime.getRuntime().exec(process);
                InputStream stream = IPPing.getInputStream();
                InputStreamReader reader = new InputStreamReader(stream);
                BufferedReader buffer = new BufferedReader(reader);


                String line = buffer.readLine(); //"Contador" do while
                while (line != null) {
                    if (line.contains("Aproximar")) {

                        line = buffer.readLine();
                        String[] average = line.split("=");

                        ping.append("Average ping: ").append(average[3]);
                    }
                    System.out.println(line + "\n");
                    line = buffer.readLine();
                }
            } else {
                //Linux
                process = "ping -4 -c 10 www.google.com.br";
                Process IPPing = Runtime.getRuntime().exec(process);
                InputStream stream = IPPing.getInputStream();
                InputStreamReader reader = new InputStreamReader(stream);
                BufferedReader buffer = new BufferedReader(reader);


                String line = buffer.readLine(); //"Contador" do while
                while (line != null) {
                    if (line.contains("packets transmitted")) {

                        line = buffer.readLine();
                        String[] entireLine = line.split("= ");
                        String[] average = entireLine[1].split("/");

                        ping.append("Average ping: ").append(average[1]).append(" ms");
                    }
                    System.out.println(line + "\n");
                    line = buffer.readLine();
                }
            }
            JOptionPane.showMessageDialog(null, "Pinging google...\n" + ping);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String IdentifyOS() {
        return System.getProperty("os.name");
    }

}
