package uz.behadllc.bookreaderfirebase.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.behadllc.bookreaderfirebase.R

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val TAG = "FavoriteFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel.getAllFavoriteBooks().observe(requireActivity()) {
            Log.d(TAG, "onViewCreated: $it")
        }

    }

}