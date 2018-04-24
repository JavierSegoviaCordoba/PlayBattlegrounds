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
package es.voghdev.playbattlegrounds.onboarding

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import es.voghdev.playbattlegrounds.R
import es.voghdev.playbattlegrounds.features.players.ui.activity.PlayerSearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btn_send.setOnClickListener {
            startActivity<PlayerSearchActivity>()
        }
    }
}