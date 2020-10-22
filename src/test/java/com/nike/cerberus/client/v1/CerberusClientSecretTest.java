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
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nike.cerberus.client.CerberusClient;
import com.nike.cerberus.client.auth.CerberusCredentialsProvider;
import com.nike.cerberus.client.domain.SecureDataResponse;
import com.nike.cerberus.client.exception.CerberusClientException;
import com.nike.cerberus.client.exception.CerberusServerApiException;
import com.nike.cerberus.client.factory.CerberusClientFactory;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class CerberusClientSecretTest extends AbstractClientTest{

    private CerberusClient cerberusClient;
    private MockWebServer mockWebServer;
    private String cerberusUrl;

    @Before
    public void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        cerberusUrl = "http://localhost:" + mockWebServer.getPort();
        final CerberusCredentialsProvider cerberusCredentialsProvider = mock(CerberusCredentialsProvider.class);
        cerberusClient = CerberusClientFactory.getClient(
                cerberusUrl,
                cerberusCredentialsProvider);

        when(cerberusCredentialsProvider.getCredentials()).thenReturn(new TestCerberusCredentials());
    }

    @After
    public void teardown() throws IOException {
        mockWebServer.shutdown();
        mockWebServer.close();
    }

    /*
     * getSecret
     */
    
    @Test
    public void getSecret_by_category_sdb_path() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(200);
        response.setBody(getResponseJson("secret","get-secret"));
        mockWebServer.enqueue(response);

        SecureDataResponse responce = cerberusClient.getSecret("some-category", "some-sdb", "some-path");

        assertThat(responce).isNotNull();
        assertThat(responce.toString()).isNotNull();
        
        assertThat(responce.getAuth()).isNull();
        assertThat(responce.getData()).isNotNull();
        assertThat(responce.getLeaseDuration()).isEqualTo(3600);
        assertThat(responce.isRenewable()).isTrue();
       	assertThat(responce.getLeaseId()).isNotNull();
        assertThat(responce.getMetadata()).isNotNull();
        assertThat(responce.getRequestId()).isNotNull();
        assertThat(responce.getWarnings()).isNull();
        assertThat(responce.getWrapInfo()).isNull();
    }
    
    @Test
    public void getSecret_by_category_sdb_path_virtual_path() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(200);
        response.setBody(getResponseJson("secret","get-secret"));
        mockWebServer.enqueue(response);

        SecureDataResponse responce = cerberusClient.getSecret("some-category", "some-sdb", "some-path/");

        assertThat(responce).isNotNull();
        assertThat(responce.toString()).isNotNull();
    }
    
    @Test
    public void getSecret_by_category_sdb_path_version() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(200);
        response.setBody(getResponseJson("secret","get-secret"));
        mockWebServer.enqueue(response);

        SecureDataResponse responce = cerberusClient.getSecret("some-category", "some-sdb", "some-path", "version-id");

        assertThat(responce).isNotNull();
        assertThat(responce.toString()).isNotNull();
    }
    
    @Test
    public void getSecret_by_category_sdb_path_version_null() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(200);
        response.setBody(getResponseJson("secret","get-secret"));
        mockWebServer.enqueue(response);

        SecureDataResponse responce = cerberusClient.getSecret("some-category", "some-sdb", "some-path", null);

        assertThat(responce).isNotNull();
        assertThat(responce.toString()).isNotNull();
    }

    @Test
    public void getSecret_not_found() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(404);
        mockWebServer.enqueue(response);

        SecureDataResponse responce = cerberusClient.getSecret("some-category", "some-sdb", "some-path");
        assertThat(responce).isNotNull();
    }
    
	@Test(expected = CerberusClientException.class)
	public void getSecret_by_null_category() {
		cerberusClient.getSecret("some-category", null, "some-path");
	}
	
	@Test(expected = CerberusClientException.class)
	public void getSecret_by_null_sdb() {
		cerberusClient.getSecret("some-category", null, "some-path");
	}
	
	@Test(expected = CerberusClientException.class)
	public void getSecret_by_null_path() {
		cerberusClient.getSecret("some-category", "some-sdb", null);
	}
    
    @Test(expected = CerberusServerApiException.class)
    public void getSecret_throws_server_exception_if_response_is_not_ok() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(403);
        mockWebServer.enqueue(response);

        cerberusClient.getSecret("some-category", "some-sdb", "some-path");
    }
    
    @Test(expected = CerberusClientException.class)
    public void getSecret_throws_client_exception_if_response_is_not_ok_with_data() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(403);
        response.setBody("non-json");
        mockWebServer.enqueue(response);

        cerberusClient.getSecret("some-category", "some-sdb", "some-path");
    }
    
    /*
	 * deleteSecret
	 */

	@Test
	public void deleteSecret_by_category_sdb_path() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(204);
		mockWebServer.enqueue(response);
		cerberusClient.deleteSecret("some-category", "some-sdb", "some-path");
	}

	@Test(expected = CerberusServerApiException.class)
	public void deleteSecret_by_category_sdb_path_not_found() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		mockWebServer.enqueue(response);
		cerberusClient.deleteSecret("some-category", "some-sdb", "some-path");
	}

	@Test(expected = CerberusServerApiException.class)
	public void deleteSecret_throws_server_exception_if_response_is_not_ok() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(403);
		mockWebServer.enqueue(response);
		cerberusClient.deleteSecret("some-category", "some-sdb", "some-path");
	}
	
	@Test(expected = CerberusClientException.class)
	public void deleteSecret_by_null_category() {
		cerberusClient.deleteSecret("some-category", null, "some-path");
	}
	
	@Test(expected = CerberusClientException.class)
	public void deleteSecret_by_null_sdb() {
		cerberusClient.deleteSecret("some-category", null, "some-path");
	}
	
	@Test(expected = CerberusClientException.class)
	public void deleteSecret_by_null_path() {
		cerberusClient.deleteSecret("some-category", "some-sdb", null);
	}

	/*
	 * createSecret
	 */

	@Test
	public void createSecret_by_category_sdb_path() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(204);
		mockWebServer.enqueue(response);

		cerberusClient.createSecret("some-category", "some-sdb", "some-path",buildSample());
	}

	@Test(expected = CerberusClientException.class)
	public void createSecret_by_null_category() {
		cerberusClient.createSecret("some-category", null, "some-path",buildSample());
	}
	
	@Test(expected = CerberusClientException.class)
	public void createSecret_by_null_sdb() {
		cerberusClient.createSecret("some-category", null, "some-path",buildSample());
	}
	
	@Test(expected = CerberusClientException.class)
	public void createSecret_by_null_path() {
		cerberusClient.createSecret("some-category", "some-sdb", null,buildSample());
	}
	
	@Test(expected = CerberusClientException.class)
	public void createSecret_by_null_values() {
		cerberusClient.createSecret("some-category", "some-sdb", "some-path",null);
	}

	@Test(expected = CerberusServerApiException.class)
	public void createSecret_throws_server_exception_if_response_is_not_ok() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		mockWebServer.enqueue(response);

		cerberusClient.createSecret("some-category", "some-sdb", "some-path",buildSample());
	}

	@Test(expected = CerberusClientException.class)
	public void createSecret_throws_client_exception_if_response_is_not_ok_with_data() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		response.setBody("non-json");
		mockWebServer.enqueue(response);
		
		cerberusClient.createSecret("some-category", "some-sdb", "some-path",buildSample());
	}
	
	/*
	 * updateSecret
	 */

	@Test
	public void updateSecret_by_category_sdb_path() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(204);
		mockWebServer.enqueue(response);

		cerberusClient.updateSecret("some-category", "some-sdb", "some-path",buildSample());
	}

	@Test(expected = CerberusClientException.class)
	public void updateSecret_by_null_category() {
		cerberusClient.updateSecret("some-category", null, "some-path",buildSample());
	}
	
	@Test(expected = CerberusClientException.class)
	public void updateSecret_by_null_sdb() {
		cerberusClient.updateSecret("some-category", null, "some-path",buildSample());
	}
	
	@Test(expected = CerberusClientException.class)
	public void updateSecret_by_null_path() {
		cerberusClient.updateSecret("some-category", "some-sdb", null,buildSample());
	}
	
	@Test(expected = CerberusClientException.class)
	public void updateSecret_by_null_values() {
		cerberusClient.updateSecret("some-category", "some-sdb", "some-path",null);
	}

	@Test(expected = CerberusServerApiException.class)
	public void updateSecret_throws_server_exception_if_response_is_not_ok() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		mockWebServer.enqueue(response);

		cerberusClient.updateSecret("some-category", "some-sdb", "some-path",buildSample());
	}

	@Test(expected = CerberusClientException.class)
	public void updateSecret_throws_client_exception_if_response_is_not_ok_with_data() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		response.setBody("non-json");
		mockWebServer.enqueue(response);
		
		cerberusClient.updateSecret("some-category", "some-sdb", "some-path",buildSample());
	}
	
	/*
	 * Helper
	 */
	
	private Map<String,String> buildSample() {
		Map<String,String> input = new HashMap<String,String>();
		input.put("some-key", "some-value");
		return input;
	}
    
}