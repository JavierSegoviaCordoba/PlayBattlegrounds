package es.voghdev.playbattlegrounds.features.players

import arrow.core.Either
import es.voghdev.playbattlegrounds.common.AbsError
import es.voghdev.playbattlegrounds.common.Ok
import es.voghdev.playbattlegrounds.features.players.model.Player
import es.voghdev.playbattlegrounds.features.players.usecase.GetPlayerById
import es.voghdev.playbattlegrounds.features.players.usecase.GetPlayerByName
import es.voghdev.playbattlegrounds.features.season.Season
import es.voghdev.playbattlegrounds.features.season.model.PlayerSeasonGameModeStats
import es.voghdev.playbattlegrounds.features.season.model.PlayerSeasonInfo
import es.voghdev.playbattlegrounds.features.season.usecase.GetPlayerSeasonInfo

class PlayerRepository(
        val getPlayerByIdApiDataSource: GetPlayerById,
        val getPlayerByNameApiDataSource: GetPlayerByName,
        val tooManyRequestsError: String,
        val getPlayerSeasonInfo: GetPlayerSeasonInfo
) :
        GetPlayerByName,
        GetPlayerById by getPlayerByIdApiDataSource {

    var start = 0L
    var seasonInfoClock = 0L
    var playerSeasonInfo = emptyPlayerSeasonInfo()

    override fun getPlayerByName(name: String, region: String): Either<AbsError, Player> {
        val result = getPlayerByNameApiDataSource.getPlayerByName(name, region)

        return if (userCanRequest(System.currentTimeMillis())) result
        else Either.left(AbsError(tooManyRequestsError))
    }

    fun userCanRequest(ms: Long): Boolean {
        /*
        * Bluehole has announced the traffic limit in the /matches request has been removed,
        * So no more throttling, Yeehaa!
        * */
        return true
    }

    fun getPlayerSeasonInfo(player: Player, season: Season, ms: Long): Either<AbsError, PlayerSeasonInfo> {
        return if (playerSeasonInfo.isEmpty() || ms > seasonInfoClock.plus(5000)) {
            val result = getPlayerSeasonInfo.getPlayerSeasonInfo(player, season)
            playerSeasonInfo = (result as? Ok)?.b ?: emptyPlayerSeasonInfo()
            seasonInfoClock = ms
            result
        } else {
            Either.right(playerSeasonInfo)
        }
    }

    private fun emptyPlayerSeasonInfo() = PlayerSeasonInfo(
            PlayerSeasonGameModeStats(),
            PlayerSeasonGameModeStats(),
            PlayerSeasonGameModeStats(),
            PlayerSeasonGameModeStats(),
            PlayerSeasonGameModeStats(),
            PlayerSeasonGameModeStats()
    )
}
