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

import java.util.List;

@Deprecated
public class CerberusListFilesResponse {

    private boolean hasNext = false;
    private Integer nextOffset = null;
    private int limit = 0;
    private int offset = 0;
    private int fileCountInResult;
    private int totalFileCount;
    private List<SecureFileSummary> secureFileSummaries;

    public boolean isHasNext() {
        return hasNext;
    }

    public Integer getNextOffset() {
        return nextOffset;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getFileCountInResult() {
        return fileCountInResult;
    }

    public int getTotalFileCount() {
        return totalFileCount;
    }

    public List<SecureFileSummary> getSecureFileSummaries() {
        return secureFileSummaries;
    }

	@Override
	public String toString() {
		return "CerberusListFilesResponse [hasNext=" + hasNext + ", nextOffset=" + nextOffset + ", limit=" + limit
				+ ", offset=" + offset + ", fileCountInResult=" + fileCountInResult + ", totalFileCount="
				+ totalFileCount + ", secureFileSummaries=" + secureFileSummaries + "]";
	}
    
    
}
