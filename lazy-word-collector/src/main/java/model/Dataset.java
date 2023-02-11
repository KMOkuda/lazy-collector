package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Dataset implements Comparable<Dataset>, Serializable{
	String kw;
	int category;
	int level;
	static HashMap<String, Integer> categoryMap = new LinkedHashMap<>(){
        {
            put("hardware", 1);
            put("software", 2);
            put("DB", 3);
            put("network", 4);
            put("security", 5);
            put("design", 6);
            put("management", 7);
            put("strategy", 8);
            put("None", 9);
         
        }
    };
	public String getKw() {
		return kw;
	}
	public void setKw(String kw) {
		this.kw = kw;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public static HashMap<String, Integer> getCategoryMap() {
		return categoryMap;
	}
	public static void setCategoryMap(HashMap<String, Integer> categoryMap) {
		Dataset.categoryMap = categoryMap;
	}
	public Dataset(String kw, int category, int level) {
		super();
		this.kw = kw;
		this.category = category;
		this.level = level;
	}
	
	public Dataset() {
		
	}
    
	@Override
	public int compareTo(Dataset dataset) {
		if(this.category < dataset.category) {
			return -1;
		}else if(this.category > dataset.category) {
			return 1;
		}else if(this.level < dataset.level) {
			return -1;
		}else if (this.level > dataset.level) {
			return 1;
		}else {
			return 0;
		}
	}

}
