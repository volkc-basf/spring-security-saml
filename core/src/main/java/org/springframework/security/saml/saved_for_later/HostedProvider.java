/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.springframework.security.saml.saved_for_later;

import java.util.Map;

import org.springframework.security.saml.registration.ExternalProviderConfiguration;
import org.springframework.security.saml.registration.HostedProviderConfiguration;
import org.springframework.security.saml.saml2.metadata.Metadata;
import org.springframework.util.Assert;

public interface HostedProvider<
	Configuration extends HostedProviderConfiguration,
	LocalMetadata extends Metadata,
	RemoteConfiguration extends ExternalProviderConfiguration,
	RemoteMetadata extends Metadata> {

	Configuration getConfiguration();

	LocalMetadata getMetadata();

	Map<RemoteConfiguration,RemoteMetadata> getRemoteProviders();

	default RemoteMetadata getRemoteProvider(String entityId) {
		Assert.notNull(entityId, "Entity ID can not be null");
		return getRemoteProviders().entrySet().stream()
			.map(e -> e.getValue())
			.filter(p -> entityId.equals(p.getEntityId()))
			.findFirst()
			.orElse(null);
	}
}