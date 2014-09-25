package mhurd.scratch;

import com.jcabi.ssh.SSH;
import com.jcabi.ssh.Shell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SSHAgent {

    public static void main(String[] args) {

        String privateKeyPath = "C:\\Users\\mhurd\\.ssh\\gmoadm_rsa";
        StringBuilder contents = new StringBuilder();
        try {
            BufferedReader input =  new BufferedReader(new FileReader(privateKeyPath));
            try {
                String line; //not declared within while loop
                /*
                * readLine is a bit quirky :
                * it returns the content of a line MINUS the newline.
                * it returns null only for the END of the stream.
                * it returns an empty String if two newlines appear in a row.
                */
                while (( line = input.readLine()) != null){
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            }
            finally {
                input.close();
            }
            Shell shell =  new SSH("longmo171-zd1", 22, "gmoadm", contents.toString());
            String stdout = new Shell.Plain(shell).exec("ls -al");
            System.out.println(stdout);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}