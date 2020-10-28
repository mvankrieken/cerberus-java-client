package com.nike.cerberus.client.v2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nike.cerberus.client.CerberusV2Client;
import com.nike.cerberus.client.auth.CerberusCredentialsProvider;
import com.nike.cerberus.client.domain.IamPrincipalPermission;
import com.nike.cerberus.client.domain.SafeDepositBoxSummary;
import com.nike.cerberus.client.domain.SafeDepositBoxV2;
import com.nike.cerberus.client.domain.UserGroupPermission;
import com.nike.cerberus.client.exception.CerberusClientException;
import com.nike.cerberus.client.exception.CerberusServerApiException;
import com.nike.cerberus.client.factory.CerberusV2ClientFactory;
import com.nike.cerberus.client.v1.AbstractClientTest;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class CerberusV2ClientSdbTest extends AbstractClientTest{

    private CerberusV2Client cerberusClient;
    private MockWebServer mockWebServer;
    private String cerberusUrl;

    @Before
    public void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        cerberusUrl = "http://localhost:" + mockWebServer.getPort();
        final CerberusCredentialsProvider cerberusCredentialsProvider = mock(CerberusCredentialsProvider.class);
        cerberusClient = CerberusV2ClientFactory.getClient(
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
     * getSafeDepositBoxesV2
     */
    
    @Test
    public void getSafeDepositBoxes_returns_list_safe_deposit_box_summary() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(200);
        response.setBody(getResponseJson("sdb","list-v2-sdb"));
        mockWebServer.enqueue(response);

        List<SafeDepositBoxSummary> responce = cerberusClient.getSafeDepositBoxesV2();

        assertThat(responce).isNotNull();
        assertThat(responce).isNotEmpty();
        
        SafeDepositBoxSummary summary = responce.get(0);
        
        assertThat(summary.toString()).isNotNull();
        assertThat(summary.getId()).isNotNull();
        assertThat(summary.getName()).isNotNull();
        assertThat(summary.getPath()).isNotNull();
        assertThat(summary.getCategoryId()).isNotNull();
        
    }

    @Test(expected = CerberusServerApiException.class)
    public void getSafeDepositBoxesV2_throws_server_exception_if_response_is_not_ok() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(404);
        mockWebServer.enqueue(response);

        cerberusClient.getSafeDepositBoxesV2();
    }
    
    @Test(expected = CerberusClientException.class)
    public void getSafeDepositBoxesV2_throws_client_exception_if_response_is_not_ok_with_data() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(404);
        response.setBody("non-json");
        mockWebServer.enqueue(response);

        cerberusClient.getSafeDepositBoxesV2();
    }
    
    /*
     * getSafeDepositBoxV2
     */
    
    @Test
    public void getSafeDepositBoxV2_returns_sdb_by_id() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(200);
        response.setBody(getResponseJson("sdb","get-v2-sdb"));
        mockWebServer.enqueue(response);

        SafeDepositBoxV2 sdb = cerberusClient.getSafeDepositBoxV2("some-id");

        assertThat(sdb).isNotNull();
        assertThat(sdb.toString()).isNotNull();
        
		assertThat(sdb.getCategoryId()).isNotNull();
		assertThat(sdb.getCreatedBy()).isNotNull();
		assertThat(sdb.getCreatedTs()).isNotNull();
		assertThat(sdb.getDescription()).isNotNull();
		
		assertThat(sdb.getId()).isNotNull();
		assertThat(sdb.getLastUpdatedBy()).isNotNull();
		assertThat(sdb.getLastUpdatedTs()).isNotNull();
		assertThat(sdb.getName()).isNotNull();
		assertThat(sdb.getOwner()).isNotNull();
		assertThat(sdb.getPath()).isNotNull();
		
		assertThat(sdb.getUserGroupPermissions()).isNotNull();
		assertThat(sdb.getUserGroupPermissions()).isNotEmpty();
		
		// userGroupPermission
		UserGroupPermission userGroupPermission = sdb.getUserGroupPermissions().iterator().next();
		assertThat(userGroupPermission).isNotNull();
		
		assertThat(userGroupPermission.getCreatedBy()).isNotNull();
		assertThat(userGroupPermission.getCreatedTs()).isNotNull();
		assertThat(userGroupPermission.getId()).isNotNull();
		assertThat(userGroupPermission.getLastUpdatedBy()).isNotNull();
		assertThat(userGroupPermission.getLastUpdatedTs()).isNotNull();
		assertThat(userGroupPermission.getName()).isNotNull();
		assertThat(userGroupPermission.getRoleId()).isNotNull();
		
		// iamRolePermission
		assertThat(sdb.getIamPrincipalPermissions()).isNotNull();
		assertThat(sdb.getIamPrincipalPermissions()).isNotEmpty();
		
		IamPrincipalPermission iamPrincipalPermission = sdb.getIamPrincipalPermissions().iterator().next();
		assertThat(iamPrincipalPermission).isNotNull();
		
		assertThat(iamPrincipalPermission.getCreatedBy()).isNotNull();
		assertThat(iamPrincipalPermission.getCreatedTs()).isNotNull();
		assertThat(iamPrincipalPermission.getId()).isNotNull();
		assertThat(iamPrincipalPermission.getLastUpdatedBy()).isNotNull();
		assertThat(iamPrincipalPermission.getLastUpdatedTs()).isNotNull();
		assertThat(iamPrincipalPermission.getRoleId()).isNotNull();
		
    }

    @Test(expected = CerberusServerApiException.class)
    public void getSafeDepositBoxV2_throws_server_exception_if_response_is_not_ok() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(404);
        mockWebServer.enqueue(response);

        cerberusClient.getSafeDepositBoxV2("some-id");
    }
    
    @Test(expected = CerberusClientException.class)
    public void getSafeDepositBoxV2_throws_client_exception_if_response_is_not_ok_with_data() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(404);
        response.setBody("non-json");
        mockWebServer.enqueue(response);

        cerberusClient.getSafeDepositBoxV2("some-id");
    }
    
	@Test(expected = CerberusClientException.class)
	public void getSafeDepositBoxV2_by_null() {
		cerberusClient.getSafeDepositBoxV2(null);
	}
	
	/*
	 * deleteSafeDepositBoxV2
	 */

	@Test
	public void deleteSafeDepositBoxV2_by_id() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(200);
		mockWebServer.enqueue(response);
		cerberusClient.deleteSafeDepositBoxV2("some-id");
	}

	@Test(expected = CerberusClientException.class)
	public void deleteSafeDepositBoxV2_by_id_not_found() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		mockWebServer.enqueue(response);
		cerberusClient.deleteSafeDepositBoxV2("some-id");
	}

	@Test(expected = CerberusServerApiException.class)
	public void deleteSafeDepositBoxV2_throws_server_exception_if_response_is_not_ok() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(403);
		mockWebServer.enqueue(response);
		cerberusClient.deleteSafeDepositBoxV2("some-id");
	}
	
	@Test(expected = CerberusClientException.class)
	public void deleteSafeDepositBoxV2_by_null() {
		cerberusClient.deleteSafeDepositBoxV2(null);
	}
	
	/*
	 * createSafeDepositBoxV2
	 */

	@Test
	public void createSafeDepositBoxV2_returns_category_by_id() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(201);
		response.setBody(getResponseJson("sdb","create-v2-sdb"));
		mockWebServer.enqueue(response);

		SafeDepositBoxV2 sdb = cerberusClient.createSafeDepositBoxV2(buildSample());

        assertThat(sdb).isNotNull();
        assertThat(sdb.toString()).isNotNull();
        
		assertThat(sdb.getCategoryId()).isNotNull();
		assertThat(sdb.getCreatedBy()).isNotNull();
		assertThat(sdb.getCreatedTs()).isNotNull();
		assertThat(sdb.getDescription()).isNotNull();
		
		assertThat(sdb.getId()).isNotNull();
		assertThat(sdb.getLastUpdatedBy()).isNotNull();
		assertThat(sdb.getLastUpdatedTs()).isNotNull();
		assertThat(sdb.getName()).isNotNull();
		assertThat(sdb.getOwner()).isNotNull();
		assertThat(sdb.getPath()).isNotNull();
		
		assertThat(sdb.getUserGroupPermissions()).isNotNull();
		assertThat(sdb.getUserGroupPermissions()).isNotEmpty();
		
		// userGroupPermission
		UserGroupPermission userGroupPermission = sdb.getUserGroupPermissions().iterator().next();
		assertThat(userGroupPermission).isNotNull();
		
		assertThat(userGroupPermission.getCreatedBy()).isNotNull();
		assertThat(userGroupPermission.getCreatedTs()).isNotNull();
		assertThat(userGroupPermission.getId()).isNotNull();
		assertThat(userGroupPermission.getLastUpdatedBy()).isNotNull();
		assertThat(userGroupPermission.getLastUpdatedTs()).isNotNull();
		assertThat(userGroupPermission.getName()).isNotNull();
		assertThat(userGroupPermission.getRoleId()).isNotNull();
		
		// iamRolePermission
		assertThat(sdb.getIamPrincipalPermissions()).isNotNull();
		assertThat(sdb.getIamPrincipalPermissions()).isNotEmpty();
		
		IamPrincipalPermission iamPrincipalPermission = sdb.getIamPrincipalPermissions().iterator().next();
		assertThat(iamPrincipalPermission).isNotNull();
		
		assertThat(iamPrincipalPermission.getCreatedBy()).isNotNull();
		assertThat(iamPrincipalPermission.getCreatedTs()).isNotNull();
		assertThat(iamPrincipalPermission.getId()).isNotNull();
		assertThat(iamPrincipalPermission.getLastUpdatedBy()).isNotNull();
		assertThat(iamPrincipalPermission.getLastUpdatedTs()).isNotNull();
		assertThat(iamPrincipalPermission.getRoleId()).isNotNull();
		assertThat(iamPrincipalPermission.getIamPrincipalArn()).isNotNull();
		assertThat(iamPrincipalPermission.getRoleId()).isNotNull();
		
	}

	@Test(expected = CerberusClientException.class)
	public void createSafeDepositBoxV2_by_null() {
		cerberusClient.createSafeDepositBoxV2(null);
	}

	@Test(expected = CerberusServerApiException.class)
	public void createSafeDepositBoxV2_throws_server_exception_if_response_is_not_ok() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		mockWebServer.enqueue(response);

		cerberusClient.createSafeDepositBoxV2(buildSample());
	}

	@Test(expected = CerberusClientException.class)
	public void createSafeDepositBoxV2_throws_client_exception_if_response_is_not_ok_with_data() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		response.setBody("non-json");
		mockWebServer.enqueue(response);
		
		cerberusClient.createSafeDepositBoxV2(buildSample());
	}
	
	/*
	 * updateSafeDepositBoxV2
	 */

	@Test
	public void updateSafeDepositBoxV2_returns_category_by_id() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(200);
		mockWebServer.enqueue(response);

		cerberusClient.updateSafeDepositBoxV2("some-id",buildSample());
	}

	@Test(expected = CerberusClientException.class)
	public void updateSafeDepositBoxV2_by_null_sdb() {
		cerberusClient.updateSafeDepositBox("some-id",null);
	}
	
	@Test(expected = CerberusClientException.class)
	public void updateSafeDepositBoxV2_by_null_id() {
		cerberusClient.updateSafeDepositBoxV2(null,buildSample());
	}
	
	@Test(expected = CerberusClientException.class)
	public void updateSafeDepositBoxV2_by_null_sdb_and_id() {
		cerberusClient.updateSafeDepositBox(null,null);
	}

	@Test(expected = CerberusServerApiException.class)
	public void updateSafeDepositBoxV2_throws_server_exception_if_response_is_not_ok() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		mockWebServer.enqueue(response);

		cerberusClient.updateSafeDepositBoxV2("some-id",buildSample());
	}

	@Test(expected = CerberusClientException.class)
	public void updateSafeDepositBoxV2_throws_client_exception_if_response_is_not_ok_with_data() {
		final MockResponse response = new MockResponse();
		response.setResponseCode(404);
		response.setBody("non-json");
		mockWebServer.enqueue(response);
		
		cerberusClient.updateSafeDepositBoxV2("some-id",buildSample());
	}
	
	/*
	 * Helper
	 */

	private SafeDepositBoxV2 buildSample() {
		SafeDepositBoxV2 input = new SafeDepositBoxV2();
		input.setCategoryId("some-category");
		input.setCreatedBy("owner");
		input.setCreatedTs(OffsetDateTime.now());
		input.setDescription("some description");
		input.setLastUpdatedBy("owner");
		input.setLastUpdatedTs(OffsetDateTime.now());
		input.setName("some-sdb");
		input.setOwner("owner");
		input.setPath("/some/path");

		IamPrincipalPermission iamPermission = new IamPrincipalPermission();
		iamPermission.setCreatedBy("owner");
		iamPermission.setCreatedTs(OffsetDateTime.now());
		iamPermission.setId("some-id");
		iamPermission.setLastUpdatedBy("owner");
		iamPermission.setLastUpdatedTs(OffsetDateTime.now());
		iamPermission.setRoleId("some-role-id");
		iamPermission.setIamPrincipalArn("some-arn");
		input.addIamPrincipalPermissions(iamPermission);
		
		// else not coverd by unit test
		input.setIamPrincipalPermissions(input.getIamPrincipalPermissions());
		
		UserGroupPermission userPermission = new UserGroupPermission();
		userPermission.setCreatedBy("owner");
		userPermission.setCreatedTs(OffsetDateTime.now());
		userPermission.setId("some-id");
		userPermission.setLastUpdatedBy("owner");
		userPermission.setLastUpdatedTs(OffsetDateTime.now());
		userPermission.setName("some-name");
		userPermission.setRoleId("some-id");
		input.addUserGroupPermission(userPermission);
		
		// else not coverd by unit test
		input.setUserGroupPermissions(input.getUserGroupPermissions());
		
		return input;
	}
    
}