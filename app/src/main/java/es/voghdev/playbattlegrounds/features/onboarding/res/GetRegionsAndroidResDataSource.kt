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
package es.voghdev.playbattlegrounds.features.onboarding.res

import android.content.Context
import arrow.core.Either
import es.voghdev.playbattlegrounds.R
import es.voghdev.playbattlegrounds.common.AbsError
import es.voghdev.playbattlegrounds.features.onboarding.model.Region
import es.voghdev.playbattlegrounds.features.onboarding.usecase.GetRegions

class GetRegionsAndroidResDataSource(val appContext: Context) : GetRegions {
    override fun getRegions(): Either<AbsError, List<Region>> {
        if (appContext == null)
            return Either.left(AbsError("You must pass a non-null Application Context"))

        return Either.right(
            appContext
                .resources
                .getStringArray(R.array.servers)
                .map { Region(it) }
        )
    }
}