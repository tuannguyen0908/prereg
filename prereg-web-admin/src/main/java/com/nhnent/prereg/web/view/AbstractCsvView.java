package com.nhnent.prereg.web.view;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public abstract class AbstractCsvView extends AbstractView {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat df = new SimpleDateFormat("\"MM/dd/yyyy HH:mm:ss\"");
	
	private CsvMapper mapper = new CsvMapper();
	protected String[] header;
	protected Object data;
	protected Class<?> clazz;
	
	protected String classKey = "class";
	protected String headerKey = "header";
	protected String dataKey = "data";
	
	/**
	 * Default Constructor.
	 * Sets the content type of the view to "application/csv".
	 */
	public AbstractCsvView() {
		setContentType("application/csv");
	}

	public Class<?> getClazz() {
		return clazz;
	}


	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getClassKey() {
		return classKey;
	}

	public void setClassKey(String classKey) {
		this.classKey = classKey;
	}

	public String getHeaderKey() {
		return headerKey;
	}

	public void setHeaderKey(String headerKey) {
		this.headerKey = headerKey;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}
	
	/**
	 * Renders the CSV view, given the specified model.
	 */
	@Override
	protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Create a schema instance for this render step.
		CsvSchema schema = createCsvSchema(model, request);

		// Delegate to application-provided document code.
		buildCsvDocumentData(model, request, response);

		// Set the content type.
		response.setContentType(getContentType());
	    response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.csv\"", ("NHN_" + sdf.format(new Date(System.currentTimeMillis())))));

		// Flush byte array to servlet output stream.
		renderSchema(schema, response);;
	}
	
	/**
	 * Template method for creating the Jackson DataBinding {@link CsvSchema} instance.
	 * @param model the model Map
	 * @param request current HTTP request (for taking the URL or headers into account)
	 * @return the new {@link ObjectWriter} instance
	 */
	protected CsvSchema createCsvSchema(Map<String, Object> model, HttpServletRequest request) {
		Object classType = model.get(classKey);
		if (classType != null) {
			this.clazz = (Class<?>) classType;
		}
		CsvSchema schema = mapper.schemaFor(clazz).withHeader();
		mapper.setDateFormat(df);
		return schema;
	}
	
	/**
	 * The actual render step: taking the Jackson {@link CsvSchema}, 
	 * creating {@link ObjectWriter} and rendering it to the given response.
	 * @param mapper the Jackson ObjectMapper to render
	 * @param response current HTTP response
	 * @throws IOException when thrown by I/O methods that we're delegating to
	 */
	protected void renderSchema(CsvSchema schema, HttpServletResponse response) throws IOException {
		ServletOutputStream out = response.getOutputStream();
		ObjectWriter objectWriter = mapper.writer(schema);
		objectWriter.writeValue(out, data);
	}
	
	/**
	 * Subclasses must implement this method to populate
	 * the CSV document, given the model.
	 * @param model the model Map
	 * @param request in case we need locale etc. Shouldn't look at attributes.
	 * @param response in case we need to set cookies. Shouldn't write to it.
	 */
	protected abstract void buildCsvDocumentData(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

}
