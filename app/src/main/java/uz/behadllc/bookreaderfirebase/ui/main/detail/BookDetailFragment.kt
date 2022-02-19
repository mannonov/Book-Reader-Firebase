package uz.behadllc.bookreaderfirebase.ui.main.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import uz.behadllc.bookreaderfirebase.ui.main.read.ReadBookViewModel
import uz.behadllc.bookreaderfirebase.ui.main.read.ReadBookViewModel.Companion.file
import uz.behadllc.bookreaderfirebase.utils.Functions.makeInvisible
import uz.behadllc.bookreaderfirebase.utils.Functions.makeVisible
import uz.behadllc.bookreaderfirebase.utils.Variables.downloadingItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_book_detail.*
import uz.behadllc.bookreaderfirebase.R
import uz.behadllc.bookreaderfirebase.database.entitiy.BookModelDatabase
import uz.behadllc.bookreaderfirebase.ui.favorite.FavoriteViewModel

@AndroidEntryPoint
class BookDetailFragment : Fragment(R.layout.fragment_book_detail) {

    private val navArgs by navArgs<BookDetailFragmentArgs>()
    private val redBookViewModel: ReadBookViewModel by viewModels()
    private var isDownloading = false
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupButtonsOnClick()
        setupObserver()
    }

    private fun setupButtonsOnClick() {
        // Navigate to Read Book Fragment
        btnReadBook.setOnClickListener {
            val action = BookDetailFragmentDirections.actionBookDetailFragmentToReadBookFragment(
                navArgs.currentBook
            )
            findNavController().navigate(action)
        }

        // Download PDF from Firebase
        btnDetailDownload.setOnClickListener {
            if (!isDownloading) {
                isDownloading = !isDownloading
                progressBarDetailDownload.makeVisible()
                redBookViewModel.getBookData(
                    navArgs.currentBook.bookUrl,
                    navArgs.currentBook.name,
                    this.requireContext()
                )
            }
        }

        btnDetailSeeComments.setOnClickListener {
            val action = BookDetailFragmentDirections.actionBookDetailFragmentToCommentsFragment(
                currentBook = navArgs.currentBook
            )
            findNavController().navigate(action)
        }

        icBack.setOnClickListener {
            this.requireActivity().onBackPressed()
        }

        bookMark.setOnClickListener {
            favoriteViewModel.addBooks(BookModelDatabase(
                bookUrl = navArgs.currentBook.bookUrl,
                iconUrl = navArgs.currentBook.iconUrl,
                name = navArgs.currentBook.name,
                desc = navArgs.currentBook.desc,
                authorName = navArgs.currentBook.authorName,
                docId = navArgs.currentBook.docId,
                pageCount = navArgs.currentBook.pageCount,
                reviewCount = navArgs.currentBook.reviewCount,
                rating = navArgs.currentBook.rating
            ))
        }

    }

    private fun setupObserver() {
        // This method call when download on complete
        file.observe(this.viewLifecycleOwner, Observer {
            isDownloading = false
            Toast.makeText(
                this.requireContext(),
                resources.getString(R.string.downloadFolder),
                Toast.LENGTH_LONG
            ).show()
            progressBarDetailDownload.makeInvisible()
        })

        // Show user download progress
        downloadingItems.observe(this.viewLifecycleOwner, Observer { item ->
            item[navArgs.currentBook.name]?.let {
                val totalPercent = ((it.currentBytes * 100) / it.totalBytes).toInt()
                progressBarDetailDownload.progress = totalPercent
            }
        })
    }

    private fun setupData() {
        val currentBook = navArgs.currentBook
        imgBookDetail.load(currentBook.iconUrl)
        tvBookDetailAuthor.text = currentBook.authorName
        tvBookDetailName.text = currentBook.name
        ratingBar.rating = currentBook.rating / currentBook.reviewCount
        tvDetailDesc.text = currentBook.desc
        tvBookDetailReviewCount.text = "${currentBook.reviewCount}".plus(" ko'rilgan")
        tvBookDetailPageCount.text = "${currentBook.pageCount}".plus(" saxifa")
    }

    override fun onStart() {
        super.onStart()
        // If selected item currently downloading  than show download progress bar to user
        downloadingItems.value?.get(navArgs.currentBook.name)?.let {
            progressBarDetailDownload.makeVisible()
        }
    }
}