package com.ricardocenteno.octostalker;

import android.test.mock.MockContext;
import com.ricardocenteno.octostalker.model.User;
import com.ricardocenteno.octostalker.viewmodel.ItemUserViewModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ItemUserViewModelTest {

    private static final String USER_NAME_TEST = "rickstart";
    private static final Integer USER_FOLLOWERS_TEST = 199;
    private static final String USER_AVATAR_TEST = "https://avatars1.githubusercontent.com/u/7413593?v=4";

    @Mock
    private MockContext mockContext;

    @Test
    public void shouldGetUsername() throws Exception {
        User user = new User();
        user.setLogin(USER_NAME_TEST);
        ItemUserViewModel itemUserViewModel = new ItemUserViewModel(user, mockContext,null);
        assertEquals(user.getLogin(), itemUserViewModel.getName());
    }

    @Test
    public void shouldGetFollowers() throws Exception {
        User user = new User();
        user.setFollowers(USER_FOLLOWERS_TEST);
        ItemUserViewModel itemUserViewModel = new ItemUserViewModel(user, mockContext,null);
        assertEquals(String.valueOf(user.getFollowers()), itemUserViewModel.getFollowers());
    }

    @Test
    public void shouldGetImageProfile() throws Exception {
        User user = new User();
        user.setAvatarUrl(USER_AVATAR_TEST);
        ItemUserViewModel itemUserViewModel = new ItemUserViewModel(user, mockContext,null);
        assertEquals(user.getAvatarUrl(), itemUserViewModel.getImgProfile());
    }
}
