package com.ricardocenteno.octostalker;

import android.test.mock.MockContext;
import android.view.View;
import com.ricardocenteno.octostalker.data.FakeRandomCompany;
import com.ricardocenteno.octostalker.data.UserService;
import com.ricardocenteno.octostalker.model.User;
import com.ricardocenteno.octostalker.viewmodel.UsersViewModel;
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
    private static final String COMPANY = "facebook";
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
        List<User> users = FakeRandomCompany.getUserList();
        doReturn(Observable.just(users)).when(userService).fetchUsers(COMPANY);
    }

    @Test public void ensureTheViewsAreInitializedCorrectly() throws Exception {
        usersViewModel.initializeViews();
        assertEquals(View.GONE, usersViewModel.peopleLabel.get());
        assertEquals(View.GONE, usersViewModel.peopleRecycler.get());
        assertEquals(View.VISIBLE, usersViewModel.peopleProgress.get());
    }
}
