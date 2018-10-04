package edu.nyu.pqs1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
* This class is a address book. A address book contains a list of entries. The class provide method of
* creating a addressbook, adding/removing an entry, searching for entry, save/read address book to file.
* @author Lin Dongxu(Damien)
*/
public class AddressBook{
	
  private List<Entry> entries;
	
  /**
   * creates a new address book object
   */
  public AddressBook() {
    entries = new ArrayList<Entry>();
  }
	
  /**
  * add an entry into the address book. The address book can contain multiple same entry.
  * 
  * @param an Entry e
  * @return true when it add successfully, return false if the input entry is null or add unsuccessfully.
  */
  public boolean addEntry(Entry e) {
    if (e == null) {
	  return false;
    }
    return entries.add(e);
  }
	
  /**
   * remove an entry from the address book. If there are several entries in address book that are the same,
   * only remove one of them.
   * 
   * @param an Entry e
   * @return true if it remove successfully, return false if the input entry is null or the entry does
   *  not exist in address book.
   */
  public boolean removeEntry(Entry e) {
  	if (e == null) {
  	  return false;
  	}
  	return entries.remove(e);
  }
  
  /**
   * This method use a String str as input, to search in the whole address book for any entries that contains
   * any information same as the str.
   * 
   * @param a String str
   * @return a list of Entry containing the information, if no any entry matched or input str is NULL,
   * return an empty list.
   */
  public List<Entry> searchEntry(String str) {
  	List<Entry> resList = new ArrayList<Entry>();
  	if (str == null) {
  	  return resList;
  	}
  	for (Entry e: entries) {
  	  if (e.getEntryString().contains(str)) {
  	    resList.add(e);
  	  }
  	}
  	return resList;
  }
  
  
  /**
   * The method serialize a address book to a local file named by input parameter fileName.
   * @param fileName
   * @return true if it successfully writes it. Otherwise it fails and return false because file 
   *  already exist.
   * @throws IOException
   */
  public boolean saveAddressBook(String fileName) throws IOException{
      StringBuilder sb = new StringBuilder();
      sb.append("name|postal|phone|email|note").append("\n");
      for (Entry e: entries) {
        sb.append(e.getName()).append("|");
      	sb.append(e.getPostalAdd()).append("|");
      	sb.append(e.getPhoneNum()).append("|");
      	sb.append(e.getEmailAdd()).append("|");
      	sb.append(e.getNote()).append("|");
      	sb.append("\n");
      }
      
      File f = new File(fileName);
      if(f.exists() && !f.isDirectory()) { 
      	return false;
      }
      
      PrintStream out = new PrintStream(new FileOutputStream(fileName));
      out.print(sb.toString());
      out.close();
      return true;
  }
  
  /**
   * This method read local file named fileName, then it will overwrite the address book with new data.
   * @param fileName
   * @throws FileNotFoundException if the file does not exist
   */
  public void readAddressBook(String fileName) throws FileNotFoundException {
  	Scanner scanner = new Scanner( new File(fileName) );
  	String text = scanner.useDelimiter("\\A").next();
  	scanner.close();
  	
  	String[] lines = text.split("\n");
  	if (lines.length > 1) {
  	  entries = new ArrayList<Entry>();
  	  for (int i = 1; i < lines.length; i++) {
  	    String[] item = lines[i].split("\\|", -1);
		entries.add(new Entry.Builder(item[0]).setPostal(item[1]).setPhone(item[2])
		    .setEmail(item[3]).setNote(item[4]).build());
  	  }
  	}
  	
  }
  
  
  @Override
  public String toString() {
  	StringBuilder sb = new StringBuilder();
  	sb.append("name|postal|phone|email|note").append("\n");
  	for (Entry e: entries) {
  	  sb.append(e.getEntryString()).append("\n");
  	}
  	return sb.toString();
  }
}
