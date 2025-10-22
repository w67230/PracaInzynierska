package net.fryc.gra.storage.score

import kotlinx.coroutines.flow.Flow

class ScoreRepository(private val scoreDao : ScoreDao) {

    fun getAllScores() : Flow<List<Score>> {
        return this.scoreDao.getAllScores();
    }

    suspend fun saveScore(score : Score) {
        return this.scoreDao.saveScore(score);
    }
}