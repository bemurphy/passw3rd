
package com.atti;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;


public class PasswordClient {
  private static final Logger log = Logger.getLogger(PasswordClient.class);
  
	/**
	 * @param args
	 * k - generate key
	 * e - encyrpt
	 * o &lt;path&gt; - output file for encryption / input file for decryption
	 * p &lt.password&gt;- plaintext/password to encrypt
	 * d &lt;path&gt; - decrypt 
	 * : = required value after flag
	 * :: = optional value after flag
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		OptionParser parser = new OptionParser("k::e:p:d:");
		OptionSet options = parser.parse(args);
		if(options.has("k")) {
			KeyLoader.generateKeyAndIVFile((String)options.valueOf("k"));
		}  else if(options.hasArgument("e")) {
			String outputPath = (String) options.valueOf("e");
			System.out.print("Enter the password to save: ");
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String password = in.readLine();
			String encryptedPassword = DefaultPasswordService.encrypt(password);
			FileOutputStream output = new FileOutputStream(outputPath);
			output.write(Base64.encodeBase64(encryptedPassword.getBytes()));
			output.flush();
			output.close();
			log.info(System.getProperty("user.name") + " created a new password file at " + outputPath);
		} else if(options.hasArgument("d")) {
			String pwFilePath = (String) options.valueOf("d");
			log.debug(pwFilePath);
			String password = DefaultPasswordService.getPassword(pwFilePath);
			log.info("Decrypted file at " + pwFilePath + " for user " + System.getProperty("user.name"));
      System.out.println("The password is: " + password);
		} else {
		  System.out.println(options.nonOptionArguments());
			System.out.println("\nUsage:\n");
			System.out.print("Generate new key/IV file: ");
			System.out.println("PasswordClient -k [<output directory>]");
			System.out.print("Encrypt new password: ");
			System.out.println("PasswordClient -e <output path>");
			System.out.print("Decrypt password: ");
			System.out.println("PasswordClient -d <key file path>\n");
		}
	}
}
