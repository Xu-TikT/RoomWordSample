package com.tikt.roomwordsample

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {

    //单纯按首字母排序
    @Query("SELECT * FROM word_table ORDER BY word ASC")//升序
    fun getAlphabetizedWords():LiveData<List<Word>>//以livedata返回


    @Insert(onConflict = OnConflictStrategy.IGNORE)//如果新插入的单词已经有了则忽略
    suspend fun insert(word:Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

}