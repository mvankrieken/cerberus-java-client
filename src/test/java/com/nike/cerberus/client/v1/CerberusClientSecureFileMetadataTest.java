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
import com.nike.cerberus.client.exception.CerberusClientException;
import com.nike.cerberus.client.exception.CerberusServerApiException;
import com.nike.cerberus.client.factory.CerberusClientFactory;
import com.nike.cerberus.client.model.SecureFileMetadata;
import com.nike.cerberus.client.model.http.HttpHeader;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class CerberusClientSecureFileMetadataTest extends AbstractClientTest {

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
	public void getSecureFileMetadata_returns_secure_file_metadata() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(200);
		response.setHeader(HttpHeader.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", "some-filename"));
		response.setHeader(HttpHeader.CONTENT_LENGTH, 5001);
		mockWebServer.enqueue(response);

		SecureFileMetadata responce = cerberusClient.getSecureFileMetadata("some-category", "some-sdbName", "some-path");

		assertThat(responce).isNotNull();
		assertThat(responce.toString()).isNotNull();
		
		assertThat(responce.getFilename()).isNotNull();
		assertThat(responce.getContentLength()).isEqualTo(5001);
	}

	@Test(expected = CerberusClientException.class)
	public void getSecureFileMetadata_by_null_category() {
		cerberusClient.getSecureFileMetadata(null, "some-sdb", "some-path");
	}

	@Test(expected = CerberusClientException.class)
	public void getSecureFileMetadata_by_null_sdb() {
		cerberusClient.getSecureFileMetadata("some-category", null, "some-path");
	}

	@Test(expected = CerberusClientException.class)
	public void getSecureFileMetadata_by_null_path() {
		cerberusClient.getSecureFileMetadata("some-category", "some-sdb", null);
	}

	@Test(expected = CerberusServerApiException.class)
	public void getSecureFileMetadata_throws_server_exception_if_response_is_not_ok() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		response.setHeader(HttpHeader.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", "some-filename"));
		response.setHeader(HttpHeader.CONTENT_LENGTH, 5001);
		mockWebServer.enqueue(response);

		cerberusClient.getSecureFileMetadata("some-category", "some-sdbName", "some-path");
	}

	@Test(expected = CerberusClientException.class)
	public void getSecureFileMetadata_throws_client_exception_missing_header_disposition() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(200);
		response.setHeader(HttpHeader.CONTENT_LENGTH, 5001);
		mockWebServer.enqueue(response);

		cerberusClient.getSecureFileMetadata("some-category", "some-sdbName", "some-path");
	}


}