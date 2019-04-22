/**
 * 
 */
package com.cc.common.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Administrator
 *
 */
public class ResponseWrapper extends HttpServletResponseWrapper {
	
	private ByteArrayOutputStream byteArrayOutputStream;
	
	public ResponseWrapper(HttpServletResponse response) {
		super(response);
		byteArrayOutputStream = new ByteArrayOutputStream();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponseWrapper#getOutputStream()
	 */
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return new ServletOutputStream() {
			
			@Override
			public void write(int b) throws IOException {
				byteArrayOutputStream.write(b);
			}
			
			/* (non-Javadoc)
			 * @see java.io.OutputStream#write(byte[])
			 */
			@Override
			public void write(byte[] b) throws IOException {
				byteArrayOutputStream.write(b);
			}

			/* (non-Javadoc)
			 * @see java.io.OutputStream#write(byte[], int, int)
			 */
			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				byteArrayOutputStream.write(b, off, len);
			}

			@Override
			public void setWriteListener(WriteListener listener) {
				
			}
			
			@Override
			public boolean isReady() {
				return false;
			}
		};
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponseWrapper#flushBuffer()
	 */
	@Override
	public void flushBuffer() throws IOException {
		byteArrayOutputStream.flush();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponseWrapper#reset()
	 */
	@Override
	public void reset() {
		byteArrayOutputStream.reset();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponseWrapper#resetBuffer()
	 */
	@Override
	public void resetBuffer() {
		byteArrayOutputStream.reset();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponseWrapper#getWriter()
	 */
	@Override
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter(new OutputStreamWriter(byteArrayOutputStream));
	}
	
	public String getResponseData(){
		return byteArrayOutputStream.toString();
	}

}
