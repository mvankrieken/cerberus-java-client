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

import java.time.OffsetDateTime;

import com.google.gson.annotations.SerializedName;

public class SecureFileSummary {

	@SerializedName("sdbox_id")
	private String sdboxId;

	@SerializedName("path")
	private String path;

	@SerializedName("size_in_bytes")
	private int sizeInBytes;

	@SerializedName("name")
	private String name;

	@SerializedName("created_by")
	private String createdBy;

	@SerializedName("created_ts")
	private OffsetDateTime createdTs;

	@SerializedName("last_updated_by")
	private String lastUpdatedBy;

	@SerializedName("last_updated_ts")
	private OffsetDateTime lastUpdatedTs;

	public String getSdboxId() {
		return sdboxId;
	}

	public String getPath() {
		return path;
	}

	public int getSizeInBytes() {
		return sizeInBytes;
	}

	public String getName() {
		return name;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public OffsetDateTime getCreatedTs() {
		return createdTs;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public OffsetDateTime getLastUpdatedTs() {
		return lastUpdatedTs;
	}

	@Override
	public String toString() {
		return "SecureFileSummary [sdboxId=" + sdboxId + ", path=" + path + ", sizeInBytes=" + sizeInBytes + ", name="
				+ name + ", createdBy=" + createdBy + ", createdTs=" + createdTs + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", lastUpdatedTs=" + lastUpdatedTs + "]";
	}
	
	
}
