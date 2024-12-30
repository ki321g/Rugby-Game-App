package ie.setu.rugbygame.ui.components.game

object ScoreCalculator {
    private const val TRY_POINTS = 5
    private const val CONVERSION_POINTS = 2
    private const val PENALTY_POINTS = 3
    private const val DROP_GOAL_POINTS = 3

    fun calculateTotalScore(
        tries: Int,
        conversions: Int,
        penalties: Int,
        dropGoals: Int
    ): Int = (tries * TRY_POINTS) +
            (conversions * CONVERSION_POINTS) +
            (penalties * PENALTY_POINTS) +
            (dropGoals * DROP_GOAL_POINTS)
}
