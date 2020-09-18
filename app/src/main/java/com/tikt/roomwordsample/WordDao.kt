package com.tikt.roomwordsample

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {

    //单纯按首字母排序
    @Query("SELECT * FROM word_table ORDER BY word ASC")//升序
    fun getAlphabetizedWords():LiveData<List<Word>>//以livedata返回


    @Insert(onConflict = OnConflictStrategy.IGNORE)//如果新插入的单词和已存在的完全相同（包括主键id）则忽略,但是后来主键改成了自动增加的id,所以重复的单词仍可以添加
    suspend fun insert(word:Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

}