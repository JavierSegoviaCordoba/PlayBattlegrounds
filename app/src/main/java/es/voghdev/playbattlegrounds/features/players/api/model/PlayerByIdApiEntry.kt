/*
 * Copyright (C) 2018 Olmo Gallegos Hernández.
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
package es.voghdev.playbattlegrounds.features.players.api.model

import es.voghdev.playbattlegrounds.features.players.model.Player

class PlayerByIdApiEntry(
    val type: String,
    val id: String,
    val attributes: PlayerAttributesApiEntry,
    val relationships: PlayerRelationshipsApiEntry?,
    val links: PlayerLinksApiEntry
) {
    fun toDomain(): Player {
        return Player(
            id = id,
            name = attributes.name,
            patchVersion = attributes.patchVersion,
            titleId = attributes.titleId,
            matches = relationships?.matches?.data?.map { it.toDomain() } ?: emptyList(),
            shardId = attributes.shardId,
            link = links.self
        )
    }
}
