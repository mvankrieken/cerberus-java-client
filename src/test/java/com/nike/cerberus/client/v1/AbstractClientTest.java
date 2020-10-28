package com.nike.cerberus.client.v1;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

import com.nike.cerberus.client.auth.CerberusCredentials;

import okhttp3.OkHttpClient;
import okio.Buffer;

public abstract class AbstractClientTest {

	public OkHttpClient buildHttpClient(int timeout, TimeUnit timeoutUnit) {
        return new OkHttpClient.Builder()
                .connectTimeout(timeout, timeoutUnit)
                .writeTimeout(timeout, timeoutUnit)
                .readTimeout(timeout, timeoutUnit)
                .build();
    }

    public String getResponseJson(final String title) {
        InputStream inputStream = getClass().getResourceAsStream(
                String.format("/com/nike/cerberus/client/%s.json", title));
        try {
            return IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
    
    public Buffer getResponseBytes(final String object,final String title) {
    	InputStream inputStream = getClass().getResourceAsStream(String.format("/com/nike/cerberus/client/%s/%s.txt", object,title));
        Buffer buffer = new Buffer();
        try {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
         
            buffer.flush();
            return buffer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        	IOUtils.closeQuietly(buffer);
            IOUtils.closeQuietly(inputStream);
           
        }
    }
    
    public String getResponseJson(final String object,final String title) {
        InputStream inputStream = getClass().getResourceAsStream(String.format("/com/nike/cerberus/client/%s/%s.json", object,title));
        try {
            return IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public class TestCerberusCredentials implements CerberusCredentials {
        @Override
        public String getToken() {
            return "TOKEN";
        }
    }
}
