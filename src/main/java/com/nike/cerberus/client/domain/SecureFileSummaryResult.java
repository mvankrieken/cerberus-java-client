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

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SecureFileSummaryResult {

	@SerializedName("has_next")
	private boolean hasNext = false;
	
	@SerializedName("next_offset")
	private Integer nextOffset = null;
	
	@SerializedName("limit")
	private int limit = 0;
	
	@SerializedName("offset")
	private int offset = 0;
	
	@SerializedName("file_count_in_result")
	private int fileCountInResult;
	
	@SerializedName("total_file_count")
	private int totalFileCount;
	
	@SerializedName("secure_file_summaries")
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
		return "SecureFileSummaryResult [hasNext=" + hasNext + ", nextOffset=" + nextOffset + ", limit=" + limit
				+ ", offset=" + offset + ", fileCountInResult=" + fileCountInResult + ", totalFileCount="
				+ totalFileCount + ", secureFileSummaries=" + secureFileSummaries + "]";
	}

}
