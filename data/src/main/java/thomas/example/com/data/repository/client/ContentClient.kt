package thomas.example.com.data.repository.client

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import thomas.example.com.data.module.ModuleSharedPrefs
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import javax.inject.Inject

class ContentClient @Inject constructor(moduleSharedPrefs: ModuleSharedPrefsImpl) {

    private var moduleSharedPrefs: ModuleSharedPrefs = moduleSharedPrefs

    fun setIdInSharedPrefs(idUser: String?): Observable<Boolean> {
        return try {
            moduleSharedPrefs.setIdUserInSharedPrefs(idUser)
            Observable.just(true)
        } catch (e: Exception) {
            Observable.error(e)
        }
    }

    fun getIdInSharedPrefs(): Single<String> {
        return Single.just(moduleSharedPrefs.getIdUserInSharedPrefs())
    }

    fun deleteIdInSharedPrefs(): Completable {
        return try {
            moduleSharedPrefs.deleteIdUserInSharedPrefs()
            Completable.complete()
        } catch (e: Exception) {
            Completable.error(e)
        }
    }

    fun setInstrumentModeInSharedPrefs(): Observable<Boolean> {
        return try {
            moduleSharedPrefs.setInstrumentModeInSharedPrefs()
            Observable.just(true)
        } catch (e: Exception) {
            Observable.error(e)
        }
    }
}