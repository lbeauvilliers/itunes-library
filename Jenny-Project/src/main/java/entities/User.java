package entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User implements Serializable{
	@Id
	@Column(name="library_persistent_id")private String library_persistent_id;
	
	@Column(name="user_name") private String user_name;
	@Column(name="user_password") private String user_password;
	
	public User() {}
	
	public User(String library_persistent_id, String user_name, String user_password){
		this.library_persistent_id = library_persistent_id;
		this.user_name = user_name;
		this.user_password = user_password;
	}

	public String getLibrary_persistent_id() {
		return library_persistent_id;
	}

	public void setLibrary_persistent_id(String library_persistent_id) {
		this.library_persistent_id = library_persistent_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

}
