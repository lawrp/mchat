package mchat;

import java.awt.Image;
import java.util.List;

public class MChatGroup implements MChatEntity {
	private String id;
	private String groupName;
	private Image photo;
	
	private List<MChatUser> members;

	public MChatGroup(String id, String groupName) {
		this.id = id;
		this.groupName = groupName;
	}

	public void addUser(MChatUser user) {
		members.add(user);
	}
	
	public List<MChatUser> getMembers() {
		return members;
	}
	
	public String getGroupName() {
		return groupName;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getDisplayName() {
		return groupName;
	}

	@Override
	public Image getPhoto() {
		return photo;
	}
}
