package ie.setu.rugbygame.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.setu.rugbygame.data.model.DonationModel

@Database(entities = [DonationModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDonationDAO(): DonationDAO
}