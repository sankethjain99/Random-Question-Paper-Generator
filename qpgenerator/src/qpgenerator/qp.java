package qpgenerator;

import java.io.*;

public class qp {

	public static void main(String[] args) throws IOException {
		
	File qpfinal=new File("E:\\f1.txt");
	FileWriter fw=new FileWriter(qpfinal);
	fw.write("hiiiiii");
	fw.close();
	}

}
