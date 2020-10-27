/*
 * Copyright (c) 2018 Nike, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nike.cerberus.client.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nike.cerberus.client.CerberusClient;
import com.nike.cerberus.client.auth.CerberusCredentialsProvider;
import com.nike.cerberus.client.domain.SecureFileSummary;
import com.nike.cerberus.client.domain.SecureFileSummaryResult;
import com.nike.cerberus.client.exception.CerberusClientException;
import com.nike.cerberus.client.exception.CerberusServerApiException;
import com.nike.cerberus.client.factory.CerberusClientFactory;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class CerberusClientSecureFileTest extends AbstractClientTest {

	private CerberusClient cerberusClient;
	private MockWebServer mockWebServer;
	private String cerberusUrl;

	@Before
	public void setup() throws IOException {
		mockWebServer = new MockWebServer();
		mockWebServer.start();
		cerberusUrl = "http://localhost:" + mockWebServer.getPort();
		final CerberusCredentialsProvider cerberusCredentialsProvider = mock(CerberusCredentialsProvider.class);
		cerberusClient = CerberusClientFactory.getClient(cerberusUrl, cerberusCredentialsProvider);

		when(cerberusCredentialsProvider.getCredentials()).thenReturn(new TestCerberusCredentials());
	}

	@After
	public void teardown() throws IOException {
		mockWebServer.shutdown();
		mockWebServer.close();
	}

	/*
	 * getSecureFile
	 */

	@Test
	public void getSecureFile_returns_byte_array() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(200);
		response.setBody(getResponseBytes("securefile", "get-securefile"));
		mockWebServer.enqueue(response);

		byte[] responce = cerberusClient.getSecureFile("some-category", "some-sdbName", "some-path");

		assertThat(responce).isNotNull();
		assertThat(responce).isNotEmpty();
	}
	
	@Test
	public void getSecureFile_returns_byte_array_with_versionid() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(200);
		response.setBody(getResponseBytes("securefile", "get-securefile"));
		mockWebServer.enqueue(response);

		byte[] responce = cerberusClient.getSecureFile("some-category", "some-sdbName", "some-path","some-version");

		assertThat(responce).isNotNull();
		assertThat(responce).isNotEmpty();
	}
	
	@Test
	public void getSecureFile_returns_byte_array_with_versionid_null() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(200);
		response.setBody(getResponseBytes("securefile", "get-securefile"));
		mockWebServer.enqueue(response);

		byte[] responce = cerberusClient.getSecureFile("some-category", "some-sdbName", "some-path",null);

		assertThat(responce).isNotNull();
		assertThat(responce).isNotEmpty();
	}

	@Test(expected = CerberusClientException.class)
	public void getSecureFile_by_null_category() {
		cerberusClient.getSecureFile(null, "some-sdb", "some-path");
	}

	@Test(expected = CerberusClientException.class)
	public void getSecureFile_by_null_sdb() {
		cerberusClient.getSecureFile("some-category", null, "some-path");
	}

	@Test(expected = CerberusClientException.class)
	public void getSecureFile_by_null_path() {
		cerberusClient.getSecureFile("some-category", "some-sdb", null);
	}

	@Test(expected = CerberusServerApiException.class)
	public void getSecureFile_throws_server_exception_if_response_is_not_ok() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		mockWebServer.enqueue(response);

		cerberusClient.getSecureFile("some-category", "some-sdbName", "some-path");
	}

	@Test(expected = CerberusClientException.class)
	public void getSecureFile_throws_client_exception_if_response_is_not_ok_with_data() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		response.setBody("non-json");
		mockWebServer.enqueue(response);

		cerberusClient.getSecureFile("some-category", "some-sdbName", "some-path");
	}
	
	/*
	 * deleteSecurefile
	 */

	@Test
	public void deleteSecurefile_return_void() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(200);
		mockWebServer.enqueue(response);

		cerberusClient.deleteSecurefile("some-category", "some-sdbName", "some-path");
	}
	
	@Test(expected = CerberusClientException.class)
	public void deleteSecurefile_by_null_category() {
		cerberusClient.deleteSecurefile(null, "some-sdb", "some-path");
	}

	@Test(expected = CerberusClientException.class)
	public void deleteSecurefile_by_null_sdb() {
		cerberusClient.deleteSecurefile("some-category", null, "some-path");
	}

	@Test(expected = CerberusClientException.class)
	public void deleteSecurefile_by_null_path() {
		cerberusClient.deleteSecurefile("some-category", "some-sdb", null);
	}

	@Test(expected = CerberusServerApiException.class)
	public void deleteSecurefile_throws_server_exception_if_response_is_not_ok() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		mockWebServer.enqueue(response);

		cerberusClient.deleteSecurefile("some-category", "some-sdbName", "some-path");
	}
	
	/*
	 * writeSecureFile
	 */

	@Test
	public void writeSecureFile_return_void() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(204);
		mockWebServer.enqueue(response);

		cerberusClient.writeSecureFile("some-category", "some-sdbName", "some-path",getResponseBytes("securefile", "get-securefile").readByteArray());
	}
	
	@Test(expected = CerberusClientException.class)
	public void writeSecureFile_by_null_category() {
		cerberusClient.writeSecureFile(null, "some-sdb", "some-path",getResponseBytes("securefile", "get-securefile").readByteArray());
	}

	@Test(expected = CerberusClientException.class)
	public void writeSecureFile_by_null_sdb() {
		cerberusClient.writeSecureFile("some-category", null, "some-path",getResponseBytes("securefile", "get-securefile").readByteArray());
	}

	@Test(expected = CerberusClientException.class)
	public void writeSecureFile_by_null_path() {
		cerberusClient.writeSecureFile("some-category", "some-sdb", null,getResponseBytes("securefile", "get-securefile").readByteArray());
	}
	
	@Test(expected = CerberusClientException.class)
	public void writeSecureFile_by_null_content() {
		cerberusClient.writeSecureFile("some-category", "some-sdb", "some-path",null);
	}

	@Test(expected = CerberusServerApiException.class)
	public void writeSecureFile_throws_server_exception_if_response_is_not_ok() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		mockWebServer.enqueue(response);

		cerberusClient.writeSecureFile("some-category", "some-sdbName", "some-path",getResponseBytes("securefile", "get-securefile").readByteArray());
	}
	
	/*
	 * listSecureFile
	 */

	@Test
	public void listSecureFile_returns_secure_file_summary_result() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(200);
		response.setBody(getResponseJson("securefile","list-securefile"));
		mockWebServer.enqueue(response);

		SecureFileSummaryResult responce = cerberusClient.listSecureFile("some-category", "some-sdbName");

		assertThat(responce).isNotNull();
		assertThat(responce.toString()).isNotNull();
		
		assertThat(responce.getFileCountInResult()).isEqualTo(3);
		assertThat(responce.getLimit()).isEqualTo(100);
		assertThat(responce.getNextOffset()).isNull();
		assertThat(responce.getOffset()).isEqualTo(0);
		assertThat(responce.getTotalFileCount()).isEqualTo(3);

		assertThat(responce.getSecureFileSummaries()).isNotNull();
		
		SecureFileSummary secureFileSummary = responce.getSecureFileSummaries().get(0);
		assertThat(secureFileSummary).isNotNull();
		assertThat(secureFileSummary.toString()).isNotNull();
		
		assertThat(secureFileSummary.getCreatedBy()).isNotNull();
		assertThat(secureFileSummary.getCreatedTs()).isNotNull();
		assertThat(secureFileSummary.getLastUpdatedBy()).isNotNull();
		assertThat(secureFileSummary.getLastUpdatedTs()).isNotNull();
		assertThat(secureFileSummary.getName()).isNotNull();
		assertThat(secureFileSummary.getPath()).isNotNull();
		assertThat(secureFileSummary.getPath()).isNotNull();
		assertThat(secureFileSummary.getSdboxId()).isNotNull();
		assertThat(secureFileSummary.getSizeInBytes()).isEqualTo(1983);
		
	}

	@Test(expected = CerberusClientException.class)
	public void getSecureFileMetadata_by_null_category() {
		cerberusClient.listSecureFile(null, "some-sdb");
	}

	@Test(expected = CerberusClientException.class)
	public void getSecureFileMetadata_by_null_sdb() {
		cerberusClient.listSecureFile("some-category", null);
	}

    @Test(expected = CerberusServerApiException.class)
    public void getSecureFileMetadata_throws_server_exception_if_response_is_not_ok() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(404);
        mockWebServer.enqueue(response);

        cerberusClient.listSecureFile("some-category", "some-sdbName");
    }
    
    @Test(expected = CerberusClientException.class)
    public void getSecureFileMetadata_throws_client_exception_if_response_is_not_ok_with_data() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(404);
        response.setBody("non-json");
        mockWebServer.enqueue(response);

        cerberusClient.listSecureFile("some-category", "some-sdbName");
    }

}