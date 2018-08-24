package thomas.example.com.data.repository.client

import io.reactivex.Observable
import thomas.example.com.data.module.ModuleSharedPrefs
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import javax.inject.Inject

class ContentClient @Inject constructor(moduleSharedPrefs: ModuleSharedPrefsImpl) {

    private var moduleSharedPrefs: ModuleSharedPrefs = moduleSharedPrefs

    fun setIdInSharedPrefs(idUser: String): Observable<Boolean> {
        return try {
            moduleSharedPrefs.setIdUserInSharedPrefs(idUser)
            Observable.just(true)
        } catch (e: Exception) {
            Observable.error(e)
        }
    }

    fun getIdInSharedPrefs(): Observable<String> {
        return Observable.just(moduleSharedPrefs.getIdUserInSharedPrefs())
    }

    fun deleteIdInSharedPrefs(): Observable<Boolean> {
        return try {
            moduleSharedPrefs.deleteIdUserInSharedPrefs()
            Observable.just(true)
        } catch (e: Exception) {
            Observable.error(e)
        }
    }
}