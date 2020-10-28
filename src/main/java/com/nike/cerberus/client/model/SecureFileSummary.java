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

package com.nike.cerberus.client.model;

import org.joda.time.DateTime;

@Deprecated
public class SecureFileSummary {

    private String sdboxId;
    private String path;
    private int sizeInBytes;
    private String name;
    private String createdBy;
    private DateTime createdTs;
    private String lastUpdatedBy;
    private DateTime lastUpdatedTs;

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

    public DateTime getCreatedTs() {
        return createdTs;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public DateTime getLastUpdatedTs() {
        return lastUpdatedTs;
    }

	@Override
	public String toString() {
		return "SecureFileSummary [sdboxId=" + sdboxId + ", path=" + path + ", sizeInBytes=" + sizeInBytes + ", name="
				+ name + ", createdBy=" + createdBy + ", createdTs=" + createdTs + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", lastUpdatedTs=" + lastUpdatedTs + "]";
	}

}
