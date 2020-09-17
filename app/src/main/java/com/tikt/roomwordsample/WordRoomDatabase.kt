package com.tikt.roomwordsample

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * 通常一个APP只需要一个Room Database实例
 */
// Annotates class to be a Room Database with a table (entity) of the Word class
//Room的数据库类必须是abstract,且继承RoomDatabase
//entities:每个实体类就是一张表,version:版本号,exportSchema:
//数据库迁移不在此代码实验室的范围内，因此exportSchema在此处设置为false以避免生成警告。
// 在真实的应用程序中，您应该考虑为Room设置目录以用于导出schema，以便可以将当前schema签入版本控制系统。
@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {

    //The database exposes DAOs through an abstract "getter" method for each @Dao.
    abstract fun wordDao(): WordDao

    //添加callback,APP启动后情况数据库并添加固定单词
    class WordDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.wordDao())
                }
            }

        }


        suspend fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()

            var word = Word( "Hello")
            wordDao.insert(word)
            word = Word( "World!")
            wordDao.insert(word)

            // TODO: Add your own words!
            word = Word("TODO!")
            wordDao.insert(word)
        }

    }

    companion object {

        @Volatile
        private var INSTANCE: WordRoomDatabase? = null
        //getDatabase返回单例。它将在首次访问数据库时使用Room的数据库构建器RoomDatabase在类的应用程序上下
        // 文中创建一个对象WordRoomDatabase并将其命名，从而创建数据库"word_database"。
        fun getDatabase(context: Context,scope:CoroutineScope): WordRoomDatabase {
            //我们定义了单例，WordRoomDatabase,以防止同时打开多个数据库实例。
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_table"
                ).addCallback(WordDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }

        }
    }

}