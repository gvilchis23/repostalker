package com.ricardocenteno.octostalker;

import android.test.mock.MockContext;
import android.view.View;

import com.ricardocenteno.octostalker.data.UserService;
import com.ricardocenteno.octostalker.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UsersViewModelTest {
    private static final String URL_TEST = "http://api.randomuser.me/?results=10&nat=en";
   /* @Mock
    private UserService userService;
    @Mock private MockContext mockContext;

    private UserViewModel peopleViewModel;

    @Before
    public void setUpMainViewModelTest() {
        peopleViewModel = new PeopleViewModel(mockContext);
    }

    @Test
    public void simulateGivenTheUserCallListFromApi() throws Exception {
        List<User> peoples = FakeRandomUserGeneratorAPI.getPeopleList();
        doReturn(Observable.just(peoples)).when(peopleService).fetchPeople(URL_TEST);
    }

    @Test public void ensureTheViewsAreInitializedCorrectly() throws Exception {
        peopleViewModel.initializeViews();
        assertEquals(View.GONE, peopleViewModel.peopleLabel.get());
        assertEquals(View.GONE, peopleViewModel.peopleRecycler.get());
        assertEquals(View.VISIBLE, peopleViewModel.peopleProgress.get());
    }*/
}
