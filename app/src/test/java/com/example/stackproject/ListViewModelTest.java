package com.example.stackproject;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.content.ClipData;
import android.view.View;

import com.example.stackproject.data.model.Items;
import com.example.stackproject.data.model.Repo;
import com.example.stackproject.data.rest.RepoRepository;
import com.example.stackproject.data.rest.RepoService;
import com.example.stackproject.ui.main.ListViewModel;
import com.example.stackproject.util.ViewModelFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import io.reactivex.Single;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
public class ListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    ViewModelFactory viewModelFactory;
    private ListViewModel viewModel;
    @Mock
    Observer<Boolean> observer;
    @Mock
    Observer<List<Repo>> observer1;
    @Mock
    RepoRepository repoRepository;
    @Mock
    RepoService repoService;
    LifecycleOwner lifecycleOwner;
    Lifecycle lifecycle;
    private Items item_list;
    private List<Repo> items;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        item_list = new Items(items);
        repoRepository = new RepoRepository(repoService);
        viewModel = new ListViewModel(repoRepository);
        viewModel.getLoading().observeForever(observer);
    }

    @Test
    public void testNull() {
        when(repoRepository.getRepositories()).thenReturn(null);
        assertNotNull(viewModel.getLoading());
        assertTrue(viewModel.getLoading().hasObservers());
    }


   /* @Test
    public void testApiFetchDataSuccess() {
        when(repoRepository.getRepositories()).thenReturn(Single.just(item_list));
        viewModel.getRepos();
        verify(observer1).onChanged(items);
    }*/

    @After
    public void tearDown() throws Exception {
        repoRepository =null;
        viewModel = null;
    }

    }

