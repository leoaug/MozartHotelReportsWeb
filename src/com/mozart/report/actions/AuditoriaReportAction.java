package com.mozart.report.actions;

public class AuditoriaReportAction extends BaseAction {

	private static final long serialVersionUID = -3277403662646637768L;

	private String format;
	private String report;
	private String contentType;

	public String showReport() throws Exception {
		try {
			setParameters();
			format = request.getParameter("format"); 
 
			report = request.getParameter("report");
			
			contentType = request.getParameter("contentType");

			return report;
		} finally {
			fecharRecurso();
		}
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
