package com.tikt.roomwordsample

import androidx.lifecycle.LiveData

//存储库
//什么是存储库：存储库类抽象了对多个数据源的访问。该存储库不是体系结构组件库的一部分，而是建议的代码分离和体系结构最佳实践。
//Repository类提供了一个干净的API，用于对应用程序其余部分的数据访问。
//为什么使用存储库：存储库管理查询，并允许您使用多个后端。
//在最常见的示例中，存储库实现了用于确定是从网络中获取数据还是使用本地数据库中缓存的结果的逻辑。
//DAO被传递到存储库构造函数，而不是整个数据库。这是因为它只需要访问DAO，因为DAO包含数据库的所有读/写方法。无需将整个数据库公开到存储库。
class WordRepository(val wordDao: WordDao) {

    //Room在单独的线程上执行所有查询。
    //当数据已更改时，LiveData将在主线程上通知观察者
    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizedWords()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }


}