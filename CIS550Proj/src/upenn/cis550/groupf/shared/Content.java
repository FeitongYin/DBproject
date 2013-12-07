package upenn.cis550.groupf.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Content implements IsSerializable {
	int contentID;
	// number of times this content is pinned
	int frequency;
	String contentKey;
	String description;
	boolean isCached;
	
	public Content() {
		
	}
	
	public Content(int contentID, int frequency, String contentKey, String description, boolean iscached) {
		setContentID(contentID);
		setFrequency(frequency);
		setContentKey(contentKey);
		setDescription(description);
		setCached(iscached);
	}
	public int getContentID() {
		return contentID;
	}
	public void setContentID(int contentID) {
		this.contentID = contentID;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public String getContentKey() {
		return contentKey;
	}
	public void setContentKey(String contentKey) {
		this.contentKey = contentKey;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isCached() {
		return isCached;
	}
	public void setCached(boolean isCached) {
		this.isCached = isCached;
	}
	
}
