package thomas.example.com.data.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleSharedPrefsImpl @Inject constructor(private var context: Context) : ModuleSharedPrefs {

    companion object {
        const val CURRENT_USER_ID: String = "CURRENT_USER_ID"
    }

    private var sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun setIdUserInSharedPrefs(idUser: String) {
        sharedPreferences.edit().putString(CURRENT_USER_ID, idUser).apply()
    }

    override fun getIdUserInSharedPrefs(): String {
        return sharedPreferences.getString(CURRENT_USER_ID, null)
    }

    override fun deleteIdUserInSharedPrefs() {
        return sharedPreferences.edit().remove(CURRENT_USER_ID).apply()
    }
}