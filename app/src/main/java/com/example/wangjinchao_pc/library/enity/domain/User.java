package com.example.wangjinchao_pc.library.enity.domain;

public class User extends BaseObject{

	/**
	 * 用户
	 */
	private static final long serialVersionUID = -1775298614368938621L;

	//账号是主键?????????????
	private String account;
	private String password;
	private String name;
	private String hobbyid;
	private String hobbyName;
	private String sex;
	private String photourl;
	private String nickname;
	private String oldpassword;
	public String getOldpassword(){
		return oldpassword;
	}
	public void setOldpassword(String oldpassword){
		this.oldpassword=oldpassword;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHobbyid() {
		return hobbyid;
	}
	public void setHobbyid(String hobbyid) {
		this.hobbyid = hobbyid;
	}
	public String getHobbyName() {
		return hobbyName;
	}
	public void setHobbyName(String hobbyName) {
		this.hobbyName = hobbyName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhotourl() {
		return photourl;
	}
	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
	

}