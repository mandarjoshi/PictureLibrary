package com.mandarjoshi.picturelibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mandarjoshi.picturelibrary.viewmodel.PictureViewModel
import com.mandarjoshi.picturelibrary.viewmodel.ViewModelFactory

import javax.inject.Inject

import com.mandarjoshi.picturelibrary.PictureLibraryApplication

import androidx.lifecycle.Observer
import com.mandarjoshi.picturelibrary.databinding.FragmentPictureMainBinding
import com.mandarjoshi.picturelibrary.model.Album
import com.mandarjoshi.picturelibrary.model.Manifest
import com.mandarjoshi.picturelibrary.model.PictureContainer
import com.mandarjoshi.picturelibrary.util.DialogUtil.getSimpleErrorDialog
import com.mandarjoshi.picturelibrary.util.Resource
import com.mandarjoshi.picturelibrary.util.Status

import com.google.android.material.tabs.TabLayoutMediator


class PictureMainFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val mViewModel: PictureViewModel by activityViewModels { viewModelFactory }

    private lateinit var binding: FragmentPictureMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as PictureLibraryApplication).appComponent.inject(this)
        mViewModel.getPictureGroups().observe(this,groupsObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPictureMainBinding.inflate(inflater, container, false)

        binding.prevButton.setOnClickListener {
            loadAllPicturesForAlbum(mViewModel.previousAlbum())
            setButtonState()
        }
        binding.nextButton.setOnClickListener {
            loadAllPicturesForAlbum(mViewModel.nextAlbum())
            setButtonState()
        }
        return binding.root
    }

    private fun setButtonState(){
        binding.prevButton.isEnabled = !mViewModel.isFirstIndex()
        binding.nextButton.isEnabled = mViewModel.hasMoreGroups()
    }

    private fun loadAllPicturesForAlbum(album: Album){
        for (pictureContainer in album.pictureContainers){
            mViewModel.getPictureDetails(pictureContainer).observe(viewLifecycleOwner, pictureDetailsObserver)
        }
    }
    private val groupsObserver: Observer<Resource<Manifest?>> =
        Observer<Resource<Manifest?>> { resource ->
            when(resource.status){
                Status.ERROR -> {
                    hideProgressBar(binding.root)
                    getSimpleErrorDialog(requireActivity()).show()
                } Status.SUCCESS -> {
                    hideProgressBar(binding.root)
                    mViewModel.refreshGroups(resource.data)
                    setButtonState()
                    resource.data?.let {
                        if(it.groups.isNotEmpty()){
                            loadAllPicturesForAlbum(mViewModel.getCurrentAlbum())
                        }
                    }
                } else -> {
                    showProgressBar(binding.root)
                }
            }
        }

    private val pictureDetailsObserver : Observer<Resource<PictureContainer?>> =
        Observer<Resource<PictureContainer?>> { resource ->
            when(resource.status){
                Status.ERROR -> {
                    getSimpleErrorDialog(requireActivity()).show()
                } Status.SUCCESS -> {
                    val album = mViewModel.getCurrentAlbum()
                    if(album.allPictureDetailsLoaded()) {
                        binding.pictureViewPager.adapter = PictureAdapter(album.pictureContainers)
                            TabLayoutMediator(binding.tabDots, binding.pictureViewPager) { _, _ ->
                            }.attach()
                    }
                } else -> {
                    //showProgressBar(binding.root)
                }
            }
        }

}
