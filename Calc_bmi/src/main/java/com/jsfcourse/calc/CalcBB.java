package com.jsfcourse.calc;

import javax.inject.Inject;
import javax.inject.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named
@RequestScoped
//@SessionScoped
public class CalcBB {
	
	
	
	
	private String weight;
	private String height;
	private Double result;
	private String bmi_result;
	@Inject
	FacesContext ctx;

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public String getBmi_result() {
		return bmi_result;
	}

	public void setBmi_result(String bmi_result) {
		this.bmi_result = bmi_result;
	}

	public boolean doTheMath() {
		try {
			
			double weight = Double.parseDouble(this.weight);
			double height = Double.parseDouble(this.height);
			
			result = round(weight/(height*height)*10000,2);
			if(weight<=0 || height <=0 || weight>=250 || height>=250 || weight>height || result>50 || result<0) {ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "B³êdnie podane parametry", null));return false;}
			
					
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacja wykonana poprawnie", null));
			return true;
		} catch (Exception e) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "B³¹d podczas przetwarzania parametrów", null));
			return false;
		}
	}
	
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public String bmi_checker() {
		String bmi_result = this.bmi_result;
		
			if(result<18.5) {bmi_result="Niedowaga";}
			if(result<24.9 && result>18.5) {bmi_result="Prawid³owa waga";}
			if(result<29.9 && result>25) {bmi_result="Nadwaga";}
			if(result>30) {bmi_result="Oty³oœæ";}
		
		
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "BMI: " + bmi_result, null));
		return null;
	}
	
	

	// Go to "showresult" if ok
	public String calc() {
		if (doTheMath()) {
			return "showresult";
		}
		return null;
	}

	// Put result in messages on AJAX call
	public String calc_AJAX() {
		
		if (doTheMath()) {
			
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Wynik: " + result, null));
			bmi_checker();
		}
		return null;
	}

	public String info() {
		return "info";
	}

	
}
