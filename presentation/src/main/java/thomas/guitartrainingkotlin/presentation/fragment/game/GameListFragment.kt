package thomas.guitartrainingkotlin.presentation.fragment.game

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_game_list.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.extension.ActivityExtensions
import thomas.guitartrainingkotlin.presentation.extension.setSupportActionBar
import thomas.guitartrainingkotlin.presentation.fragment.BaseFragment
import thomas.guitartrainingkotlin.presentation.viewmodel.game.GameListViewModel

class GameListFragment : BaseFragment<GameListViewModel>() {

    override val viewModelClass = GameListViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_game_list

    private var navHost: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            navHost = it.findViewById(R.id.game_nav_host_fragment) as View
        }

        initiateToolbar()
        initiateViews()
        initiateViewModelObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initiateToolbar() {
        setHasOptionsMenu(true)
        activity?.setSupportActionBar(fragment_game_list_toolbar, ActivityExtensions.DISPLAY_UP)
    }

    private fun initiateViews() {
        button_game_interval.setOnClickListener {
            navHost?.let { view ->
                Navigation.findNavController(view).navigate(R.id.action_game_list_to_interval_game, null, null)
            }
        }

        button_game_scale.setOnClickListener {
            navHost?.let { view ->
                Navigation.findNavController(view).navigate(R.id.action_game_list_to_scale_game, null, null)
            }
        }

        button_game_reversed_interval.setOnClickListener {
            navHost?.let { view ->
                Navigation.findNavController(view).navigate(R.id.action_game_list_to_reversed_interval_game, null, null)
            }
        }
    }

    private fun initiateViewModelObservers() {}
}