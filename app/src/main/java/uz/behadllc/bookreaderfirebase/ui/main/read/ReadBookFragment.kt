package uz.behadllc.bookreaderfirebase.ui.main.read

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import uz.behadllc.bookreaderfirebase.ui.main.read.ReadBookViewModel.Companion.file
import uz.behadllc.bookreaderfirebase.utils.Functions.makeInvisible
import uz.behadllc.bookreaderfirebase.utils.Functions.makeVisible
import uz.behadllc.bookreaderfirebase.utils.Variables
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_read_book.*
import uz.behadllc.bookreaderfirebase.R
import uz.behadllc.bookreaderfirebase.utils.Functions.calculateProgress
import uz.behadllc.bookreaderfirebase.utils.Functions.calculateProgressString

@AndroidEntryPoint
class ReadBookFragment : Fragment(R.layout.fragment_read_book) {
    private val navArgs by navArgs<ReadBookFragmentArgs>()
    private val redBookViewModel: ReadBookViewModel by viewModels()

    private fun setupObserver() {
        file.observe(this.viewLifecycleOwner, Observer {
            changeLayoutVisibilities()
            pdfView.fromUri(it)
                .pageFling(true)
                .swipeHorizontal(true)
                .pageSnap(true)
                .load()
        })

        // Show user download progress
        Variables.downloadingItems.observe(this.viewLifecycleOwner, Observer { item ->
            item[navArgs.currentBook.name]?.let {
                val totalPercent = it.calculateProgress()
                progressBarDownloadState.progress = totalPercent
                tvProcessPercent.text = "$totalPercent".plus(" %")
                tvProcessPercentText.text = it.calculateProgressString()
            }
        })
    }

    private fun changeLayoutVisibilities() {
        loadingViewRead.makeInvisible()
        containerViewRead.makeVisible()
    }

    override fun onStart() {
        super.onStart()
        setupObserver()
        // If book currently downloading than show progressbar
        if (Variables.downloadingItems.value?.get(navArgs.currentBook.name) == null) {
            redBookViewModel.getBookData(
                navArgs.currentBook.bookUrl,
                navArgs.currentBook.name,
                this.requireContext()
            )
        }
    }
}