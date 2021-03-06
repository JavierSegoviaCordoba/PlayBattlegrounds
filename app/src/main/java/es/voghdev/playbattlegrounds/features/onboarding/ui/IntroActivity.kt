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
package es.voghdev.playbattlegrounds.features.onboarding.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import es.voghdev.playbattlegrounds.R
import es.voghdev.playbattlegrounds.common.Success
import es.voghdev.playbattlegrounds.common.asApp
import es.voghdev.playbattlegrounds.features.onboarding.model.Region
import es.voghdev.playbattlegrounds.features.onboarding.usecase.GetPlayerAccount
import es.voghdev.playbattlegrounds.features.onboarding.usecase.GetRegions
import es.voghdev.playbattlegrounds.features.onboarding.usecase.SetPlayerAccount
import es.voghdev.playbattlegrounds.features.onboarding.usecase.SetPlayerRegion
import es.voghdev.playbattlegrounds.features.players.ui.activity.PlayerSearchActivity
import es.voghdev.playbattlegrounds.features.season.usecase.GetSeasons
import es.voghdev.playbattlegrounds.features.season.usecase.SetCurrentSeason
import es.voghdev.playbattlegrounds.hideSoftKeyboard
import es.voghdev.playbattlegrounds.startActivity
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class IntroActivity : AppCompatActivity(), KodeinAware {
    val DEFAULT_REGION = Region("pc-eu")

    override val kodein: Kodein by lazy { applicationContext.asApp().kodein }

    private val setPlayerAccount: SetPlayerAccount by instance()
    private val getPlayerAccount: GetPlayerAccount by instance()
    private val setUserRegion: SetPlayerRegion by instance()
    private val getRegions: GetRegions by instance()
    private val getSeasons: GetSeasons by instance()
    private val setCurrentSeason: SetCurrentSeason by instance()
    private val dispatcher = Dispatchers.IO
    private val coroutineScope = IntroScope()

    class IntroScope : CoroutineScope by MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_intro)

        rootView.setOnClickListener {
            hideSoftKeyboard(userEditText)
        }

        startButton.setOnClickListener {
            rootView.setTransition(R.id.start, R.id.step2)
            rootView.transitionToEnd()
        }

        step2NextButton.setOnClickListener {
            rootView.setTransition(R.id.step2, R.id.step3)
            rootView.transitionToEnd()
        }

        sendButton.setOnClickListener {
            val playerName = userEditText.text.toString().trim()

            setPlayerAccount.setPlayerAccount(playerName)

            startActivity<PlayerSearchActivity>("playerName" to playerName)

            finish()
        }

        val playerAccount = getPlayerAccount.getPlayerAccount()
        if (playerAccount is Success && playerAccount.b.isNotEmpty()) {
            startActivity<PlayerSearchActivity>()

            finish()
        }

        fillServersSpinner()
        storeCurrentSeason()
    }

    private fun storeCurrentSeason() {
        coroutineScope.launch {
            val seasonsResult = withContext(dispatcher) {
                getSeasons.getSeasons()
            }

            if (seasonsResult is Success) {
                val currentSeason = seasonsResult.b.firstOrNull { it.isCurrentSeason }
                if (currentSeason != null)
                    setCurrentSeason.setCurrentSeason(currentSeason)
            }
        }
    }

    private fun fillServersSpinner() {
        val result = getRegions.getRegions()
        if (result is Success) {
            serverSpinner.attachDataSource(result.b.toMutableList())

            serverSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) = Unit

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    setUserRegion.setCurrentRegion(result.b.elementAtOrElse(position, { DEFAULT_REGION }))

                    rootView.setTransition(R.id.step3, R.id.step4)
                    rootView.transitionToEnd()
                }
            })
        }
    }
}