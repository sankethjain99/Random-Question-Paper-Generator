package qpgenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import qpgenerator.DBUtil;

public class ClientTest {
	public static void main(String[] args) throws SQLException, IOException   {
		
		String SQL="INSERT INTO storetextfile_table (file_name,file_size_in_kb,file_extension,file_content)VALUES(?,?,?,?)";
		Path dir = Paths.get("InputFiles");
		ArrayList<String> al = new ArrayList<String>();
		try(Stream<Path> list = Files.list(dir);
				Connection connection = DBUtil.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			List<Path> pathList = list.collect(Collectors.toList());
			System.out.println("Following files are saved in database..");
			for (Path path : pathList) {
				System.out.println(path.getFileName());
				File file = path.toFile();
				String fileName = file.getName();
				long fileLength = file.length();
				long fileLengthInKb=fileLength/1024;
				ps.setString(1, fileName);
				ps.setLong(2, fileLengthInKb);
				ps.setString(3, fileName.substring(fileName.lastIndexOf(".")+1));
				ps.setCharacterStream(4, new FileReader(file), fileLength);
				ps.addBatch();
			}
			System.out.println("----------------------------------------");
			int[] executeBatch = ps.executeBatch();
			System.out.println("Enter The SubCode");
			Scanner sc = new Scanner(System.in);
			String ab= sc.next();
			ResultSet rs = ps.executeQuery("select * from storetextfile_table where file_name = '"+ab+"'");
			
			while(rs.next()) {
				
				FileWriter fw=new FileWriter("E:\\student1.txt");    
		           fw.write(rs.getString("file_content"));    
		           fw.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		try(BufferedReader in = new BufferedReader(new FileReader("E:\\student1.txt"))){
		String str;

		List<String> list = new ArrayList<String>();
		while((str = in.readLine()) != null){
		    list.add(str);
		}

		String[] stringArr = list.toArray(new String[0]);
		List<String> itemList = new ArrayList<String>();
		for(String i : stringArr) {
			
			String[] items = i.split(",");
		      itemList = Arrays.asList(items);
		      al.addAll(itemList);
		}
		File qpfinal=new File("E:\\f1.txt");
		FileWriter fw=new FileWriter(qpfinal);
		for(int i=0;i<4;i++) {
			
			fw.write(getRandomElement(al) + "\n");
			
			
		System.out.println(getRandomElement(al));
		}
		fw.close();
		
	}
	}
	public static String getRandomElement(List<String> list) 
    { 
        Random rand = new Random(); 
        return list.get(rand.nextInt(list.size())); 
    } 
}
