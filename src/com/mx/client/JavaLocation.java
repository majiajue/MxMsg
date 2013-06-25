package com.mx.client;


public class JavaLocation {
  private String PeerId, NickName, flagFile;

  @Override
public String toString() {
	return  PeerId + System.getProperty("line.separator")+ NickName;
}

public JavaLocation(String PeerId, String NickName,
		      String flagFile) {
    setPeerId(PeerId);
    setNickName(NickName);
    setFlagFile(flagFile);
  }

  public String getPeerId() {
	return PeerId;
}

public void setPeerId(String peerId) {
	PeerId = peerId;
}

public String getNickName() {
	return NickName;
}

public void setNickName(String nickName) {
	NickName = nickName;
}






  
  /** Return path to head image file */
  
  public String getFlagFile() {
    return(flagFile);
  }

  /** Specify path to head image file. */
  
  public void setFlagFile(String flagFile) {
    this.flagFile = flagFile;
  }
}
