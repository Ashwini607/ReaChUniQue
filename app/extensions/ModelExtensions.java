package extensions;

import java.util.ArrayList;
import java.util.List;

import play.templates.JavaExtensions;



public class ModelExtensions extends JavaExtensions{
	 
	public void println(String [] x){
	for (int i = 0; i < x.length; i =i+1){
		System.out.println(x);
	}
	}
}

