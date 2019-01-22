package thomas.example.com.data.business

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import thomas.example.com.data.manager.SharedPrefsManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentBusinessHelper @Inject constructor(private val sharedPrefsManager: SharedPrefsManager) {

    fun getUserIdInSharedPrefs(): Single<String> {
        return Single.just(sharedPrefsManager.getUserIdInSharedPrefs())
    }

    fun setIdInSharedPrefs(userId: String): Observable<Boolean> {
        sharedPrefsManager.setIdUserInSharedPrefs(userId)
        return Observable.just(true)
    }

    fun deleteIdInSharedPrefs(): Completable {
        return try {
            sharedPrefsManager.deleteIdUserInSharedPrefs()
            Completable.complete()
        } catch (e: Exception) {
            Completable.error(e)
        }
    }

    fun setInstrumentModeInSharedPrefs(): Completable {
        return try {
            sharedPrefsManager.setInstrumentModeInSharedPrefs()
            Completable.complete()
        } catch (e: Exception) {
            Completable.error(e)
        }
    }
}