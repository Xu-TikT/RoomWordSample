package com.tikt.roomwordsample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 请勿保留生命周期比ViewModel短的上下文的引用！例如：
 * Activity,Fragment,View
 * 保留引用可能导致内存泄漏，例如ViewModel引用了被销毁的Activity！所有这些对象都可以由操作系统销毁，并在进行配置更改时重新创建，
 * 并且在ViewModel的生命周期中可能会多次发生。
 * 如果您需要应用程序上下文（具有与应用程序一样长的生命周期），请使用AndroidViewModel，如本代码实验室中所示。
 * 如果想让viewModels里的数据在app销毁后也能存在请参考Saved State module for ViewModels
 * ViewModels don't survive the app's process being killed in the background when the OS needs more resources.
 * For UI data that needs to survive process death due to running out of resources,
 * you can use the Saved State module for ViewModels.
 */
class WordViewModel(application: Application) : AndroidViewModel(application) {

    //添加了一个私有成员变量来保存对存储库的引用。
    private val repository: WordRepository
    //添加了一个公共LiveData成员变量来缓存单词列表。
    val allWords: LiveData<List<Word>>

    //Created an init block that gets a reference to the WordDao from the WordRoomDatabase.
    init {

        val wordsDao = WordRoomDatabase.getDatabase(application,viewModelScope).wordDao()
        //In the init block, constructed the WordRepository based on the WordRoomDatabase.
        repository = WordRepository(wordsDao)
        //In the init block, initialized the allWords LiveData using the repository.
        allWords = repository.allWords

    }

    /**
     * Created a wrapper insert() method that calls the Repository's insert() method. In this way,
     * the implementation of insert() is encapsulated from the UI. We don't want insert to block the main thread,
     * so we're launching a new coroutine and calling the repository's insert, which is a suspend function.
     * As mentioned,
     * ViewModels have a coroutine scope based on their life cycle called viewModelScope, which we use here.
     */
    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}