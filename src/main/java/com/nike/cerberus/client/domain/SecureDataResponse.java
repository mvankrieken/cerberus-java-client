/*
 * Copyright (c) 2020 Nike, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nike.cerberus.client.domain;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class SecureDataResponse {
	
	@SerializedName("request_id")
	private String requestId;
	
	@SerializedName("lease_id")
	private String leaseId = "";
	
	@SerializedName("renewable")
	private boolean renewable;
	
	@SerializedName("lease_duration")
	private int leaseDuration = 3600;
	
	@SerializedName("data")
	private Object data;
	
	@SerializedName("wrap_info")
	private Object wrapInfo;
	
	@SerializedName("warnings")
	private Object warnings;
	
	@SerializedName("auth")
	private Object auth;
	
	@SerializedName("metadata")
	private Map<String, String> metadata = new HashMap<>();

	public String getRequestId() {
		return requestId;
	}

	public String getLeaseId() {
		return leaseId;
	}

	public boolean isRenewable() {
		return renewable;
	}

	public int getLeaseDuration() {
		return leaseDuration;
	}

	public Object getData() {
		return data;
	}

	public Object getWrapInfo() {
		return wrapInfo;
	}

	public Object getWarnings() {
		return warnings;
	}

	public Object getAuth() {
		return auth;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	@Override
	public String toString() {
		return "SecureDataResponse [requestId=" + requestId + ", leaseId=" + leaseId + ", renewable=" + renewable
				+ ", leaseDuration=" + leaseDuration + ", data=" + data + ", wrapInfo=" + wrapInfo + ", warnings="
				+ warnings + ", auth=" + auth + ", metadata=" + metadata + "]";
	}

	
}
