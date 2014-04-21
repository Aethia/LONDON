package fr.m1.miage.london.network;

public class Action implements java.io.Serializable {
	private int typeAction;
	private String text;
	private Object o;
	
	public Action(){
		o = null;
	}
	
	public String getText(){
		return text;
	}
	
	public int getType(){
		return typeAction;
	}
	
	public Object getObject(){
		return o;
	}
	
	public void setType(int type){
		this.typeAction = type;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setObject(Object o){
		this.o = o;
	}

}
