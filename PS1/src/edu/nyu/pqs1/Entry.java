package edu.nyu.pqs1;


/**
*  For an entry, field name is required not empty, other fields like postalAdd can be empty.
* 
* @author Lin Dongxu(Damien)
**/
public class Entry {
  
  private final String name;
  private final String postalAdd;
  private final String phoneNum;
  private final String emailAdd;
  private final String note;


  /**
   * inner Builder class for constructing an entry
   */
  public static class Builder{
	
	//field name required 
    private final String name;
	
	//these fields are optional
    private String postalAdd = "";
    private String phoneNum = "";
    private String emailAdd = "";
    private String note = "";

	
	/**
	 * create a new Builder object, name is required.
	 * @param name which cannot be NULL
	 * @throws IllegalArgumentException if input name is NULL
	 */
	public Builder(String name) {
	  if (name == null) {
	    throw new IllegalArgumentException("name cannot be NULL");
	  }else {
	    this.name = name;
	  }
	}
	
	/**
	 * @param postal address value. If input is NULL, do nothing.
	 * @return updated Builder object.
	 */
	public Builder setPostal(String val) {
	  if (val == null) {
	    return this;
	  }
	  postalAdd = val;
	  return this;
	}
	
	/**
	 * The method eliminates all not digit character then update the builder.
	 * @param phone number. If input is NULL, do nothing.
	 * @return updated Builder object.
	 */
	public Builder setPhone(String num) {
	  if (num == null) {
        return this;
      }
	  phoneNum = num.replaceAll("[\\s-()]", "");
	  return this;
	}
	
	/**
	 * @param email address value. If input is NULL, do nothing.
	 * @return updated Builder object.
	 */
	public Builder setEmail(String val) {
	  if (val == null) {
        return this;
      }
	  emailAdd = val;
	  return this;
	}
	
	/**
	 * @param note value. If input is NULL, do nothing.
	 * @return updated Builder object.
	 */
	public Builder setNote(String val) {
	  if (val == null) {
        return this;
      }
	  note = val;
	  return this;
	}

	/**
	 * @return new Entry object
	 */
	public Entry build() {
	  return new Entry(this);
	}
  }

  private Entry(Builder builder) {
    name = builder.name;
	postalAdd = builder.postalAdd;
	phoneNum = builder.phoneNum;
	emailAdd = builder.emailAdd;
	note = builder.note;
  }


  /**
   * @return name of the entry
   */
  public String getName() {
	return name;
  }


  /**
   * @return postal address of the entry
   */
  public String getPostalAdd() {
	return postalAdd;
  }


  /**
   * @return phone number of the entry
   */
  public String getPhoneNum() {
	return phoneNum;
  }


  /**
   * @return email address of the entry
   */
  public String getEmailAdd() {
	return emailAdd;
  }


  /**
   * @return note of the entry
   */
  public String getNote() {
	return note;
  }


  /**
   * @return raw string of the entry
   */
  public String getEntryString() {
	return name + "|" + postalAdd + "|" + phoneNum + "|" + emailAdd + "|" + note;
  }

  @Override
  public String toString() {
    return "Name: " + name + "|" + "postal address: " + postalAdd + "|" + "phone numer: " + phoneNum
			+ "|" + "email address: " + emailAdd + "|" + "note: " + note;
  }

  @Override
  public int hashCode() {
	int result = 17;
	int prime = 31;
    result = prime * result + name.hashCode();
    result = prime * result + postalAdd.hashCode();
    result = prime * result + phoneNum.hashCode();
    result = prime * result + emailAdd.hashCode();
    result = prime * result + note.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
	if (obj.getClass() != this.getClass()) {
	  return false;
	}
	return this.toString().equals(obj.toString());
  }
}


