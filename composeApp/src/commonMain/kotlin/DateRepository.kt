import kotlinx.coroutines.flow.Flow
import org.example.easy_key.database.EmotionDatabase
import org.example.easy_key.database.DateEntity

interface DateRepository {
    fun getAllRecordsFlow(): Flow<List<DateEntity>>
    suspend fun addRecord(entity: DateEntity)
    suspend fun count(): Int

}

class DateRepositoryImp(
    private val roomDatabase: EmotionDatabase,
    // api
    // store
): DateRepository {
    private val dao = roomDatabase.recordDao
    val allRecordFlow: Flow<List<DateEntity>> = dao.getAllAsFlow()

    override fun getAllRecordsFlow(): Flow<List<DateEntity>> {
        return dao.getAllAsFlow()
    }
    override suspend fun addRecord(item: DateEntity) {
        dao.insert(item)
    }

    override suspend fun count() = dao.count()
}