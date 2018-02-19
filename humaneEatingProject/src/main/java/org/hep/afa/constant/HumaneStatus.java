package org.hep.afa.constant;

public enum HumaneStatus {
	
	ALL("All"),VEGANFRIENDLY("Vegan-Friendly"),VEGAN("Vegan"),HUMANEFRIENDLY("Humane-Friendly")/*,WATCHLIST("Watch List")*/;

	String humaneStatus;
	private HumaneStatus(String str){
		this.humaneStatus = str;
	}
	
	@Override
	public String toString(){
		return humaneStatus;
	}
	
	public static String[] names() {
		HumaneStatus[] hs = HumaneStatus.values();
	    String[] names = new String[hs.length];

	    for (int i = 0; i < hs.length; i++) {
	        names[i] = hs[i].humaneStatus;
	    }

	    return names;
	}

}
