package com.barclays.masterjson.beans;

import org.joda.time.DateTime;

public class Validity {
	
	DateTime notBefore;
	DateTime notAfter;
	
	public DateTime getNotBefore() {
		return notBefore;
	}
	public void setNotBefore(DateTime notBefore) {
		this.notBefore = notBefore;
	}
	public DateTime getNotAfter() {
		return notAfter;
	}
	public void setNotAfter(DateTime notAfter) {
		this.notAfter = notAfter;
	}

}
