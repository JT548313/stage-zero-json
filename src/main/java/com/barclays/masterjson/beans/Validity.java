package com.barclays.masterjson.beans;

import java.util.Date;

public class Validity {
	
	Date notBefore;
	Date notAfter;
	
	public Date getNotBefore() {
		return notBefore;
	}
	public void setNotBefore(Date notBefore) {
		this.notBefore = notBefore;
	}
	public Date getNotAfter() {
		return notAfter;
	}
	public void setNotAfter(Date notAfter) {
		this.notAfter = notAfter;
	}

}
