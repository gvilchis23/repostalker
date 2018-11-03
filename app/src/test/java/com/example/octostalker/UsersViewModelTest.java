package com.example.octostalker;

import android.test.mock.MockContext;
import android.view.View;
import com.example.octostalker.data.MockCompany;
import com.example.octostalker.data.UserService;
import com.example.octostalker.model.User;
import com.example.octostalker.viewmodel.UsersViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import io.reactivex.Observable;
import static org.mockito.Mockito.doReturn;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UsersViewModelTest {
    private static final String COMPANY = "bypass";
    @Mock
    private UserService userService;
    @Mock private MockContext mockContext;

    private UsersViewModel usersViewModel;

    @Before
    public void setUpMainViewModelTest() {
        usersViewModel = new UsersViewModel(mockContext,null);
    }

    @Test
    public void simulateGivenTheUserCallListFromApi() throws Exception {
        List<User> users = MockCompany.getUserList();
        doReturn(Observable.just(users)).when(userService).getOrganizationMember(COMPANY);
    }

    @Test public void ensureTheViewsAreInitializedCorrectly() throws Exception {
        usersViewModel.initializeViews();
        assertEquals(View.GONE, usersViewModel.peopleLabel.get());
        assertEquals(View.GONE, usersViewModel.peopleRecycler.get());
        assertEquals(View.VISIBLE, usersViewModel.peopleProgress.get());
    }
}
